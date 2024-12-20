package org.example.testgiaodienfx.repository;

import org.example.testgiaodienfx.config.MysqlConnect;
import org.example.testgiaodienfx.model.RoomType;
import org.example.testgiaodienfx.model.Rooms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {
    public List<RoomType> getAllTypeRoom() {
        List<RoomType> roomTypeList = new ArrayList<>();
        String query = "SELECT * FROM RoomTypes WHERE status = 1";
        try (Connection connection = MysqlConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                RoomType roomType = new RoomType();
                roomType.setRoom_type_id(resultSet.getInt("room_type_id"));
                roomType.setRoom_type_name(resultSet.getString("room_type_name"));
                roomType.setDescription(resultSet.getString("description"));
                roomTypeList.add(roomType);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
            return new ArrayList<>();
        }
        return roomTypeList;
    }

    public List<Rooms> getRoomsWithType() {
        List<Rooms> roomsList = new ArrayList<>();
        String query = "SELECT r.room_id, r.room_type_id, r.price_per_night, r.price_per_hour, r.status, rt.room_type_name , rt.description " +
                "FROM Rooms r " +
                "JOIN RoomTypes rt ON r.room_type_id = rt.room_type_id " +
                "WHERE rt.status = 1 " +
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
                room.setDescription(resultSet.getString("description"));

                roomsList.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomsList;
    }
    public boolean addRoom(Rooms room) {
        String query = "INSERT INTO Rooms (room_id, room_type_id, price_per_night, price_per_hour, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = MysqlConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, room.getRoom_id());
            preparedStatement.setInt(2, room.getRoom_type_id());
            preparedStatement.setFloat(3, room.getPrice_per_night());
            preparedStatement.setFloat(4, room.getPrice_per_hour());
            preparedStatement.setInt(5, room.getStatus());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm phòng: " + e.getMessage());
            return false;
        }
    }
    public boolean updateRoom(Rooms room) {
        String query = "UPDATE Rooms SET room_type_id = ?, price_per_night = ?, price_per_hour = ?, status = ? WHERE room_id = ?";

        System.out.println("Updating room with ID: " + room.getRoom_id()); // Log thông tin phòng

        try (Connection connection = MysqlConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, room.getRoom_type_id());
            preparedStatement.setFloat(2, room.getPrice_per_night());
            preparedStatement.setFloat(3, room.getPrice_per_hour());
            preparedStatement.setInt(4, room.getStatus());
            preparedStatement.setInt(5, room.getRoom_id());

            System.out.println("Executing update..."); // Log trạng thái trước khi thực thi

            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println("Rows affected: " + rowsAffected); // Log số dòng bị ảnh hưởng

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật phòng: " + e.getMessage());
            e.printStackTrace(); // In chi tiết lỗi
            return false;
        }
    }
    public boolean deleteRoom(int roomId) {
        String query = "DELETE FROM Rooms WHERE room_id = ?";
        try (Connection connection = MysqlConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, roomId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error in deleteRoom: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public String getRoomTypeNameById(int roomTypeId) throws SQLException {
        String query = "SELECT room_type_name FROM RoomTypes WHERE room_type_id = ?";
        try (Connection conn = MysqlConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, roomTypeId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("room_type_name");
                } else {
                    return null;
                }
            }
        }
    }
    public boolean updateRoomStatus(int roomId, int status) {
        String query = "UPDATE Rooms SET status = ? WHERE room_id = ?";
        try (Connection conn = MysqlConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, status); // 0 = Đang sử dụng, 1 = Trống
            pstmt.setInt(2, roomId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Rooms> getAvailableRooms() {
        String query = "SELECT * FROM Rooms WHERE status = 1";
        List<Rooms> availableRooms = new ArrayList<>();

        try (Connection conn = MysqlConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Rooms room = new Rooms();
                room.setRoom_id(rs.getInt("room_id"));
                room.setRoom_type_id(rs.getInt("room_type_id"));
                availableRooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableRooms;
    }

}
