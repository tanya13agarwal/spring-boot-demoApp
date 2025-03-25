package com.telusko.demoApp.service;

import com.telusko.demoApp.model.UserPrinciple;
import com.telusko.demoApp.model.Users;
import com.telusko.demoApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    //Just becoz ye ek interface hai so we need to create a clas that implements it
    //UserPrinciple is the class that implements it
    //uska object return krr do
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByUsername(username);
        if(user == null) {
            System.out.println("user not found");
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrinciple(user);
    }
}
