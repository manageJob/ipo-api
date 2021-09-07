package com.example.ipoapi.controllers;

import com.example.ipoapi.dtos.JwtDTO;
import com.example.ipoapi.dtos.UserInfoDTO;
import com.example.ipoapi.models.JwtRequest;
import com.example.ipoapi.services.UserService;
import com.example.ipoapi.services.MyUserDetailsService;
import com.example.ipoapi.utils.JwtUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.util.Base64;

@RestController
@Slf4j(topic = "application")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtility jwtUtility;

    @PostMapping("/authenticate")
    public JwtDTO authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Credentials", e);
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtUtility.generateToken(userDetails);
        UserInfoDTO userInfo =  userService.getUserByUsername(jwtRequest.getUsername());
        final String UserId = URLEncoder.encode(userInfo.getId(), java.nio.charset.StandardCharsets.UTF_8.toString());
        String encodedUserId = Base64.getEncoder().encodeToString(UserId.getBytes());
//        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
//        String decodedString = new String(decodedBytes);
        return new JwtDTO(token, encodedUserId);
    }

}
