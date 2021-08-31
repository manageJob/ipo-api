package com.example.ipoapi.services;

import com.example.ipoapi.dtos.UserInfoDTO;
import com.example.ipoapi.repositories.UserInterfaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public MyUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfoDTO userInfo =  userService.getUserByUser(username);
        return new User(userInfo.getUsername(), userInfo.getPassword(), new ArrayList<>());
    }
}