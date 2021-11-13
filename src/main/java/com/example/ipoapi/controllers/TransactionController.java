package com.example.ipoapi.controllers;

import com.example.ipoapi.dtos.BankNumberDTO;
import com.example.ipoapi.dtos.ErrorResponseDTO;
import com.example.ipoapi.dtos.ManageUserDTO;
import com.example.ipoapi.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Slf4j(topic = "application")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transaction/account")
    public ResponseEntity<?> getAccount() {
        return ResponseEntity.ok().body(transactionService.getAccount());
    }

    @GetMapping("/transaction/{id}")
    public ResponseEntity<?> getBankNumberById(@PathVariable("id") Integer id) {
        try {
            BankNumberDTO bankNumberDTO = transactionService.getBankNumberById(id);
            return ResponseEntity.ok().body(bankNumberDTO);
        } catch (NoResultException ex) {
            log.warn("Api GET : /transaction/{} : Have Error {}, {}", id, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        } catch (Exception ex) {
            log.error("Api GET : /transaction/{} : Have Error {}, {}", id, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }

}
