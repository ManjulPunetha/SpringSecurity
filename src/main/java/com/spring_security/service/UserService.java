package com.spring_security.service;

import com.spring_security.model.User;
import com.spring_security.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class UserService
{
    @Autowired
    private UserRepository repository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public void addUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
    }

    public String verifyUser(User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(user.getUserName());
            } else {
                return "Login Not Successful";
            }
        } catch (BadCredentialsException e) {
            return "Incorrect credentials.";
        } catch (NoSuchAlgorithmException e) {
            return "Incorrect algorithm.";
        }
    }
}
