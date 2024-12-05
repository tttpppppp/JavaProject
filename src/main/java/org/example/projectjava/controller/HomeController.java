package org.example.projectjava.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import org.example.projectjava.model.Rooms;
import org.example.projectjava.service.UserService;

import java.util.List;

public class HomeController {
    private UserService userService = new UserService();

    @FXML
    private GridPane gridPane;

    @FXML
    public void initialize() {
        refreshData();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), event -> refreshData())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    public void refreshData() {
        if (gridPane != null) {
            // Lấy lại danh sách phòng từ cơ sở dữ liệu sau khi cập nhật thủ công trong MySQL Workbench
            List<Rooms> roomDetails = userService.getAllRooms();

            // Xóa các Label hiện tại trong GridPane để chuẩn bị hiển thị mới
            gridPane.getChildren().clear();

            int row = 0;
            int column = 0;
            int roomsPerRow = 3;

            // Duyệt qua các phòng và thêm Label vào GridPane
            for (Rooms roomDetail : roomDetails) {
                String status = roomDetail.getStatus() == 0 ? "Còn trống" : "Đã đặt";
                String roomText = roomDetail.getRoom_id() + " - " + roomDetail.getRoom_type_name() + " - " + status;

                // Tạo Label cho phòng
                Label roomLabel = new Label(roomText);

                // Áp dụng màu sắc cho Label dựa trên trạng thái
                String backgroundColor = (roomDetail.getStatus() == 0) ? "#4CAF50" : "#F44336"; // Màu xanh cho phòng trống, đỏ cho phòng đã đặt

                roomLabel.setStyle("-fx-background-color: " + backgroundColor + "; " +
                        "-fx-background-radius: 20px; -fx-padding: 20px; " +
                        "-fx-text-fill: #fff; -fx-font-size: 14px; -fx-font-weight: bold; " +
                        "-fx-effect: dropshadow(gaussian, #888, 3, 0, 2, 2);");

                // Thêm Label vào GridPane
                gridPane.add(roomLabel, column, row);

                column++;
                if (column >= roomsPerRow) {
                    column = 0;
                    row++;
                }
            }
        } else {
            System.out.println("GridPane is not initialized!");
        }
    }
}
