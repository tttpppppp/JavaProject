package org.example.testgiaodienfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import org.example.testgiaodienfx.model.Bookings;
import org.example.testgiaodienfx.model.Customers;
import org.example.testgiaodienfx.model.RoomType;
import org.example.testgiaodienfx.model.Rooms;
import org.example.testgiaodienfx.service.BookingSerivce;
import org.example.testgiaodienfx.service.CustomerService;
import org.example.testgiaodienfx.service.RoomSerice;
import org.example.testgiaodienfx.utils.Dialog;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class BookingController implements Initializable {

    private CustomerService customerService = new CustomerService();
    private RoomSerice roomService = new RoomSerice();
    private BookingSerivce bookingService = new BookingSerivce();
    private Dialog dialog = new Dialog();

    @FXML
    private TextField txtMaDatPhong , txtMaKhachHang  , txtTimKiem;

    @FXML
    private ComboBox<Rooms> comboPhong;
    @FXML
    private DatePicker txtNgayDat ,txtNgayTra;
    @FXML
    private RadioButton radioAn , radioKichHoat;

    @FXML
    private TableColumn<Customers, Integer> maKhachHangColumn;

    @FXML
    private TableColumn<Customers, String> cmndColumn;

    @FXML
    private TableColumn<Customers, String> diaChiColumn;

    @FXML
    private TableColumn<Customers, String> emailColumn;

    @FXML
    private TableColumn<Customers, String> soDienThoaiColumn;

    @FXML
    private TableColumn<Customers, String> tenKhachHangColumn;

    @FXML
    private TableView<Customers> tableViewKhachHang;

    private ObservableList<Customers> listCustomer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        maKhachHangColumn.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        cmndColumn.setCellValueFactory(new PropertyValueFactory<>("id_card"));
        diaChiColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        soDienThoaiColumn.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        tenKhachHangColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        listCustomer = FXCollections.observableArrayList(customerService.getAllCustomers());
        tableViewKhachHang.setItems(listCustomer);

        tableViewKhachHang.setOnMouseClicked(event -> {
            Customers selectedCustomer = tableViewKhachHang.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                txtMaKhachHang.setText(String.valueOf(selectedCustomer.getCustomer_id()));
            }
        });
        try {
            populateRoomTypes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void populateRoomTypes() throws SQLException {
        List<Rooms> roomTypeList = roomService.getAvailableRooms();
        comboPhong.setItems(FXCollections.observableArrayList(roomTypeList));
        if (!roomTypeList.isEmpty()) {
            comboPhong.setValue(roomTypeList.get(0));
            int defaultRoomTypeId = roomTypeList.get(0).getRoom_id();
            System.out.println("ID của phòng mặc định: " + defaultRoomTypeId);
        }
        comboPhong.setCellFactory(comboBox -> new ListCell<>() {
            @Override
            protected void updateItem(Rooms rooms, boolean empty) {
                super.updateItem(rooms, empty);
                setText(empty || rooms == null ? null : String.valueOf(rooms.getRoom_id()));
            }
        });
        comboPhong.setConverter(new StringConverter<>() {
            @Override
            public String toString(Rooms rooms) {
                return rooms == null ? "" : String.valueOf(rooms.getRoom_id());
            }

            @Override
            public Rooms fromString(String s) {
                return null;
            }
        });
        comboPhong.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int roomTypeId = newValue.getRoom_id();
                System.out.println("ID của phòng đã chọn: " + roomTypeId);
            }
        });
    }
    @FXML
    public void filterCustomer() {
        String input = txtTimKiem.getText();
        List<Customers> customersList = customerService.getAllCustomers();
        List<Customers> filteredList = customersList.stream()
                .filter(item ->
                        (input != null && input.equals(item.getPhone_number())) ||
                                (input != null && input.equals(item.getId_card()))
                )
                .toList();
        if (filteredList.isEmpty()) {
            listCustomer.setAll(customersList);
        } else {
            listCustomer.setAll(filteredList);
        }

        tableViewKhachHang.setItems(listCustomer);
    }
    @FXML
    public void handleAddBooking() {
        try {
            String maDatPhong = txtMaDatPhong.getText();
            String maKhachHang = txtMaKhachHang.getText();
            Rooms selectedRoom = comboPhong.getValue();
            LocalDate ngayDat = txtNgayDat.getValue();
            LocalDate ngayTra = txtNgayTra.getValue();
            boolean isActive = radioKichHoat.isSelected();
            boolean isInactive = radioAn.isSelected();
            if (selectedRoom == null || ngayDat == null || ngayTra == null) {
                dialog.showError("Vui lòng nhập đầy đủ thông tin");
                return;
            }
            Bookings newBooking = new Bookings();
            newBooking.setCustomer_id(Integer.parseInt(maKhachHang));
            newBooking.setRoom_id(selectedRoom.getRoom_id());
            newBooking.setCheck_in(Timestamp.valueOf(ngayDat.atStartOfDay()));
            newBooking.setCheck_out(Timestamp.valueOf(ngayTra.atStartOfDay()));
            newBooking.setStatus(isActive ? 1 : (isInactive ? 0 : 0));

            boolean success = bookingService.addBooking(newBooking);
            if (success) {
                boolean roomUpdated = roomService.updateRoomStatus(selectedRoom.getRoom_id(), 0);

                if (roomUpdated) {
                    dialog.showSuccess("Đặt phòng thành công, trạng thái phòng đã được cập nhật.");
                    clearInputs();
                    populateRoomTypes();
                } else {
                    dialog.showError("Đặt phòng thành công, nhưng không thể cập nhật trạng thái phòng.");
                }
            } else {
                dialog.showError("Đã có lỗi xảy ra, vui lòng thử lại.");
            }
        } catch (NumberFormatException e) {
            dialog.showError("Vui lòng kiểm tra lại mã đặt phòng và mã khách hàng.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void clearInputs() {
        txtMaDatPhong.clear();
        txtMaKhachHang.clear();
        comboPhong.setValue(null);
        txtNgayDat.setValue(null);
        txtNgayTra.setValue(null);
        radioKichHoat.setSelected(false);
        radioAn.setSelected(false);
    }

}
