package com.JwtAuthentication.TaskOne.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JwtAuthentication.TaskOne.Service.Welcome.IWelcomeService;

@RestController
public class WelcomeController {
    
    @Autowired
    private IWelcomeService _welcomeService;

    @GetMapping("/hello")
    public ResponseEntity<?> welcome(){
        var response = _welcomeService.getUsername();
        return new ResponseEntity<>("Welcome " + response.Data, HttpStatus.OK);
    }
}
