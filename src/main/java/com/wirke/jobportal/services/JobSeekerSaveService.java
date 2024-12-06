package com.wirke.jobportal.services;

import java.util.ConcurrentModificationException;
import java.util.List;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.wirke.jobportal.entity.JobPostActivity;
import com.wirke.jobportal.entity.JobSeekerProfile;
import com.wirke.jobportal.entity.JobSeekerSave;
import com.wirke.jobportal.repository.JobSeekerSaveRepository;

import jakarta.transaction.Transactional;

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

    @Transactional
    public void addNew(JobSeekerSave jobSeekerSave) {
        try {
            jobSeekerSaveRepository.save(jobSeekerSave);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new ConcurrentModificationException("This record was modified by someone else.");
    }
}
}
