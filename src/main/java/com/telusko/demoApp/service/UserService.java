package com.telusko.demoApp.service;

import com.telusko.demoApp.model.Users;
import com.telusko.demoApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private JWTService jwtService;

    //For taking control of Authentication we need AuthenticationManager object
    @Autowired
    private AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users registerUser(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public String verify(Users user) {
        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                            user.getUsername(), user.getPassword()
                ));

        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        }

        return "Failure";
    }

    public List<Users> getUsers() {
        return repo.findAll();
    }
}
