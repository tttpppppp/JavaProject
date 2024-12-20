package org.example.testgiaodienfx.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import org.example.testgiaodienfx.model.RoomType;
import org.example.testgiaodienfx.model.Rooms;
import org.example.testgiaodienfx.service.RoomSerice;
import org.example.testgiaodienfx.utils.Dialog;

import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class RoomController implements Initializable {
    private RoomSerice roomService = new RoomSerice();
    private Dialog dialog = new Dialog();
    @FXML
    private TableView<Rooms> tableViewPhong;
    @FXML
    private TableColumn<Rooms, Integer> maPhongColumn;
    @FXML
    private TableColumn<Rooms, String> roomTypeNameColumn;
    @FXML
    private TableColumn<Rooms, Float> giaQuaDemColumn;
    @FXML
    private TableColumn<Rooms, Float> getTheoGioDemColumn;
    @FXML
    private TableColumn<Rooms, Integer> statusColumn;

    @FXML
    private TableColumn<Rooms, String> txtDescriptionCloumn;

    @FXML
    private ComboBox<RoomType> comboLoaiPhong;

    @FXML
    private RadioButton radioConTrong, radioDaDat;

    @FXML
    private TextField txtGiaQuaDem, txtMaPhong, txtTheoGio;

    private ObservableList<Rooms> listRooms;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        maPhongColumn.setCellValueFactory(new PropertyValueFactory<>("room_id"));
        roomTypeNameColumn.setCellValueFactory(new PropertyValueFactory<>("room_type_name"));
        giaQuaDemColumn.setCellValueFactory(new PropertyValueFactory<>("price_per_night"));
        getTheoGioDemColumn.setCellValueFactory(new PropertyValueFactory<>("price_per_hour"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        txtDescriptionCloumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        giaQuaDemColumn.setCellFactory(column -> new TableCell<Rooms, Float>() {
            private final DecimalFormat formatter = new DecimalFormat("###,###,###.## VNĐ");

            @Override
            protected void updateItem(Float price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(formatter.format(price));
                }
            }
        });
        getTheoGioDemColumn.setCellFactory(column -> new TableCell<Rooms, Float>() {
            private final DecimalFormat formatter = new DecimalFormat("###,###,###.## VNĐ");

            @Override
            protected void updateItem(Float price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(formatter.format(price));
                }
            }
        });
        statusColumn.setCellFactory(column -> new TableCell<Rooms, Integer>() {
            @Override
            protected void updateItem(Integer status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                } else {
                    setText(status == 0 ? "Đã đặt" : "Còn trống");
                }
            }
        });
        listRooms = FXCollections.observableArrayList(roomService.getAllRooms());
        tableViewPhong.setItems(listRooms);

        tableViewPhong.setOnMouseClicked(event -> {
            Rooms selectedRoom = tableViewPhong.getSelectionModel().getSelectedItem();
            if (selectedRoom != null) {
                txtMaPhong.setText(String.valueOf(selectedRoom.getRoom_id()));
                txtGiaQuaDem.setText(String.valueOf(selectedRoom.getPrice_per_night()));
                txtTheoGio.setText(String.valueOf(selectedRoom.getPrice_per_hour()));
                RoomType selectedRoomType = findRoomTypeByName(selectedRoom.getRoom_type_name());
                if (selectedRoomType != null) {
                    comboLoaiPhong.setValue(selectedRoomType);
                } else {
                    System.out.println("Không tìm thấy RoomType phù hợp!");
                }
                radioConTrong.setSelected(selectedRoom.getStatus() == 1);
                radioDaDat.setSelected(selectedRoom.getStatus() == 0);
            }
        });

        Platform.runLater(() -> {
            try {
                populateRoomTypes();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private RoomType findRoomTypeByName(String name) {
        return comboLoaiPhong.getItems()
                .stream()
                .filter(roomType -> roomType.getRoom_type_name().equals(name))
                .findFirst()
                .orElse(null);
    }

    private void populateRoomTypes() throws SQLException {
        List<RoomType> roomTypeList = roomService.getAllRoomTypes();
        comboLoaiPhong.setItems(FXCollections.observableArrayList(roomTypeList));
        if (!roomTypeList.isEmpty()) {
            comboLoaiPhong.setValue(roomTypeList.get(0));
            int defaultRoomTypeId = roomTypeList.get(0).getRoom_type_id();
            System.out.println("ID của loại phòng mặc định: " + defaultRoomTypeId);
        }
        comboLoaiPhong.setCellFactory(comboBox -> new ListCell<>() {
            @Override
            protected void updateItem(RoomType roomType, boolean empty) {
                super.updateItem(roomType, empty);
                setText(empty || roomType == null ? null : roomType.getRoom_type_name());
            }
        });
        comboLoaiPhong.setConverter(new StringConverter<>() {
            @Override
            public String toString(RoomType roomType) {
                return roomType == null ? null : roomType.getRoom_type_name();
            }

            @Override
            public RoomType fromString(String string) {
                return null;
            }
        });
        comboLoaiPhong.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int roomTypeId = newValue.getRoom_type_id();
                System.out.println("ID của loại phòng đã chọn: " + roomTypeId);
            }
        });
    }

    public void addRoom(ActionEvent event) {
        try {
            int roomId = Integer.parseInt(txtMaPhong.getText());
            float pricePerNight = Float.parseFloat(txtGiaQuaDem.getText());
            float pricePerHour = Float.parseFloat(txtTheoGio.getText());
            int status = radioConTrong.isSelected() ? 1 : 0;

            RoomType selectedRoomType = comboLoaiPhong.getValue();
            if (selectedRoomType == null) {
                selectedRoomType = comboLoaiPhong.getItems().get(0);
            }
            int roomTypeId = selectedRoomType.getRoom_type_id();

            Rooms newRoom = new Rooms(status, pricePerHour, pricePerNight, roomTypeId, roomId);

            boolean success = roomService.addRoom(newRoom);

            if (success) {
                String roomTypeName = roomService.getRoomTypeNameById(roomTypeId);
                newRoom.setRoom_type_name(roomTypeName);
                listRooms.add(newRoom);
                dialog.showSuccess("Thêm phòng thành công!");
            } else {
                dialog.showError("Có lỗi khi thêm phòng.");
            }
        } catch (NumberFormatException e) {
            dialog.showError("Vui lòng nhập đúng định dạng cho các trường giá và mã phòng.");
        } catch (SQLException e) {
            dialog.showError("Có lỗi khi kết nối với cơ sở dữ liệu: " + e.getMessage());
        }
    }
    @FXML
    private void handleDeleteRoom() throws SQLException {
        Rooms selectedRoom = tableViewPhong.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            boolean confirmed = dialog.showConfirmation(
                    "Bạn có chắc chắn muốn xóa phòng: " + selectedRoom.getRoom_id() + "?"
            );

            if (confirmed) {
                boolean success = roomService.deleteRoom(selectedRoom.getRoom_id());
                if (success) {
                    listRooms.remove(selectedRoom);
                    tableViewPhong.refresh();
                    dialog.showSuccess("Xóa phòng thành công!");
                } else {
                    dialog.showError("Có lỗi khi xóa phòng.");
                }
            }
        } else {
            dialog.showError("Vui lòng chọn phòng để xóa.");
        }
    }


    @FXML
    private void handleEditRoom() {
        Rooms selectedRoom = tableViewPhong.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            try {
                selectedRoom.setRoom_id(Integer.parseInt(txtMaPhong.getText()));
                selectedRoom.setPrice_per_night(Float.parseFloat(txtGiaQuaDem.getText()));
                selectedRoom.setPrice_per_hour(Float.parseFloat(txtTheoGio.getText()));
                RoomType selectedRoomType = comboLoaiPhong.getValue();
                if (selectedRoomType != null) {
                    selectedRoom.setRoom_type_id(selectedRoomType.getRoom_type_id());
                    selectedRoom.setRoom_type_name(selectedRoomType.getRoom_type_name());
                }
                selectedRoom.setStatus(radioConTrong.isSelected() ? 1 : 0);
                boolean success = roomService.updateRoom(selectedRoom);
                if (success) {
                    tableViewPhong.refresh();
                    dialog.showSuccess("Cập nhật phòng thành công!");
                } else {
                    dialog.showError("Có lỗi khi cập nhật phòng.");
                }
            } catch (NumberFormatException e) {
                dialog.showError("Vui lòng nhập đúng định dạng cho giá phòng và mã phòng.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            dialog.showError("Vui lòng chọn phòng để chỉnh sửa.");
        }
    }
}
