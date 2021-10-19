package com.yaohongy.StudyLog.controller;

import java.sql.Timestamp;
import java.util.Collection;

import com.yaohongy.StudyLog.config.MyUserDetail;
import com.yaohongy.StudyLog.entities.StudyLog;
import com.yaohongy.StudyLog.entities.User;
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

    @Autowired
    public LogController(LogService logService, UserService userService) {
        this.logService = logService;
        this.userService = userService;
    }

    @GetMapping("/mylogs")
    public String myLogs(Authentication authentication, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int perPage) {
        if(authentication == null) return "login";
        MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();
        User user = myUserDetail.getUser();
        Collection<StudyLog> logs = logService.findAllPageByUser(user, page, perPage).getContent();
        model.addAttribute("logs", logs);
        model.addAttribute("user", user);
        return "mylogs";
    }

    @GetMapping("/newlog")
    public String getnewlog(Authentication authentication, Model model) {
        if(authentication == null) return "login";
        MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();
        User user = myUserDetail.getUser();
        model.addAttribute("user", user);
        model.addAttribute("logs", new StudyLog());
        return "newlog";
    }

    @PostMapping("/newlog")
    public String postnewlog(Authentication authentication, Model model, @ModelAttribute StudyLog newlog) {
        if(authentication == null) return "login";
        MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();
        User user = myUserDetail.getUser();
        newlog.setUser(user);
        newlog.setCreateDate(new Timestamp(System.currentTimeMillis()));
        logService.save(newlog);
        return "redirect:/mylogs";
    }

    @PostMapping("/deletelog/{id}")
    public String deletelog(Authentication authentication, @PathVariable int id, Model model) {
        StudyLog log = logService.findById(id).get();
        logService.delete(log);
        return "redirect:/mylogs";
    }


}
