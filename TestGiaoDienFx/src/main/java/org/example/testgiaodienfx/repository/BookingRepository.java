package org.example.testgiaodienfx.repository;

import org.example.testgiaodienfx.config.MysqlConnect;
import org.example.testgiaodienfx.model.Bookings;
import org.example.testgiaodienfx.model.DanhSachBooking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository {
    public boolean addBooking(Bookings booking) {
        String sql = "INSERT INTO Bookings (customer_id, room_id, check_in, check_out, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = MysqlConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, booking.getCustomer_id());
            statement.setInt(2, booking.getRoom_id());
            statement.setTimestamp(3, booking.getCheck_in());
            statement.setTimestamp(4, booking.getCheck_out());
            statement.setInt(5, booking.getStatus());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Bookings> getAllBookings() {
        List<Bookings> bookingsList = new ArrayList<>();
        String sql = "SELECT * FROM Bookings";

        try (Connection connection = MysqlConnect.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Bookings booking = new Bookings();
                booking.setBooking_id(resultSet.getInt("booking_id"));
                booking.setCustomer_id(resultSet.getInt("customer_id"));
                booking.setRoom_id(resultSet.getInt("room_id"));
                booking.setCheck_in(resultSet.getTimestamp("check_in"));
                booking.setCheck_out(resultSet.getTimestamp("check_out"));
                booking.setStatus(resultSet.getInt("status"));
                bookingsList.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookingsList;
    }

    public Bookings getBookingById(int bookingId) {
        String sql = "SELECT * FROM Bookings WHERE booking_id = ?";
        try (Connection connection = MysqlConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bookingId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Bookings booking = new Bookings();
                booking.setBooking_id(resultSet.getInt("booking_id"));
                booking.setCustomer_id(resultSet.getInt("customer_id"));
                booking.setRoom_id(resultSet.getInt("room_id"));
                booking.setCheck_in(resultSet.getTimestamp("check_in"));
                booking.setCheck_out(resultSet.getTimestamp("check_out"));
                booking.setStatus(resultSet.getInt("status"));
                return booking;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateBooking(Bookings booking) {
        String sql = "UPDATE Bookings SET customer_id = ?, room_id = ?, check_in = ?, check_out = ?, status = ? WHERE booking_id = ?";
        try (Connection connection = MysqlConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, booking.getCustomer_id());
            statement.setInt(2, booking.getRoom_id());
            statement.setTimestamp(3, booking.getCheck_in());
            statement.setTimestamp(4, booking.getCheck_out());
            statement.setInt(5, booking.getStatus());
            statement.setInt(6, booking.getBooking_id());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteBooking(int bookingId) {
        String sql = "DELETE FROM Bookings WHERE booking_id = ?";
        try (Connection connection = MysqlConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bookingId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<DanhSachBooking> getAllBookingsWithDetails() {
        List<DanhSachBooking> danhSachBookings = new ArrayList<>();
        String query = """
                SELECT 
                    c.name,
                    c.phone_number,
                    c.id_card,
                    r.room_id,
                    rt.room_type_name, -- Thay đổi r.room_type_name thành rt.room_type_name
                    b.check_in,
                    b.check_out,
                    b.status,
                    b.booking_id
                FROM Bookings b
                JOIN Customers c ON b.customer_id = c.customer_id
                JOIN Rooms r ON b.room_id = r.room_id
                JOIN RoomTypes rt ON r.room_type_id = rt.room_type_id
                """;

        try (Connection connection = MysqlConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                DanhSachBooking danhSachBooking = new DanhSachBooking();
                danhSachBooking.setCustomerName(resultSet.getString("name"));
                danhSachBooking.setBookingid(resultSet.getInt("booking_id"));
                danhSachBooking.setPhone_number(resultSet.getString("phone_number"));
                danhSachBooking.setId_card(resultSet.getString("id_card"));
                danhSachBooking.setRoom_id(resultSet.getInt("room_id"));
                danhSachBooking.setRoom_type_name(resultSet.getString("room_type_name"));
                danhSachBooking.setCheck_in(resultSet.getTimestamp("check_in"));
                danhSachBooking.setCheck_out(resultSet.getTimestamp("check_out"));
                danhSachBooking.setStatus(resultSet.getInt("status"));

                danhSachBookings.add(danhSachBooking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachBookings;
    }
}
