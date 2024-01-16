package com.JwtAuthentication.TaskOne.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.JwtAuthentication.TaskOne.Model.JwtRequestModel;
import com.JwtAuthentication.TaskOne.Model.JwtResponseModel;
import com.JwtAuthentication.TaskOne.Service.Authenticate.IAuthenticateService;

@RestController
public class authController {
    
    @Autowired
    private IAuthenticateService _authenticateService;

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponseModel> login(@RequestBody JwtRequestModel jwtRequestModel){

        var serviceResponse = _authenticateService.authenticate(jwtRequestModel);

        return new ResponseEntity<JwtResponseModel>(new JwtResponseModel(serviceResponse.Data), HttpStatus.OK);
        
    }

}
