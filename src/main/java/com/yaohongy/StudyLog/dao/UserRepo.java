package com.yaohongy.StudyLog.dao;

import com.yaohongy.StudyLog.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUsername(@Param("username") String username);
    Page<User> findAllByOrderById(Pageable pageable);
}
