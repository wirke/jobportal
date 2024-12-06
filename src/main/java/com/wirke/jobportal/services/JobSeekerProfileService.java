package com.wirke.jobportal.services;

import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wirke.jobportal.entity.JobSeekerProfile;
import com.wirke.jobportal.entity.Users;
import com.wirke.jobportal.repository.JobSeekerProfileRepository;
import com.wirke.jobportal.repository.UsersRepository;

@Service
public class JobSeekerProfileService {

    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final UsersRepository usersRepository;

    public JobSeekerProfileService(JobSeekerProfileRepository jobSeekerProfileRepository,
                                    UsersRepository usersRepository) {
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.usersRepository = usersRepository;
    }

    public Optional<JobSeekerProfile> getOne(Integer id){
        return jobSeekerProfileRepository.findById(id);
    }

    public JobSeekerProfile addNew(JobSeekerProfile jobSeekerProfile) {
        return jobSeekerProfileRepository.save(jobSeekerProfile);
    }

    public JobSeekerProfile getCurrentSeekerProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUser = authentication.getName();
            Users user = usersRepository.findByEmail(currentUser).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));
            
            Optional<JobSeekerProfile> seekerProfile = getOne(user.getUser_id());
            return seekerProfile.orElse(null);
        } else return null;
    }
}
