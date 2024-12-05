package org.example.projectjava.repository;

import org.example.projectjava.config.MysqlConnect;
import org.example.projectjava.model.Employees;
import org.example.projectjava.model.Rooms;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public Employees checkLogin(String email, String password) {
        Employees emEmployee = null;
        String query = "SELECT * FROM Employees WHERE email = ?";

        try (
                Connection connection = MysqlConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");
                if (BCrypt.checkpw(password, hashedPassword)) {
                    emEmployee = new Employees();
                    emEmployee.setEmployee_id(resultSet.getInt("employee_id"));
                    emEmployee.setName(resultSet.getString("name"));
                    emEmployee.setEmail(resultSet.getString("email"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emEmployee;
    }
    public List<Rooms> getRoomsWithType() {
        List<Rooms> roomsList = new ArrayList<>();
        String query = "SELECT r.room_id, r.room_type_id, r.price_per_night, r.price_per_hour, r.status, rt.room_type_name " +
                "FROM Rooms r " +
                "JOIN RoomTypes rt ON r.room_type_id = rt.room_type_id " +
                "ORDER BY r.room_id ASC";


        try (Connection connection = MysqlConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Rooms room = new Rooms();
                room.setRoom_id(resultSet.getInt("room_id"));
                room.setRoom_type_id(resultSet.getInt("room_type_id"));
                room.setPrice_per_night(resultSet.getFloat("price_per_night"));
                room.setPrice_per_hour(resultSet.getFloat("price_per_hour"));
                room.setStatus(resultSet.getInt("status"));
                room.setRoom_type_name(resultSet.getString("room_type_name"));

                roomsList.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomsList;
    }
}
