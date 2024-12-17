package com.royalmade.controller;



import com.royalmade.entity.Admin;
import com.royalmade.entity.JwtRequest;
import com.royalmade.entity.JwtResponse;
import com.royalmade.service.JwtService;
import com.royalmade.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
   private UserService userService;

    @Autowired
    private JwtService jwtService;


    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }
    @PostMapping({"/registerAdmin"})
    public Admin registerNewAdmin(@RequestBody Admin admin) {
        return userService.registerAdmin(admin);
    }

    //Login
    @PostMapping("/auth/login")
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {

        return jwtService.createJwtToken(jwtRequest);
    }
}