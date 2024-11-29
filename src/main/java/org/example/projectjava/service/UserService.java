package org.example.projectjava.service;

import org.example.projectjava.repository.UserRepository;
import org.example.projectjava.service.Imp.UserServiceImp;

public class UserService implements UserServiceImp {
    UserRepository userRepository = new UserRepository();
    @Override
    public boolean checkLogin(String email, String password) {
        return userRepository.checkLogin(email, password) != null;
    }
}
