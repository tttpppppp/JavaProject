package org.example.testgiaodienfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.testgiaodienfx.model.Customers;
import org.example.testgiaodienfx.model.RoomType;
import org.example.testgiaodienfx.service.CustomerService;
import org.example.testgiaodienfx.utils.Dialog;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    private CustomerService customerService = new CustomerService();
    private Dialog dialog = new Dialog();

    @FXML
    private TextField txtCMND, txtDiaChi, txtEmail, txtMaKhachHang, txtTenKhachHang, txtSoDienThoai;

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
                txtTenKhachHang.setText(selectedCustomer.getName());
                txtCMND.setText(selectedCustomer.getId_card());
                txtSoDienThoai.setText(selectedCustomer.getPhone_number());
                txtDiaChi.setText(selectedCustomer.getAddress());
                txtEmail.setText(selectedCustomer.getEmail());
            }
        });
    }

    @FXML
    private void handleAddCustomer() {
        System.out.println("hhh");
        String name = txtTenKhachHang.getText();
        String phoneNumber = txtSoDienThoai.getText();
        String email = txtEmail.getText();
        String address = txtDiaChi.getText();
        String idCard = txtCMND.getText();
        Customers newCustomer = new Customers(name, phoneNumber, email, address, idCard);
        boolean success = customerService.addCustomer(newCustomer);
        if (success) {
            listCustomer.add(newCustomer);
            dialog.showSuccess("Thêm khách hàng thành công!");
        } else {
            dialog.showError("Có lỗi khi thêm khách hàng.");
        }
    }
    @FXML
    private void handleEditCustomer() {
        Customers selectedCustomer = tableViewKhachHang.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            selectedCustomer.setName(txtTenKhachHang.getText());
            selectedCustomer.setPhone_number(txtSoDienThoai.getText());
            selectedCustomer.setEmail(txtEmail.getText());
            selectedCustomer.setAddress(txtDiaChi.getText());
            selectedCustomer.setId_card(txtCMND.getText());

            boolean success = customerService.updateCustomer(selectedCustomer);

            if (success) {
                tableViewKhachHang.refresh();
                dialog.showSuccess("Cập nhật khách hàng thành công!");
            } else {
                dialog.showError("Có lỗi khi cập nhật khách hàng.");
            }
        } else {
            dialog.showError("Vui lòng chọn khách hàng để chỉnh sửa.");
        }
    }
    @FXML
    private void handleDeleteCustomer() {
        Customers selectedRoomType = tableViewKhachHang.getSelectionModel().getSelectedItem();
        if (selectedRoomType != null) {
            boolean confirm = dialog.showConfirmation("Bạn có chắc chắn muốn xóa loại phòng này?");
            if (confirm) {
                boolean success = customerService.deleteCustomer(selectedRoomType.getCustomer_id());
                if (success) {
                    listCustomer.remove(selectedRoomType);
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
