package com.example.ipoapi.services;

import com.example.ipoapi.daos.specification.ManageTransactionSpecification;
import com.example.ipoapi.dtos.ManageTransactionCriteriaDTO;
import com.example.ipoapi.dtos.ManageTransactionDTO;
import com.example.ipoapi.dtos.ManageTransactionResponseDTO;
import com.example.ipoapi.dtos.UserInfoResponseDTO;
import com.example.ipoapi.entities.TransactionEntity;
import com.example.ipoapi.entities.UserEntity;
import com.example.ipoapi.repositories.TransactionInterfaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.ipoapi.constants.Constants.PENDING;

@Service
@Slf4j(topic = "application")
public class ManageTransactionService {


    private final TransactionInterfaceRepository transactionInterfaceRepository;

    @Autowired
    public ManageTransactionService(TransactionInterfaceRepository transactionInterfaceRepository) {
        this.transactionInterfaceRepository = transactionInterfaceRepository;
    }

    public List<ManageTransactionResponseDTO> searchManageTransaction(ManageTransactionCriteriaDTO manageTransactionCriteriaDTO) {
        List<TransactionEntity> transactionEntities = transactionInterfaceRepository.findByAccountEntityBankAccountNameLikeAndStatusOrderById("%" + manageTransactionCriteriaDTO.getBankAccountName() + "%", PENDING);
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

    @Transactional
    public void updateTransaction(List<Integer> ids, ManageTransactionDTO manageTransactionDTO) {
        List<TransactionEntity> transactionEntities = transactionInterfaceRepository.findAllById(ids);
        if (transactionEntities.isEmpty()) {
            throw new NoResultException("Transaction is not found.");
        }
        for (TransactionEntity transactionEntity: transactionEntities) {
            transactionEntity.setStatus(manageTransactionDTO.getStatus());
            transactionInterfaceRepository.saveAndFlush(transactionEntity);
        }
    }
}
