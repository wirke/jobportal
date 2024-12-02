package com.wirke.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wirke.jobportal.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer>{
    
    
}
