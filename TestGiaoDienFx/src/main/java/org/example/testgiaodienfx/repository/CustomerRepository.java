package org.example.testgiaodienfx.repository;

import org.example.testgiaodienfx.config.MysqlConnect;
import org.example.testgiaodienfx.model.Customers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    public boolean addCustomer(Customers customer) {
        String query = "INSERT INTO Customers (name, phone_number, email, address, id_card) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = MysqlConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getPhone_number());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getAddress());
            preparedStatement.setString(5, customer.getId_card());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm khách hàng: " + e.getMessage());
            return false;
        }
    }
    public List<Customers> getAllCustomers() {
        List<Customers> customersList = new ArrayList<>();
        String query = "SELECT * FROM Customers";

        try (Connection connection = MysqlConnect.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Customers customer = new Customers();
                customer.setName(resultSet.getString("name"));
                customer.setCustomer_id(resultSet.getInt("customer_id"));
                customer.setPhone_number(resultSet.getString("phone_number"));
                customer.setEmail(resultSet.getString("email"));
                customer.setAddress(resultSet.getString("address"));
                customer.setId_card(resultSet.getString("id_card"));
                customersList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customersList;
    }
    public boolean updateCustomer(Customers customer) {
        String query = "UPDATE Customers SET name = ?, phone_number = ?, email = ?, address = ?, id_card = ? WHERE customer_id = ?";

        try (Connection connection = MysqlConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getPhone_number());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getAddress());
            preparedStatement.setString(5, customer.getId_card());
            preparedStatement.setInt(6, customer.getCustomer_id());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error updating customer: " + e.getMessage());
            return false;
        }
    }
    public boolean deleteCustomer(int customerId) {
        String query = "DELETE FROM Customers WHERE customer_id = ?";
        try (Connection connection = MysqlConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, customerId);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0; // If one or more rows are affected, return true

        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa khách hàng: " + e.getMessage());
            return false;
        }
    }


}
