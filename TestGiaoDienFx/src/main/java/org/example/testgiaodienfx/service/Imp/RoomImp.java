package org.example.testgiaodienfx.service.Imp;


import org.example.testgiaodienfx.model.RoomType;
import org.example.testgiaodienfx.model.Rooms;

import java.sql.SQLException;
import java.util.List;

public interface RoomImp {
    List<Rooms> getAllRooms();
    List<RoomType> getAllRoomTypes() throws SQLException;
    boolean addRoom(Rooms room) throws SQLException;
    String getRoomTypeNameById(int roomTypeId) throws SQLException;
    boolean updateRoom(Rooms room) throws SQLException;
    boolean deleteRoom(int roomId) throws SQLException;
    boolean updateRoomStatus(int roomId, int status) throws SQLException;
    List<Rooms> getAvailableRooms();
}
