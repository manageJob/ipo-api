package com.example.ipoapi.controllers;

import com.example.ipoapi.dtos.ErrorResponseDTO;
import com.example.ipoapi.dtos.ManageTransactionCriteriaDTO;
import com.example.ipoapi.services.ManageTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.NoResultException;

@RestController
@Slf4j(topic = "application")
public class ManageTransactionController {

    private final ManageTransactionService manageTransactionService;

    @Autowired
    public ManageTransactionController(ManageTransactionService manageTransactionService) {
        this.manageTransactionService = manageTransactionService;
    }

    @GetMapping("/manage-transaction")
    public ResponseEntity<?> search(@RequestParam(name = "bankName", defaultValue = "") String bankName
    ) {
        ManageTransactionCriteriaDTO manageTransactionCriteriaDTO = ManageTransactionCriteriaDTO.builder()
                .bankName(bankName)
                .build();
        try {
            return ResponseEntity.ok(manageTransactionService.searchManageTransaction(manageTransactionCriteriaDTO));
        } catch (NoResultException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }
}
