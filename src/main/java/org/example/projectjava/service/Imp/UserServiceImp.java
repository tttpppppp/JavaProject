package org.example.projectjava.service.Imp;

import org.example.projectjava.model.Employees;
import org.example.projectjava.model.Rooms;

import java.util.List;

public interface  UserServiceImp {
    boolean checkLogin(String email , String password);
    List<Rooms> getAllRooms();
}
