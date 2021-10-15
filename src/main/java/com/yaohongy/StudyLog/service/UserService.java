package com.yaohongy.StudyLog.service;

import com.yaohongy.StudyLog.entities.*;
import com.yaohongy.StudyLog.dao.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service("userService")
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;    

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void delete(User user) { 
        userRepo.delete(user);
    }

    public User save(User user) {
        //Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public boolean usernameCheck(String username) {
        Optional<User> optionalUser = userRepo.findByUsername(username);
        return optionalUser.isPresent();
    }

    public Page<User> findAllByPage(int page, int perPage) {
        return userRepo.findAllByOrderById(PageRequest.of(page, perPage));
    }
}
