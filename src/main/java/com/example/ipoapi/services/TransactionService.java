package com.example.ipoapi.services;

import com.example.ipoapi.dtos.*;
import com.example.ipoapi.entities.AccountEntity;
import com.example.ipoapi.entities.NewsEntity;
import com.example.ipoapi.entities.TransactionEntity;
import com.example.ipoapi.entities.UserEntity;
import com.example.ipoapi.repositories.AccountInterfaceRepository;
import com.example.ipoapi.repositories.TransactionInterfaceRepository;
import com.example.ipoapi.repositories.UserInterfaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.ipoapi.constants.Constants.*;
import static java.lang.Integer.parseInt;

@Service
@Slf4j(topic = "application")
public class TransactionService {

    private final UserInterfaceRepository userInterfaceRepository;

    private final AccountInterfaceRepository accountInterfaceRepository;

    private final TransactionInterfaceRepository transactionInterfaceRepository;

    @Autowired
    public TransactionService(UserInterfaceRepository userInterfaceRepository, AccountInterfaceRepository accountInterfaceRepository, TransactionInterfaceRepository transactionInterfaceRepository) {
        this.userInterfaceRepository = userInterfaceRepository;
        this.accountInterfaceRepository = accountInterfaceRepository;
        this.transactionInterfaceRepository = transactionInterfaceRepository;
    }

    public List<OptionDTO> getAccount() {
        return userInterfaceRepository.findByRoleIdInOrderById(new ArrayList<>(Arrays.asList(ROLE_MANAGER, ROLE_OPM))).stream().filter(c -> !c.getAccountEntity().getBankNumber().equalsIgnoreCase("") && !c.getAccountEntity().getBankAccountName().equalsIgnoreCase("")).map(c -> new OptionDTO(c.getAccountEntity().getBankAccountName(), String.valueOf(c.getAccountEntity().getId()))).collect(Collectors.toList());
    }

    public BankDetailDTO getBankDetailById(Integer accountId) {
        Optional<AccountEntity> accountEntityOptional = accountInterfaceRepository.findById(accountId);
        if (accountEntityOptional.isPresent()) {
            AccountEntity accountEntity = accountEntityOptional.get();
            return wrapperBankDetailDTO(accountEntity);
        } else {
            throw new NoResultException("Account is not found.");
        }
    }

    private BankDetailDTO wrapperBankDetailDTO(AccountEntity accountEntity) {
        return new BankDetailDTO(accountEntity.getBankName(), accountEntity.getBankNumber());
    }

    @Transactional
    public Integer createTransaction(TransactionDTO transactionDTO) {
        TransactionEntity transactionEntity = new TransactionEntity();
        if (transactionDTO.getType().equalsIgnoreCase(WITHDRAW)) {
            byte[] decodedBytes = Base64.getDecoder().decode(String.valueOf(transactionDTO.getAccountId()));
            String decodedAccountId = new String(decodedBytes);
            transactionDTO.setAccountId(decodedAccountId);
        }
        transactionEntity.setAccountId(parseInt(transactionDTO.getAccountId()));
        transactionEntity.setAmount(parseInt(transactionDTO.getAmount()));
        transactionEntity.setType(transactionDTO.getType());
        transactionEntity.setStatus("Pending");
        transactionEntity.setTransactionTime(transactionDTO.getTransactionTime());
        return transactionInterfaceRepository.saveAndFlush(transactionEntity).getId();
    }

    public List<TransactionDetailDTO> getTransactionByAccountId(String accountId) {
      List<TransactionEntity> transactionEntities = transactionInterfaceRepository.findByAccountIdOrderById(parseInt(accountId));
        if (transactionEntities.size() == 0) {
            throw new NoResultException("Transaction is not found.");
        } else {
            return wrapperTransactionDTO(transactionEntities);
        }
    }

    private List<TransactionDetailDTO> wrapperTransactionDTO(List<TransactionEntity> transactionEntities) {
        List<TransactionDetailDTO> transactionDetailDTOS = new ArrayList<>();
        for(TransactionEntity transactionEntity: transactionEntities) {
            TransactionDetailDTO transactionDetailDTO = new TransactionDetailDTO();
            transactionDetailDTO.setAmount(String.valueOf(transactionEntity.getAmount()));
            transactionDetailDTO.setStatus(transactionEntity.getStatus());
            transactionDetailDTO.setType(transactionEntity.getType());
            transactionDetailDTO.setTransactionTime(transactionEntity.getTransactionTime());
            transactionDetailDTOS.add(transactionDetailDTO);
        }
        return transactionDetailDTOS;
    }
}
