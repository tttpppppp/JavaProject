package org.example.testgiaodienfx.service;

import org.example.testgiaodienfx.model.RoomType;
import org.example.testgiaodienfx.repository.RoomTypeRepository;
import org.example.testgiaodienfx.service.Imp.RoomTypeImp;

import java.util.List;

public class RoomTypeService implements RoomTypeImp {
    RoomTypeRepository roomTypeRepository = new RoomTypeRepository();
    @Override
    public List<RoomType> getAllRoomTypes() {
        return roomTypeRepository.getAllRoomTypes();
    }

    @Override
    public boolean addRoomType(RoomType roomType) {
        return roomTypeRepository.addRoomType(roomType);
    }

    @Override
    public boolean updateRoomType(RoomType roomType) {
        return roomTypeRepository.updateRoomType(roomType);
    }

    @Override
    public boolean deleteRoomType(int roomTypeId) {
        return roomTypeRepository.deleteRoomType(roomTypeId);
    }
}
