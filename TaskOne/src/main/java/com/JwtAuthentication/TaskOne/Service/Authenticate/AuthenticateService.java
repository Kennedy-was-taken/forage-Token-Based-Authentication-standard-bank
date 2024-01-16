package com.JwtAuthentication.TaskOne.Service.Authenticate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.JwtAuthentication.TaskOne.Jwt.JwtTokenUtil;
import com.JwtAuthentication.TaskOne.Model.JwtRequestModel;
import com.JwtAuthentication.TaskOne.Model.ServiceResponse;
import com.JwtAuthentication.TaskOne.Service.JwtUserDetailService;

@Service
public class AuthenticateService implements IAuthenticateService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailService jwtUserDetailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public ServiceResponse<String> authenticate(JwtRequestModel jwtRequestModel) {

        var serviceResponse = new ServiceResponse<String>();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequestModel.getUsername(), jwtRequestModel.getPassword()));
        UserDetails userDetails = jwtUserDetailService.loadUserByUsername(jwtRequestModel.getUsername());

        serviceResponse.setData(jwtTokenUtil.generateToken(userDetails));
        serviceResponse.Message = "Jwt token created";

        return serviceResponse;
    }
    
}
