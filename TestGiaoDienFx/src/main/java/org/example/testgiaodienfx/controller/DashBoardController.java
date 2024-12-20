package org.example.testgiaodienfx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashBoardController {
    @FXML
    private Button btnHome , btnQuanLyKhachHang , btnLoaiPhong ,
            btnQuanLyPhong , btnThanhToan , btnDatPhong , btnQuanLyNhanVien ,
            btnDanhSachThanhToan , btnDanhSachDatPhong;

    @FXML
    private AnchorPane contentMain;

    @FXML
    private void initialize() {
        loadFXMLContent("home.fxml");
        btnHome.setStyle("-fx-background-color: #ffe329; -fx-text-fill: #006BFF;");
        btnHome.setOnAction(event -> {
            setActiveButton(btnHome);
            loadFXMLContent("home.fxml");
        });

        btnLoaiPhong.setOnAction(event -> {
            setActiveButton(btnLoaiPhong);
            loadFXMLContent("loaiphong.fxml");
        });

        btnQuanLyPhong.setOnAction(event -> {
            setActiveButton(btnQuanLyPhong);
            loadFXMLContent("quanLyPhong.fxml");
        });

        btnQuanLyKhachHang.setOnAction(event -> {
            setActiveButton(btnQuanLyKhachHang);
            loadFXMLContent("quanLyKhachHang.fxml");
        });
        btnThanhToan.setOnAction(event -> {
            setActiveButton(btnThanhToan);
            loadFXMLContent("payment.fxml");
        });
        btnDatPhong.setOnAction(event -> {
            setActiveButton(btnDatPhong);
            loadFXMLContent("datphong.fxml");
        });
        btnQuanLyNhanVien.setOnAction(event -> {
            setActiveButton(btnQuanLyNhanVien);
        });
        btnDanhSachThanhToan.setOnAction(event -> {
            setActiveButton(btnDanhSachThanhToan);
        });
        btnDanhSachDatPhong.setOnAction(event -> {
            setActiveButton(btnDanhSachDatPhong);
            loadFXMLContent("danhsachdatphong.fxml");
        });
    }
    private void setActiveButton(Button activeButton) {
        Button[] buttons = {btnHome, btnLoaiPhong, btnQuanLyPhong ,
                btnQuanLyKhachHang ,btnThanhToan , btnDatPhong ,btnQuanLyNhanVien ,
                btnDanhSachThanhToan , btnDanhSachDatPhong  };

        for (Button button : buttons) {
            button.setStyle("-fx-background-color:  #006BFF; -fx-text-fill: white;");
        }
        activeButton.setStyle("-fx-background-color: #ffe329; -fx-text-fill: #006BFF;");
    }
    private void loadFXMLContent(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/testgiaodienfx/" + fxmlFileName));
            Node newContent = loader.load();
            contentMain.getChildren().clear();
            contentMain.getChildren().add(newContent);

            AnchorPane.setTopAnchor(newContent, 0.0);
            AnchorPane.setBottomAnchor(newContent, 0.0);
            AnchorPane.setLeftAnchor(newContent, 0.0);
            AnchorPane.setRightAnchor(newContent, 0.0);


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading FXML: " + fxmlFileName);
        }
    }
}
