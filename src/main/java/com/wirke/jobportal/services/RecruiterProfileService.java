package com.wirke.jobportal.services;

import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wirke.jobportal.entity.RecruiterProfile;
import com.wirke.jobportal.entity.Users;
import com.wirke.jobportal.repository.RecruiterProfileRepository;
import com.wirke.jobportal.repository.UsersRepository;

@Service
public class RecruiterProfileService {
    
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final UsersRepository usersRepository;

    public RecruiterProfileService(RecruiterProfileRepository recruiterProfileRepository,
                                UsersRepository usersRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.usersRepository = usersRepository;
    }

    public Optional<RecruiterProfile> getOne(Integer id){

        return recruiterProfileRepository.findById(id);
    }

    public RecruiterProfile addNew(RecruiterProfile recruiterProfile){

        return recruiterProfileRepository.save(recruiterProfile);
    }

    public RecruiterProfile getCurrentRecruiterProfile() {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUser = authentication.getName();
            Users user = usersRepository.findByEmail(currentUser).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));
            
            Optional<RecruiterProfile> recruiterProfile = getOne(user.getUser_id());
            return recruiterProfile.orElse(null);
        } else return null;
    }
}
