package com.wirke.jobportal.services;

import java.sql.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wirke.jobportal.entity.JobSeekerProfile;
import com.wirke.jobportal.entity.RecruiterProfile;
import com.wirke.jobportal.entity.Users;
import com.wirke.jobportal.repository.JobSeekerProfileRepository;
import com.wirke.jobportal.repository.RecruiterProfileRepository;
import com.wirke.jobportal.repository.UsersRepository;

@Service
public class UsersService {
    
    private final UsersRepository usersRepository;
    private final RecruiterProfileRepository recruiterRepository;
    private final JobSeekerProfileRepository jobSeekerRepository;

    public UsersService(UsersRepository usersRepository,
            RecruiterProfileRepository recruiterRepository,
            JobSeekerProfileRepository jobSeekerRepository){
        this.usersRepository = usersRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.recruiterRepository = recruiterRepository;
    }

    public Users addNew(Users users){
        
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        Users savedUser = usersRepository.save(users);

        int userTypeId = users.getUserTypeId().getUserTypeId();
        
        if(userTypeId == 1){
            recruiterRepository.save(new RecruiterProfile(savedUser));
        } else {
            jobSeekerRepository.save(new JobSeekerProfile(savedUser));
        }

        return savedUser;
    }

    public Optional<Users> getUserByEmail(String email){
        return usersRepository.findByEmail(email);
    }
}
