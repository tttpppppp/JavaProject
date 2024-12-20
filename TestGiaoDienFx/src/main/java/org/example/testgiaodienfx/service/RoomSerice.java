package org.example.testgiaodienfx.service;


import org.example.testgiaodienfx.model.RoomType;
import org.example.testgiaodienfx.model.Rooms;
import org.example.testgiaodienfx.repository.RoomRepository;
import org.example.testgiaodienfx.service.Imp.RoomImp;

import java.sql.SQLException;
import java.util.List;

public class RoomSerice implements RoomImp {
    private RoomRepository roomRepository = new RoomRepository();

    @Override
    public List<RoomType> getAllRoomTypes(){
        return roomRepository.getAllTypeRoom();
    }

    @Override
    public boolean addRoom(Rooms room) throws SQLException {
        return roomRepository.addRoom(room);
    }

    @Override
    public String getRoomTypeNameById(int roomTypeId) throws SQLException {
        return roomRepository.getRoomTypeNameById(roomTypeId);
    }

    @Override
    public boolean updateRoom(Rooms room) throws SQLException {
       return roomRepository.updateRoom(room);
    }

    @Override
    public boolean deleteRoom(int roomId) throws SQLException {
        return roomRepository.deleteRoom(roomId);
    }

    @Override
    public boolean updateRoomStatus(int roomId, int status) throws SQLException {
        return roomRepository.updateRoomStatus(roomId, status);
    }

    @Override
    public List<Rooms> getAvailableRooms() {
        return roomRepository.getAvailableRooms();
    }

    @Override
    public List<Rooms> getAllRooms() {
        return roomRepository.getRoomsWithType();
    }
}
