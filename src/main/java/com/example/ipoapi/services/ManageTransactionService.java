package com.example.ipoapi.services;

import com.example.ipoapi.daos.specification.ManageTransactionSpecification;
import com.example.ipoapi.dtos.ManageTransactionCriteriaDTO;
import com.example.ipoapi.dtos.ManageTransactionResponseDTO;
import com.example.ipoapi.dtos.UserInfoResponseDTO;
import com.example.ipoapi.entities.TransactionEntity;
import com.example.ipoapi.repositories.TransactionInterfaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "application")
public class ManageTransactionService {

    private final ManageTransactionSpecification manageTransactionSpecification;

    private final TransactionInterfaceRepository transactionInterfaceRepository;

    @Autowired
    public ManageTransactionService(ManageTransactionSpecification manageTransactionSpecification, TransactionInterfaceRepository transactionInterfaceRepository) {
        this.manageTransactionSpecification = manageTransactionSpecification;
        this.transactionInterfaceRepository = transactionInterfaceRepository;
    }

    public List<ManageTransactionResponseDTO> searchManageTransaction(ManageTransactionCriteriaDTO manageTransactionCriteriaDTO) {
        List<TransactionEntity> transactionEntities = transactionInterfaceRepository.findAll(manageTransactionSpecification.manageTransactionSpecification(manageTransactionCriteriaDTO), Sort.by(Sort.Direction.ASC, "name"));
        return transactionEntities.stream().map(t -> new ManageTransactionResponseDTO(
                t.getId(),
                t.getAccountEntity().getBankName(),
                t.getAccountEntity().getBankAccountName(),
                t.getAccountEntity().getBankNumber(),
                String.valueOf(t.getAmount()),
                t.getStatus(),
                t.getType()
        )).collect(Collectors.toList());
    }
}
