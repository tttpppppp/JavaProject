package org.example.testgiaodienfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.testgiaodienfx.model.RoomType;
import org.example.testgiaodienfx.model.Rooms;
import org.example.testgiaodienfx.service.RoomTypeService;
import org.example.testgiaodienfx.utils.Dialog;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomTypeController implements Initializable {
    private RoomTypeService roomTypeService = new RoomTypeService();
    private Dialog dialog = new Dialog();
    @FXML
    private TextField txtIDLoaiPhong , txtMoTa , txtTenLoaiPhong;

    @FXML
    private TableColumn<RoomType, Integer> idTypeRoomColumn;

    @FXML
    private TableColumn<RoomType, String> moTaColumn;

    @FXML
    private RadioButton radioAn , radioKichHoat;

    @FXML
    private TableColumn<RoomType, String> roomTypeNameColumn;

    @FXML
    private TableView<RoomType> tableViewPhong;

    @FXML
    private TableColumn<RoomType, Integer> trangthaiCloumn;


    private ObservableList<RoomType> listRoomTypes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTypeRoomColumn.setCellValueFactory(new PropertyValueFactory<>("room_type_id"));
        roomTypeNameColumn.setCellValueFactory(new PropertyValueFactory<>("room_type_name"));
        moTaColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        trangthaiCloumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        listRoomTypes = FXCollections.observableArrayList(roomTypeService.getAllRoomTypes());
        trangthaiCloumn.setCellFactory(column -> new TableCell<RoomType, Integer>() {
            @Override
            protected void updateItem(Integer status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                } else {
                    setText(status == 0 ? "Ẩn" : "Kích hoạt");
                }
            }
        });
        tableViewPhong.setItems(listRoomTypes);

        tableViewPhong.setOnMouseClicked(event -> {
            RoomType selectedRoomType = tableViewPhong.getSelectionModel().getSelectedItem();
            if (selectedRoomType != null) {
                txtIDLoaiPhong.setText(String.valueOf(selectedRoomType.getRoom_type_id()));
                txtTenLoaiPhong.setText(selectedRoomType.getRoom_type_name());
                txtMoTa.setText(selectedRoomType.getDescription());
                radioKichHoat.setSelected(selectedRoomType.getStatus() == 1);
                radioAn.setSelected(selectedRoomType.getStatus() == 0);
            }
        });

    }
    @FXML
    private void handleAddRoomType() {
        String roomTypeName = txtTenLoaiPhong.getText();
        String description = txtMoTa.getText();
        int status = radioKichHoat.isSelected() ? 1 : 0;

        RoomType newRoomType = new RoomType(roomTypeName, description, status);

        boolean success = roomTypeService.addRoomType(newRoomType);

        if (success) {
            listRoomTypes.add(newRoomType);
            dialog.showSuccess("Thêm loại phòng thành công!");
        } else {
            dialog.showError("Có lỗi khi thêm loại phòng.");
        }
    }
    @FXML
    private void handleEditRoomType() {
        RoomType selectedRoomType = tableViewPhong.getSelectionModel().getSelectedItem();
        if (selectedRoomType != null) {
            selectedRoomType.setRoom_type_name(txtTenLoaiPhong.getText());
            selectedRoomType.setDescription(txtMoTa.getText());
            selectedRoomType.setStatus(radioKichHoat.isSelected() ? 1 : 0);

            boolean success = roomTypeService.updateRoomType(selectedRoomType);

            if (success) {
                tableViewPhong.refresh();
                dialog.showSuccess("Cập nhật loại phòng thành công!");
            } else {
                dialog.showError("Có lỗi khi cập nhật loại phòng.");
            }
        } else {
            dialog.showError("Vui lòng chọn loại phòng để chỉnh sửa.");
        }
    }
    @FXML
    private void handleDeleteRoomType() {
        RoomType selectedRoomType = tableViewPhong.getSelectionModel().getSelectedItem();
        if (selectedRoomType != null) {
            boolean confirm = dialog.showConfirmation("Bạn có chắc chắn muốn xóa loại phòng này?");
            if (confirm) {
                boolean success = roomTypeService.deleteRoomType(selectedRoomType.getRoom_type_id());
                if (success) {
                    listRoomTypes.remove(selectedRoomType);
                    dialog.showSuccess("Xóa loại phòng thành công!");
                } else {
                    dialog.showError("Có lỗi xảy ra khi xóa loại phòng.");
                }
            }
        } else {
            dialog.showError("Vui lòng chọn loại phòng để xóa.");
        }
    }
}
