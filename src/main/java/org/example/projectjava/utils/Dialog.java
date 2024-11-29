package org.example.projectjava.utils;

import javafx.scene.control.Alert;

public class Dialog {
    public void showSuccessDialog(String title, String content) {
        showDialog(Alert.AlertType.INFORMATION, title, content);
    }

    // Phương thức để hiển thị hộp thoại lỗi
    public void showErrorDialog(String title, String content) {
        showDialog(Alert.AlertType.ERROR, title, content);
    }

    // Phương thức để hiển thị hộp thoại cảnh báo
    public void showWarningDialog(String title, String content) {
        showDialog(Alert.AlertType.WARNING, title, content);
    }

    // Phương thức chung để hiển thị hộp thoại
    private void showDialog(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
