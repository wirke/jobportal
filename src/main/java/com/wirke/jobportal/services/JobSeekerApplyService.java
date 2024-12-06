package com.wirke.jobportal.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wirke.jobportal.entity.JobPostActivity;
import com.wirke.jobportal.entity.JobSeekerApply;
import com.wirke.jobportal.entity.JobSeekerProfile;
import com.wirke.jobportal.repository.JobSeekerApplyRepository;

@Service
public class JobSeekerApplyService {
    
    private final JobSeekerApplyRepository jobSeekerApplyRepository;

    public JobSeekerApplyService(JobSeekerApplyRepository jobSeekerApplyRepository) {
        this.jobSeekerApplyRepository = jobSeekerApplyRepository;
    }

    public List<JobSeekerApply> getCandidatesJobs(JobSeekerProfile userAccoundId){
        return jobSeekerApplyRepository.findByUserId(userAccoundId);
    }
    
    public List<JobSeekerApply> getJobCandidates(JobPostActivity job){
        return jobSeekerApplyRepository.findByJob(job);
    }

    public void addNew(JobSeekerApply jobSeekerApply) {
        jobSeekerApplyRepository.save(jobSeekerApply);
    }
}
