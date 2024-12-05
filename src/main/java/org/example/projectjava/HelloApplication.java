package org.example.projectjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.projectjava.config.MysqlConnect;
import org.example.projectjava.model.Rooms;
import org.example.projectjava.service.UserService;

import java.io.IOException;
import java.sql.Connection;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 740, 500);
        stage.setTitle("Vé Xe Rẻ");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        try (Connection connection = MysqlConnect.getConnection()) {
            if (connection != null) {
                System.out.println("Database connection established successfully.");
            } else {
                System.err.println("Failed to establish database connection.");
            }
        } catch (Exception e) {

        }

        UserService userService = new UserService();
        for (Rooms rooms : userService.getAllRooms()) {
            System.out.println(rooms.getRoom_id() + rooms.getRoom_type_name());
        }
        launch();
    }
}