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


    private final TransactionInterfaceRepository transactionInterfaceRepository;

    @Autowired
    public ManageTransactionService(TransactionInterfaceRepository transactionInterfaceRepository) {
        this.transactionInterfaceRepository = transactionInterfaceRepository;
    }

    public List<ManageTransactionResponseDTO> searchManageTransaction(ManageTransactionCriteriaDTO manageTransactionCriteriaDTO) {
        List<TransactionEntity> transactionEntities = transactionInterfaceRepository.findByAccountEntityBankAccountNameLikeOrderById("%" + manageTransactionCriteriaDTO.getBankAccountName() + "%");
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
