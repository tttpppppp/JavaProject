package org.example.testgiaodienfx.repository;

import org.example.testgiaodienfx.config.MysqlConnect;
import org.example.testgiaodienfx.model.RoomType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomTypeRepository {

    public List<RoomType> getAllRoomTypes() {
        List<RoomType> roomTypes = new ArrayList<>();
        String query = "SELECT * FROM RoomTypes";

        try (Connection connection = MysqlConnect.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                RoomType roomType = new RoomType();
                roomType.setRoom_type_id(resultSet.getInt("room_type_id"));
                roomType.setRoom_type_name(resultSet.getString("room_type_name"));
                roomType.setDescription(resultSet.getString("description"));
                roomType.setStatus(resultSet.getInt("status"));
                roomTypes.add(roomType);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách loại phòng: " + e.getMessage());
        }

        return roomTypes;
    }
    public boolean addRoomType(RoomType roomType) {
        String query = "INSERT INTO RoomTypes (room_type_name, description, status) VALUES (?, ?, ?)";

        try (Connection connection = MysqlConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, roomType.getRoom_type_name());
            preparedStatement.setString(2, roomType.getDescription());
            preparedStatement.setInt(3, roomType.getStatus());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm loại phòng: " + e.getMessage());
            return false;
        }
    }
    public boolean updateRoomType(RoomType roomType) {
        String query = "UPDATE RoomTypes SET room_type_name = ?, description = ?, status = ? WHERE room_type_id = ?";

        try (Connection connection = MysqlConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, roomType.getRoom_type_name());
            preparedStatement.setString(2, roomType.getDescription());
            preparedStatement.setInt(3, roomType.getStatus());
            preparedStatement.setInt(4, roomType.getRoom_type_id());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật loại phòng: " + e.getMessage());
            return false;
        }
    }
    public boolean deleteRoomType(int roomTypeId) {
        String query = "DELETE FROM RoomTypes WHERE room_type_id = ?";
        try (Connection connection = MysqlConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, roomTypeId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa loại phòng: " + e.getMessage());
            return false;
        }
    }

}
