package com.wirke.jobportal.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wirke.jobportal.entity.JobPostActivity;
import com.wirke.jobportal.entity.JobSeekerProfile;
import com.wirke.jobportal.entity.JobSeekerSave;
import com.wirke.jobportal.repository.JobSeekerSaveRepository;

@Service
public class JobSeekerSaveService {
    
    private final JobSeekerSaveRepository jobSeekerSaveRepository;

    public JobSeekerSaveService(JobSeekerSaveRepository jobSeekerSaveRepository) {
        this.jobSeekerSaveRepository = jobSeekerSaveRepository;
    }

    public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userAccountId){
        return jobSeekerSaveRepository.findByUserId(userAccountId);
    }
    public List<JobSeekerSave> getJobCandidates(JobPostActivity job){
        return jobSeekerSaveRepository.findByJob(job);
    }
}
