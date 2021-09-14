package com.example.ipoapi.controllers;

import com.example.ipoapi.dtos.*;
import com.example.ipoapi.services.ManageUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.List;

@RestController
@Slf4j(topic = "application")
public class ManageUserController {

    private final ManageUserService manageUserService;

    @Autowired
    public ManageUserController(ManageUserService manageUserService) {
        this.manageUserService = manageUserService;
    }

    @GetMapping("/manage-user")
    public ResponseEntity<?> search(@RequestParam(name = "name", defaultValue = "") String name,
                                    @RequestParam(name = "lastname", defaultValue = "") String lastname
    ) {
        ManageUserCriteriaDTO manageUserCriteriaDTO = ManageUserCriteriaDTO.builder()
                .name(name)
                .lastname(lastname)
                .build();
        try {
            return ResponseEntity.ok(manageUserService.searchManageUser(manageUserCriteriaDTO));
        } catch (NoResultException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }

    @GetMapping("/manage-user/role")
    public ResponseEntity<?> getRole(String requestUsername) {
        return ResponseEntity.ok().body(manageUserService.getRole());
    }

    @PostMapping("/manage-user")
    public ResponseEntity<?> createUser(@RequestBody ManageUserDTO manageUserDTO) {
        try {
            Integer createUserId = manageUserService.createUser(manageUserDTO);
            return ResponseEntity.ok().body(createUserId);
        } catch (NoResultException ex) {
            log.warn("Api GET : /manage-user : Have Error {}, {}", ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        } catch (Exception ex) {
            log.error("Api GET : /manage-user : Have Error {}, {}", ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }

    @PutMapping("/manage-user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Integer id, @RequestBody ManageUserDTO manageUserDTO) {
        try {
            Integer updatedId = manageUserService.updateUser(id, manageUserDTO);
            return ResponseEntity.ok().body(updatedId);
        } catch (NoResultException ex) {
            log.warn("Api GET : /manage-user/{} : Have Error {}, {}", id, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        } catch (Exception ex) {
            log.error("Api GET : /manage-user/{} : Have Error {}, {}", id, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }

    @PutMapping("/manage-user-password/{id}")
    public ResponseEntity<?> updateUserPassword(@PathVariable("id") Integer id) {
        try {
            Integer updatedId = manageUserService.updateUserPassword(id);
            return ResponseEntity.ok().body(updatedId);
        } catch (NoResultException ex) {
            log.warn("Api GET : /manage-user-password/{} : Have Error {}, {}", id, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        } catch (Exception ex) {
            log.error("Api GET : /manage-user-password/{} : Have Error {}, {}", id, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }

    @GetMapping("/manage-user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        try {
            ManageUserDTO manageUserDTO = manageUserService.getUserById(id);
            return ResponseEntity.ok().body(manageUserDTO);
        } catch (NoResultException ex) {
            log.warn("Api GET : /manage-user-password/{} : Have Error {}, {}", id, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        } catch (Exception ex) {
            log.error("Api GET : /manage-user-password/{} : Have Error {}, {}", id, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }

    @DeleteMapping("/manage-user")
    public ResponseEntity<?> delete(@RequestParam(name = "ids", defaultValue = "") List<Integer> ids) {
        try {
            manageUserService.delete(ids);
            return ResponseEntity.ok().build();
        } catch (NoResultException ex) {
            log.warn("Api DELETE : /manage-user : Have Error {}, {} with ids: {}", ids, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        } catch (Exception ex) {
            log.warn("Api DELETE : /manage-user : Have Error {}, {} with ids: {}", ids, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }
}
