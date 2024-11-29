package org.example.projectjava.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.projectjava.utils.Dialog;
import org.example.projectjava.service.UserService;

import java.io.IOException;
import java.net.URL;

public class EmployeeController {
    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private Text txtError;

    @FXML
    Button btnLogin;

    UserService userService = new UserService();
    Dialog dialog = new Dialog();
    public void checkLogin(ActionEvent actionEvent) throws IOException {
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();
        if (email.isEmpty() || password.isEmpty()) {
            txtError.setText("Vui lòng điền đầy đủ thông tin");
        }else{
            txtError.setText("");
            if (userService.checkLogin(email, password)) {
                try {
                    URL fxmlLocation = getClass().getResource("/org/example/projectjava/home.fxml");
                    if (fxmlLocation == null) {
                        throw new IOException("File FXML không tồn tại!");
                    }
                    Parent homeRoot = FXMLLoader.load(fxmlLocation);
                    Scene homeScene = new Scene(homeRoot);

                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.setScene(homeScene);
                    stage.setTitle("Trang chủ");
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                txtError.setText("Sai email hoặc password");
            }
        }
    }
}
