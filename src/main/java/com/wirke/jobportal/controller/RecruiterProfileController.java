package com.wirke.jobportal.controller;

import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wirke.jobportal.entity.RecruiterProfile;
import com.wirke.jobportal.entity.Users;
import com.wirke.jobportal.repository.UsersRepository;
import com.wirke.jobportal.services.RecruiterProfileService;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {
    
    private final UsersRepository usersRepository;
    private final RecruiterProfileService recruiterProfileService;


    public RecruiterProfileController(UsersRepository usersRepository,
            RecruiterProfileService recruiterProfileService) {
        this.usersRepository = usersRepository;
        this.recruiterProfileService = recruiterProfileService;
    }

    @GetMapping
    public String recruiterProfile(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if(!(authentication instanceof AnonymousAuthenticationToken)){

            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(() ->
                new UsernameNotFoundException("Could not find user"));
            
                Optional<RecruiterProfile> recruiterProfile = recruiterProfileService
                    .getOne(users.getUser_id());

                if (!recruiterProfile.isEmpty()){
                    model.addAttribute("profile", recruiterProfile.get());
                }
        }
        return "recruiter-profile";
    }
}
