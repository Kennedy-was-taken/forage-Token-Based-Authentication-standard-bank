package com.JwtAuthentication.TaskOne.Service.Welcome;

import com.JwtAuthentication.TaskOne.Model.ServiceResponse;

public interface IWelcomeService {
    
    public ServiceResponse<String> getUsername();
}
