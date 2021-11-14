package com.example.ipoapi.controllers;

import com.example.ipoapi.dtos.*;
import com.example.ipoapi.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.List;

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
    public ResponseEntity<?> getBankDetailById(@PathVariable("id") Integer id) {
        try {
            BankDetailDTO bankDetailDTO = transactionService.getBankDetailById(id);
            return ResponseEntity.ok().body(bankDetailDTO);
        } catch (NoResultException ex) {
            log.warn("Api GET : /transaction/{} : Have Error {}, {}", id, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        } catch (Exception ex) {
            log.error("Api GET : /transaction/{} : Have Error {}, {}", id, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }

    @PostMapping("/transaction")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        try {
            Integer createTransactionId = transactionService.createTransaction(transactionDTO);
            return ResponseEntity.ok().body(createTransactionId);
        } catch (NoResultException ex) {
            log.warn("Api POST : /transaction : Have Error {}, {}", ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        } catch (Exception ex) {
            log.error("Api POST : /transaction : Have Error {}, {}", ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }

    @GetMapping("/transaction/account/{accountId}")
    public ResponseEntity<?> getTransactionByAccountId(@PathVariable("accountId") String accountId) {
        try {
            List<TransactionDetailDTO> transactionDetailDTOS = transactionService.getTransactionByAccountId(accountId);
            return ResponseEntity.ok().body(transactionDetailDTOS);
        } catch (NoResultException ex) {
            log.warn("Api GET : /transaction/{} : Have Error {}, {}", accountId, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        } catch (Exception ex) {
            log.error("Api GET : /transaction/{} : Have Error {}, {}", accountId, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }

}
