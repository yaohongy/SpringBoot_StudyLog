package com.yaohongy.StudyLog.service;

import com.yaohongy.StudyLog.dao.CategoryRepo;
import com.yaohongy.StudyLog.entities.Category;
import com.yaohongy.StudyLog.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service("CategoryService")
public class CategoryService {
    

    private CategoryRepo categoryRepo;

    @Autowired
    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public Category save(Category category) {
        return categoryRepo.save(category);
    }

    public Category findById(Long id) {
        return categoryRepo.findById(id).get();
    }

    public void delete(Category category) {
        categoryRepo.delete(category);
    }

    public Page<Category> findByUser(User user, int page, int perPage) {
        return categoryRepo.findByUser(user, PageRequest.of(page, perPage));
    }

}
