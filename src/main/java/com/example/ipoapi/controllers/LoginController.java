package com.example.ipoapi.controllers;

import com.example.ipoapi.dtos.LoginDTO;
import com.example.ipoapi.entities.LoginEntity;
import com.example.ipoapi.services.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j(topic = "application")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;

    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") Integer id) {
        LoginDTO loginDTO = loginService.getById(id);
        return ResponseEntity.ok().body(loginDTO);
    }

}
