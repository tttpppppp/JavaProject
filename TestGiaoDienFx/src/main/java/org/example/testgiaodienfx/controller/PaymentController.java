package org.example.testgiaodienfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.testgiaodienfx.model.DanhSachBooking;
import org.example.testgiaodienfx.service.BookingSerivce;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    private BookingSerivce bookingSerivce = new BookingSerivce();
//    @FXML
//    private TextField txtMaDatPhong, txtMaKhachHang, txtTimKiem;
//
//    @FXML
//    private DatePicker txtNgayDat, txtNgayTra;
//
//    @FXML
//    private ComboBox<Rooms> comboPhong;
//
//    @FXML
//    private RadioButton radioAn, radioKichHoat;

    @FXML
    private TableView<DanhSachBooking> tableViewKhachHang;

    @FXML
    private TableColumn<DanhSachBooking, String> tenKhachHangColumn;

    @FXML
    private TableColumn<DanhSachBooking, String> soDienThoaiColumn;

    @FXML
    private TableColumn<DanhSachBooking, String> cmndColumn;

    @FXML
    private TableColumn<DanhSachBooking, Integer> soPhongClumn;

    @FXML
    private TableColumn<DanhSachBooking, String> loaiPhongClumn;

    @FXML
    private TableColumn<DanhSachBooking, LocalDate> ngayDatClumn;

    @FXML
    private TableColumn<DanhSachBooking, LocalDate> ngayTraClumn;
    @FXML
    private TableColumn<DanhSachBooking, Integer> maDatPhongColumn;

    private ObservableList<DanhSachBooking> listDanhSachPhong;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tenKhachHangColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        maDatPhongColumn.setCellValueFactory(new PropertyValueFactory<>("bookingid"));
        soDienThoaiColumn.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        cmndColumn.setCellValueFactory(new PropertyValueFactory<>("id_card"));
        soPhongClumn.setCellValueFactory(new PropertyValueFactory<>("room_id"));
        loaiPhongClumn.setCellValueFactory(new PropertyValueFactory<>("room_type_name"));
        ngayDatClumn.setCellValueFactory(new PropertyValueFactory<>("check_in"));
        ngayTraClumn.setCellValueFactory(new PropertyValueFactory<>("check_out"));
        listDanhSachPhong = FXCollections.observableArrayList(bookingSerivce.getAllBookingsWithDetails());
        tableViewKhachHang.setItems(listDanhSachPhong);
    }
}
