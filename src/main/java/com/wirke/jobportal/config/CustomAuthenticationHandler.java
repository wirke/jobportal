package com.wirke.jobportal.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationHandler implements AuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        System.out.println(username + " is logged in");

        boolean hasJobSeekerRole = authentication.getAuthorities().stream().anyMatch
                        (r->r.getAuthority().equals("Job Seeker"));

        boolean hasRecruiterrRole = authentication.getAuthorities().stream().anyMatch
                        (r->r.getAuthority().equals("Recruiter"));

        if (hasJobSeekerRole || hasRecruiterrRole){
            response.sendRedirect("/dashboard/");
        }
    }
}
