package com.wirke.jobportal.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.wirke.jobportal.entity.Users;
import com.wirke.jobportal.entity.UsersType;
import com.wirke.jobportal.services.UsersService;
import com.wirke.jobportal.services.UsersTypeService;

import jakarta.validation.Valid;

@Controller
public class UsersController {
    
    private final UsersTypeService usersTypeService;
    private final UsersService usersService;

    public UsersController(UsersTypeService usersTypeService, UsersService usersService) {
        this.usersTypeService = usersTypeService;
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String register(Model model){
        List<UsersType> usersTypes = usersTypeService.getAll();
        model.addAttribute("getAllTypes", usersTypes);
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid Users users){

        //System.out.println("User:: "+users);
        usersService.addNew(users);
        return "dashboard";
    }
}
