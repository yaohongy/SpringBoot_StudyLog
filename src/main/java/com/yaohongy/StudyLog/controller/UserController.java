package com.yaohongy.StudyLog.controller;

import java.util.List;

import com.yaohongy.StudyLog.entities.User;
import com.yaohongy.StudyLog.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/new")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/users/new") 
    public String createUser(@ModelAttribute User user, Model model) {
        user = userService.save(user);
        model.addAttribute("user", user);
        model.addAttribute("message", "User " + user.getUsername() + " has been created");
        return "message";
    }

    @GetMapping("/users/manager")
    public String userManager(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int perPage) {
        Page<User> userPage = userService.findAllByPage(page, perPage);
        List<User> users = userPage.getContent();
        model.addAttribute("users", users);
        return "userManager";
    }

}
