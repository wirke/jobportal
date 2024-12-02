package com.wirke.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wirke.jobportal.entity.UsersType;

public interface UsersTypeRepository extends JpaRepository<UsersType, Integer>{
    
}
