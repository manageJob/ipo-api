package com.example.ipoapi.services;

import com.example.ipoapi.dtos.*;
import com.example.ipoapi.entities.AccountEntity;
import com.example.ipoapi.repositories.AccountInterfaceRepository;
import com.example.ipoapi.repositories.UserInterfaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.ipoapi.constants.Constants.ROLE_MANAGER;
import static com.example.ipoapi.constants.Constants.ROLE_OPM;
import static java.lang.Integer.parseInt;

@Service
@Slf4j(topic = "application")
public class TransactionService {

    private final UserInterfaceRepository userInterfaceRepository;

    private final AccountInterfaceRepository accountInterfaceRepository;

    @Autowired
    public TransactionService(UserInterfaceRepository userInterfaceRepository, AccountInterfaceRepository accountInterfaceRepository) {
        this.userInterfaceRepository = userInterfaceRepository;
        this.accountInterfaceRepository = accountInterfaceRepository;
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

}
