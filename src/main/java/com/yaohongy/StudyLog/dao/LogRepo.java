package com.yaohongy.StudyLog.dao;

import com.yaohongy.StudyLog.entities.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;


@Repository
public interface LogRepo extends JpaRepository<StudyLog, Long> {

    Page<StudyLog> findByUserOrderByCreateDateDesc(User user, Pageable pageable);

    Page<StudyLog> findAllByOrderByCreateDateDesc(Pageable pageable);

    Optional<StudyLog> findById(long id);    
}
