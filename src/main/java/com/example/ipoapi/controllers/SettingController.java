package com.example.ipoapi.controllers;

import com.example.ipoapi.dtos.ErrorResponseDTO;
import com.example.ipoapi.dtos.UserInfoDTO;
import com.example.ipoapi.services.SettingService;
import com.example.ipoapi.services.UserService;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j(topic = "application")
public class SettingController {

    private final UserService userService;

    private final SettingService settingService;

    @Autowired
    public SettingController(UserService userService, SettingService settingService) {
        this.userService = userService;
        this.settingService = settingService;
    }

    @GetMapping("/setting/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id ) {
        try {
            UserInfoDTO userInfo = settingService.getUserById(id);
            return ResponseEntity.ok().body(userInfo);
        } catch (NoResultException ex) {
            log.warn("Api GET : /setting/{} : Have Error {}, {}", id, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        } catch (Exception ex) {
            log.error("Api GET : /setting/{} : Have Error {}, {}", id, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }

}
