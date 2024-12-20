package org.example.testgiaodienfx.service;


import org.example.testgiaodienfx.repository.UserRepository;
import org.example.testgiaodienfx.service.Imp.UserServiceImp;

import java.util.List;

public class UserService implements UserServiceImp {
    org.example.testgiaodienfx.repository.UserRepository userRepository = new UserRepository();
    @Override
    public boolean checkLogin(String email, String password) {
        return userRepository.checkLogin(email, password) != null;
    }
}
