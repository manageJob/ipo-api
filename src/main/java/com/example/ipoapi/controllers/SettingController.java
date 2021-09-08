package com.example.ipoapi.controllers;

import com.example.ipoapi.dtos.ErrorResponseDTO;
import com.example.ipoapi.dtos.NewPasswordDTO;
import com.example.ipoapi.dtos.UserInfoDTO;
import com.example.ipoapi.services.SettingService;
import com.example.ipoapi.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;

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
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
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

    @PutMapping("/setting/update-password/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable("id") Integer id, @RequestBody NewPasswordDTO newPasswordDTO) {
        try {
            Integer updatedPasswordId = settingService.updatePassword(id, newPasswordDTO);
            return ResponseEntity.ok().body(updatedPasswordId);
        } catch (NoResultException ex) {
            log.warn("Api GET : /setting/update-password/{} : Have Error {}, {}", id, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        } catch (Exception ex) {
            log.error("Api GET : /setting/update-password/{} : Have Error {}, {}", id, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }

    @PutMapping("/setting/update-user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Integer id, @RequestBody UserInfoDTO userInfoDTO) {
        try {
            Integer updatedId = settingService.updateUser(id, userInfoDTO);
            return ResponseEntity.ok().body(updatedId);
        } catch (NoResultException ex) {
            log.warn("Api GET : /setting/update-user/{} : Have Error {}, {}", id, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        } catch (Exception ex) {
            log.error("Api GET : /setting/update-user/{} : Have Error {}, {}", id, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }

}
