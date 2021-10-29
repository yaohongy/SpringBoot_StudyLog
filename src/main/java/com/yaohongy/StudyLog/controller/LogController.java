package com.yaohongy.StudyLog.controller;

import java.sql.Timestamp;
import java.util.Collection;

import com.yaohongy.StudyLog.config.MyUserDetail;
import com.yaohongy.StudyLog.entities.Category;
import com.yaohongy.StudyLog.entities.StudyLog;
import com.yaohongy.StudyLog.entities.User;
import com.yaohongy.StudyLog.service.CategoryService;
import com.yaohongy.StudyLog.service.LogService;
import com.yaohongy.StudyLog.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LogController {
    
    private final LogService logService;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public LogController(LogService logService, UserService userService, CategoryService categoryService) {
        this.logService = logService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("/mylogs")
    public String myLogs(Authentication authentication, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int perPage) {
        if(authentication == null) return "login";
        MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();
        User user = myUserDetail.getUser();
        Collection<StudyLog> logs = logService.findAllPageByUser(user, page, perPage).getContent();
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
        log.setCategory(categoryService.findById(category.getId()));
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

    @PostMapping("/deletelog/{id}")
    public String deletelog(Authentication authentication, @PathVariable int id, Model model) {
        StudyLog log = logService.findById(id).get();
        logService.delete(log);
        return "redirect:/mylogs";
    }


}
