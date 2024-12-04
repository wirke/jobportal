package com.wirke.jobportal.controller;

import java.sql.Date;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.wirke.jobportal.entity.JobPostActivity;
import com.wirke.jobportal.entity.Users;
import com.wirke.jobportal.services.JobPostActivityService;
import com.wirke.jobportal.services.UsersService;

@Controller
public class JobPostActivityController {
    
    private final UsersService usersService;
    private final JobPostActivityService jobPostActivityService;



    public JobPostActivityController(UsersService usersService, JobPostActivityService jobPostActivityService) {
        this.usersService = usersService;
        this.jobPostActivityService = jobPostActivityService;
    }

    @GetMapping("/dashboard/")
    public String searchJobs(Model model){

        Object currentUserProfile = usersService.getCurrentUserProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            model.addAttribute("username", currentUsername);
        }

        if (currentUserProfile != null) {
            model.addAttribute("user", currentUserProfile);
        } else {
            model.addAttribute("user", null);
        }

        return "dashboard";
    }

    @GetMapping("/dashboard/add")
    public String addJobs(Model model){

        model.addAttribute("jobPostActivity", new JobPostActivity());
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "add_jobs";
    }

    @PostMapping("/dashboard/addNew")
    public String addNew(JobPostActivity jobPostActivity, Model model){

        Users user = usersService.getCurrentUser();

        if(user!=null){
            jobPostActivity.setPostedById(user);
        }

        jobPostActivity.setPostedDate(new Date(0));
        model.addAttribute("jobPostActivity", jobPostActivity);

        JobPostActivity saved = jobPostActivityService.addNew(jobPostActivity);
        return "redirect:/dashboard/";
    }
}

