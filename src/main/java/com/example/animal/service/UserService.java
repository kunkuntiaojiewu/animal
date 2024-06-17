package com.example.animal.service;

import com.example.animal.entity.User;
import com.example.animal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    public User login(String username, String password) {
        User user = userRepository.findByUserName(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else return null;
    }
    public User register(String username, String password){
        if (userRepository.findByUserName(username)==null){
            return userRepository.save(new User(username, password));
        }else return null;
    }

}
