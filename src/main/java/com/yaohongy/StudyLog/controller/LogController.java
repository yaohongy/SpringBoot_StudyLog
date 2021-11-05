package com.yaohongy.StudyLog.controller;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import com.yaohongy.StudyLog.config.MyUserDetail;
import com.yaohongy.StudyLog.config.PageConfig;
import com.yaohongy.StudyLog.entities.Category;
import com.yaohongy.StudyLog.entities.StudyLog;
import com.yaohongy.StudyLog.entities.User;
import com.yaohongy.StudyLog.service.CategoryService;
import com.yaohongy.StudyLog.service.LogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LogController {
    
    private final LogService logService;
    private final PageConfig pageConfig;
    private final CategoryService categoryService;

    @Autowired
    public LogController(LogService logService, PageConfig pageConfig, CategoryService categoryService) {
        this.logService = logService;
        this.pageConfig = pageConfig;
        this.categoryService = categoryService;
    }

    @GetMapping("/mylogs")
    public String myLogs(Authentication authentication, Model model, @RequestParam(defaultValue = "NULL") String categoryName, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "0") int perPage) {
        if(authentication == null) return "login";
        MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();
        User user = myUserDetail.getUser();
        if (perPage == 0) perPage = pageConfig.getDefaultPageSize();
        Collection<StudyLog> logs = logService.findAllPageByUser(user, page, perPage).getContent();
        if (!categoryName.equals("NULL")) {
            logs = logs.stream().filter(log -> log.getCategory().getCategoryName().equals(categoryName)).collect(Collectors.toList());
        }
        Collection<Category> categories = categoryService.findByUser(user, 0, Integer.MAX_VALUE).getContent();
        model.addAttribute("logs", logs);
        model.addAttribute("user", user);
        model.addAttribute("categories", categories);
        model.addAttribute("category", new Category());
        return "mylogs";
    }

    @GetMapping("/newlog")
    public String getnewlog(Authentication authentication, Model model) {
        if(authentication == null) return "login";
        MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();
        User user = myUserDetail.getUser();
        Collection<Category> categories = categoryService.findByUser(user, 0, Integer.MAX_VALUE).getContent();
        model.addAttribute("categories", categories);
        model.addAttribute("user", user);
        model.addAttribute("category", new Category());
        model.addAttribute("log", new StudyLog());
        return "newlog";
    }

    @PostMapping("/newlog")
    public String postNewLog(Authentication authentication, Model model, @ModelAttribute StudyLog log, @ModelAttribute Category category) {
        if(authentication == null) return "login";
        MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();
        User user = myUserDetail.getUser();
        log.setUser(user);
        log.setCreateDate(new Timestamp(System.currentTimeMillis()));
        log.setCategory(categoryService.findById(category.getId()).get());
        logService.save(log);
        return "redirect:/mylogs";
    }

    @PostMapping("/newCategory")
    public String postNewCategory(Authentication authentication, Model model, @ModelAttribute Category category) {
        if(authentication == null) return "login";
        MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();
        User user = myUserDetail.getUser();
        category.setUser(user);
        categoryService.save(category);
        return "redirect:/mylogs";
    }

    @DeleteMapping("/deleteCategory/{categoryId}")
    public String deleteCategory(Authentication authentication, Model model, @PathVariable Long categoryId) {
        if(authentication == null) return "login";
        Optional<Category> optionalCategory = categoryService.findById(categoryId);
        if (optionalCategory.isEmpty()) {
            model.addAttribute("message", "No such category");
            return "message";
        }
        Category category = optionalCategory.get();
        if (!((MyUserDetail)authentication.getPrincipal()).getUsername().equals(category.getUser().getUsername())) {
            model.addAttribute("message", "Can not delete");
            return "message";
        }
        categoryService.delete(category);
        model.addAttribute("message", "Category deleted");
        return "message";
    }

    @PostMapping("/deletelog/{id}")
    public String deletelog(Authentication authentication, @PathVariable int id, Model model) {
        StudyLog log = logService.findById(id).get();
        logService.delete(log);
        return "redirect:/mylogs";
    }


}
