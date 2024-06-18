package com.example.animal.repository;

import com.example.animal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String name);

    User findById(Long userId);
}
