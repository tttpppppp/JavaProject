package org.example.testgiaodienfx.service.Imp;

import org.example.testgiaodienfx.model.RoomType;

import java.util.List;

public interface RoomTypeImp {
    List<RoomType> getAllRoomTypes();
    boolean addRoomType(RoomType roomType);
    boolean updateRoomType(RoomType roomType);
    boolean deleteRoomType(int roomTypeId);
}
