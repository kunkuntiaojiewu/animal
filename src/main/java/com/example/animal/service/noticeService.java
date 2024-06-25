package com.example.animal.service;

import com.example.animal.entity.Notice;
import com.example.animal.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class noticeService {
    @Autowired
    private NoticeRepository noticeRepository;

    @Transactional
    public Boolean deleteNoticeById(Long id){
        try {
            noticeRepository.deleteById(id);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public Boolean saveNotice(Notice notice){
        try {
            noticeRepository.save(notice);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

}
