package com.helltractor.mall.controller;

import com.helltractor.mall.service.UserClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserClientService userClientService;
    
    @GetMapping("/register")
    public String register(@RequestParam(defaultValue = "mall1@helltractor.top") String email,
                           @RequestParam(defaultValue = "password1") String password,
                           @RequestParam(defaultValue = "confirmPassword") String confirmPassword) {
        return this.userClientService.register(email, password, confirmPassword);
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(defaultValue = "mall@helltractor.top") String email,
                        @RequestParam(defaultValue = "password") String password) {
        return this.userClientService.login(email, password);
    }
    
}