package com.wirke.jobportal.services;

import java.sql.Date;

import org.springframework.stereotype.Service;

import com.wirke.jobportal.entity.Users;
import com.wirke.jobportal.repository.UsersRepository;

@Service
public class UsersService {
    
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    public Users addNew(Users users){
        
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        return usersRepository.save(users);
    }
}
