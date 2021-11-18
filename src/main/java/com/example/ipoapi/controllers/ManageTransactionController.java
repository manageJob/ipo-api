package com.example.ipoapi.controllers;

import com.example.ipoapi.dtos.ErrorResponseDTO;
import com.example.ipoapi.dtos.ManageTransactionCriteriaDTO;
import com.example.ipoapi.dtos.ManageTransactionDTO;
import com.example.ipoapi.dtos.NewsDTO;
import com.example.ipoapi.services.ManageTransactionService;
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
public class ManageTransactionController {

    private final ManageTransactionService manageTransactionService;

    @Autowired
    public ManageTransactionController(ManageTransactionService manageTransactionService) {
        this.manageTransactionService = manageTransactionService;
    }

    @GetMapping("/manage-transaction")
    public ResponseEntity<?> search(@RequestParam(name = "bankAccountName", defaultValue = "") String bankAccountName
    ) {
        ManageTransactionCriteriaDTO manageTransactionCriteriaDTO = ManageTransactionCriteriaDTO.builder()
                .bankAccountName(bankAccountName)
                .build();
        try {
            return ResponseEntity.ok(manageTransactionService.searchManageTransaction(manageTransactionCriteriaDTO));
        } catch (NoResultException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }

    @PutMapping("/manage-transaction")
    public ResponseEntity<?> updateTransaction(@RequestParam(name = "ids", defaultValue = "") List<Integer> ids, @RequestBody ManageTransactionDTO manageTransactionDTO) {
        String idString = ids.stream().map(Objects::toString).collect(Collectors.joining(","));
        try {
            manageTransactionService.updateTransaction(ids, manageTransactionDTO);
            return ResponseEntity.ok().build();
        } catch (NoResultException ex) {
            log.warn("Api PUT : /manage-transaction : Have Error {}, {} with ids: {}", idString, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        } catch (Exception ex) {
            log.warn("Api PUT : /manage-transaction : Have Error {}, {} with ids: {}", idString, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }
}
