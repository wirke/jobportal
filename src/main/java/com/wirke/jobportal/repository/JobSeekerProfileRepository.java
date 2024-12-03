package com.wirke.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wirke.jobportal.entity.JobSeekerProfile;

public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile, Integer>{
    
}
