package org.example.testgiaodienfx.repository;


import org.example.testgiaodienfx.config.MysqlConnect;
import org.example.testgiaodienfx.model.Employees;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
