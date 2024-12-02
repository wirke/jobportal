package com.wirke.jobportal.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wirke.jobportal.entity.UsersType;
import com.wirke.jobportal.repository.UsersTypeRepository;

@Service
public class UsersTypeService {
    
    private final UsersTypeRepository usersTypeRepository;

    public UsersTypeService(UsersTypeRepository usersTypeRepository) {
        this.usersTypeRepository = usersTypeRepository;
    }

    public List<UsersType> getAll(){
        return usersTypeRepository.findAll();
    }
}
