package com.yaohongy.StudyLog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {

    @GetMapping("/")
    public String Index() {
        return "index";
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(defaultValue = "false") boolean error, Model model) {
        model.addAttribute("error", error);
        return "login";
    }
}
