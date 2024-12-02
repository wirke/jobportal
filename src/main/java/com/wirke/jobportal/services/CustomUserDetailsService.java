package com.wirke.jobportal.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wirke.jobportal.entity.Users;
import com.wirke.jobportal.repository.UsersRepository;
import com.wirke.jobportal.util.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    
    private final UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        Users user = usersRepository.findByEmail(username).orElseThrow(() 
            -> new UsernameNotFoundException("Could not find user"));
        return new CustomUserDetails(user);
    }
}
