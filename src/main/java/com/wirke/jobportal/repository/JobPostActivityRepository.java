package com.wirke.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wirke.jobportal.entity.JobPostActivity;

public interface JobPostActivityRepository extends JpaRepository<JobPostActivity, Integer>{
    

}
