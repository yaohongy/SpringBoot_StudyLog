package com.yaohongy.StudyLog.service;

import java.util.Optional;

import com.yaohongy.StudyLog.config.MyUserDetail;
import com.yaohongy.StudyLog.dao.UserRepo;
import com.yaohongy.StudyLog.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return new MyUserDetail(optionalUser.get());
        }
        else {
            throw new UsernameNotFoundException("User not exist");
        }
    }
    
}
