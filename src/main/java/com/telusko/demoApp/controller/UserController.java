package com.telusko.demoApp.controller;

import com.telusko.demoApp.model.Users;
import com.telusko.demoApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/")
    public Users register(@RequestBody Users user) {
        return service.registerUser(user);
    }

    @GetMapping("/hello")
    public List<Users> hello() {
        return service.getUsers();
    }

    @PostMapping("/login")
    public String verify(@RequestBody Users user) {
        return service.verify(user);
    }
}
