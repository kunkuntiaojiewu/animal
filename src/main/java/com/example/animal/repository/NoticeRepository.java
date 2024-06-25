package com.example.animal.repository;

import com.example.animal.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice,Long> {
}
