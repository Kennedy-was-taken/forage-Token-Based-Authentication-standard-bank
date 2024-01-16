package com.JwtAuthentication.TaskOne.Service.Authenticate;

import com.JwtAuthentication.TaskOne.Model.JwtRequestModel;
import com.JwtAuthentication.TaskOne.Model.ServiceResponse;

public interface IAuthenticateService {
    
    public ServiceResponse<String> authenticate(JwtRequestModel jwtRequestModel);
}
