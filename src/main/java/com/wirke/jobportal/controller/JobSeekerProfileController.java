package com.wirke.jobportal.controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wirke.jobportal.repository.UsersRepository;
import com.wirke.jobportal.services.JobSeekerProfileService;
import com.wirke.jobportal.util.FileUploadUtil;
import com.wirke.jobportal.entity.JobSeekerProfile;
import com.wirke.jobportal.entity.Skills;
import com.wirke.jobportal.entity.Users;

@Controller
@RequestMapping("/job-seeker-profile")
public class JobSeekerProfileController {
 
    private JobSeekerProfileService jobSeekerProfileService;
    private UsersRepository usersRepository;

    @Value("${file.upload-dir}")
    private String rootUploadDir;
    
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

    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException ex, Model model) {
        model.addAttribute("error", "Operation failed: " + ex.getMessage());
        return "error-page";
    }

    @SuppressWarnings("null")
    @PostMapping("/addNew")
    public String addNew(JobSeekerProfile jobSeekerProfile,
                         @RequestParam("image") MultipartFile image,
                         @RequestParam("pdf") MultipartFile pdf,
                         Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = usersRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            jobSeekerProfile.setUserId(user);
            jobSeekerProfile.setUserAccountId(user.getUser_id());
        }

        List<Skills> skillsList = new ArrayList<>();
        for (Skills skills : jobSeekerProfile.getSkills()) {
            skills.setJobSeekerProfile(jobSeekerProfile);
        }

        model.addAttribute("profile", jobSeekerProfile);
        model.addAttribute("skills", skillsList);

        String imageName = "";
        String resumeName = "";

        if (!image.isEmpty() && !image.getOriginalFilename().isEmpty()) {
            imageName = StringUtils.cleanPath(image.getOriginalFilename());
            jobSeekerProfile.setProfilePhoto(imageName);
        }

        if (!pdf.isEmpty() && !pdf.getOriginalFilename().isEmpty()) {
            resumeName = StringUtils.cleanPath(pdf.getOriginalFilename());
            jobSeekerProfile.setResume(resumeName);
        }

        JobSeekerProfile savedProfile = jobSeekerProfileService.addNew(jobSeekerProfile);

        try {
            String uploadDir = Paths.get(rootUploadDir, "candidate", 
                savedProfile.getUserAccountId().toString()).toString();

            if (!image.isEmpty() && !image.getOriginalFilename().isEmpty()) {
                FileUploadUtil.saveFile(uploadDir, imageName, image);
            }

            if (!pdf.isEmpty() && !pdf.getOriginalFilename().isEmpty()) {
                FileUploadUtil.saveFile(uploadDir, resumeName, pdf);
            }

            } catch (IOException ex) {

            throw new RuntimeException("Error saving files: " + ex.getMessage(), ex);
        }

        return "redirect:/dashboard/";
    }
}
