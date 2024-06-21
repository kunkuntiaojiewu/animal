package com.example.animal.service;

import com.example.animal.entity.User;
import com.example.animal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findUserById(Long id) {
        return userRepository.findById(id);
    }
    @Transactional
    public User login(String username, String password) {
        User user = userRepository.findByUserName(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else return null;
    }
    @Transactional
    public User register(String username, String password){
        if (userRepository.findByUserName(username)==null){
            User user = new User(username, password);
            user.setAvatar("https://img.icons8.com/dotty/80/test-account.png");
            return userRepository.save(user);
        }else return null;
    }
    @Transactional
    public void updatePassword(Long userId,String newPassword){
        User user = userRepository.findById(userId);
        user.setPassword(newPassword);
        userRepository.save(user);
    }

}
