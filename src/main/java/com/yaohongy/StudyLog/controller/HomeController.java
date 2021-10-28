package com.yaohongy.StudyLog.controller;

import com.yaohongy.StudyLog.ExtApi.Quote;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


@Controller
public class HomeController {

    @GetMapping("/")
    public String Index(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://quoters.apps.pcfone.io/api/random";
        Quote quote = restTemplate.getForObject(url, Quote.class);
        model.addAttribute("quote", quote);
        return "index";
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(defaultValue = "false") boolean error, Model model) {
        model.addAttribute("error", error);
        return "login";
    }
}
