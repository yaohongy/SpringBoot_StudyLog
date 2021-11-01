package com.yaohongy.StudyLog.dao;

import java.util.Optional;

import com.yaohongy.StudyLog.entities.Category;
import com.yaohongy.StudyLog.entities.User;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    Page<Category> findByUser(User user, Pageable pageable);
    Optional<Category> findByCategoryName(String categoryName);
}
