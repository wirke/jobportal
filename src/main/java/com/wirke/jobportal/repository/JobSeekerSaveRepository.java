package com.wirke.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wirke.jobportal.entity.JobPostActivity;
import com.wirke.jobportal.entity.JobSeekerProfile;
import com.wirke.jobportal.entity.JobSeekerSave;
import java.util.List;


@Repository
public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave, Integer>{
    
    List<JobSeekerSave> findByUserId(JobSeekerProfile userAccountId);
    
    List<JobSeekerSave> findByJob(JobPostActivity job);
}
