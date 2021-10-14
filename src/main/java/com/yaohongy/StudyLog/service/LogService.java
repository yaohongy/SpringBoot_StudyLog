package com.yaohongy.StudyLog.service;

import java.util.Optional;

import com.yaohongy.StudyLog.dao.LogRepo;
import com.yaohongy.StudyLog.entities.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service("logService")
public class LogService {
    
    private final LogRepo logRepo;

    @Autowired
    public LogService(LogRepo logRepo) {
        this.logRepo = logRepo;
    } 
    public Optional<StudyLog> findById(long id) {
        return logRepo.findById(id);
    }

    public StudyLog save(StudyLog log) {
        return logRepo.save(log);
    }

    public Page<StudyLog> findAllPage(int page, int perPage) {
        return logRepo.findAllByOrderByCreateDateDesc(PageRequest.of(page, perPage));
    }

    public Page<StudyLog> findAllPageByUser(User user, int page, int perPage) {
        return logRepo.findByUserOrderByCreateDateDesc(
                user, PageRequest.of(page, perPage)
        );
    }

    public void delete(StudyLog log) {
        logRepo.delete(log);;
    }
}
