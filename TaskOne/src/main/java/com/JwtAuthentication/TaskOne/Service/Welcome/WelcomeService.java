package com.JwtAuthentication.TaskOne.Service.Welcome;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.JwtAuthentication.TaskOne.Model.ServiceResponse;

@Service
public class WelcomeService implements IWelcomeService{

    @Override
    public ServiceResponse<String> getUsername(){
        var serviceResponse = new ServiceResponse<String>();
        
        serviceResponse.Data = SecurityContextHolder.getContext().getAuthentication().getName();
        serviceResponse.Message = "Successfully retrieved the username";
        
        return serviceResponse;
    }
    
}
