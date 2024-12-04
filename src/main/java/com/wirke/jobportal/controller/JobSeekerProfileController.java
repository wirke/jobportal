package com.wirke.jobportal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wirke.jobportal.repository.UsersRepository;
import com.wirke.jobportal.services.JobSeekerProfileService;
import com.wirke.jobportal.entity.JobSeekerProfile;
import com.wirke.jobportal.entity.Skills;
import com.wirke.jobportal.entity.Users;

@Controller
@RequestMapping("/job-seeker-profile")
public class JobSeekerProfileController {
 
    private JobSeekerProfileService jobSeekerProfileService;
    private UsersRepository usersRepository;
    
    public JobSeekerProfileController(JobSeekerProfileService jobSeekerProfileService,
            UsersRepository usersRepository) {
        this.jobSeekerProfileService = jobSeekerProfileService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/")
    public String JobSeekerProfile(Model model){

        JobSeekerProfile jobSeekerProfile = new JobSeekerProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Skills> skills = new ArrayList<>();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            Users user = usersRepository.findByEmail((authentication.getName())).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

            Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUser_id());

            if(seekerProfile.isPresent()){
                jobSeekerProfile = seekerProfile.get();
                
                if(jobSeekerProfile.getSkills().isEmpty()){

                    skills.add(new Skills());
                    jobSeekerProfile.setSkills(skills);
                }
            }

            model.addAttribute("skills", skills);
            model.addAttribute("profile", jobSeekerProfile);
        }
        return "job-seeker-profile";
    }
}
