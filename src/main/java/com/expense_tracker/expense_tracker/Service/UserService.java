package com.expense_tracker.expense_tracker.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.expense_tracker.expense_tracker.Model.Users;
import com.expense_tracker.expense_tracker.Repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository UserRepository;

    @Autowired
    AuthenticationManager authManager;
    
    @Autowired
    private JWTservice jwtservice;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserRepository.save(user);
    }

    public String verify(Users user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated())
        {
            return jwtservice.generateToken(user.getUsername());
            //return "Success";
        }

        return "Fail";
    }
}
