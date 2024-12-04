package com.wirke.jobportal.services;

import org.springframework.stereotype.Service;

import com.wirke.jobportal.entity.JobPostActivity;
import com.wirke.jobportal.repository.JobPostActivityRepository;

@Service
public class JobPostActivityService {

    private final JobPostActivityRepository jobPostActivityRepository;
    
    public JobPostActivityService(JobPostActivityRepository jobPostActivityRepository) {
        this.jobPostActivityRepository = jobPostActivityRepository;
    }

    public JobPostActivity addNew(JobPostActivity jobPostActivity){
        
        return jobPostActivityRepository.save(jobPostActivity);
    }
}
