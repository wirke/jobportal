package com.wirke.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wirke.jobportal.entity.RecruiterProfile;

public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile, Integer>{
    
}
