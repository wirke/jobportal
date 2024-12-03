package com.wirke.jobportal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping(name = "home")
    public String home(){
        return "index";
    }
}
