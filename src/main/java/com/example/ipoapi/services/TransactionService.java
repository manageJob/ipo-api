package com.example.ipoapi.services;

import com.example.ipoapi.dtos.OptionDTO;
import com.example.ipoapi.entities.UserEntity;
import com.example.ipoapi.repositories.UserInterfaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.ipoapi.constants.Constants.ROLE_MANAGER;
import static com.example.ipoapi.constants.Constants.ROLE_OPM;

@Service
@Slf4j(topic = "application")
public class TransactionService {

    private final UserInterfaceRepository userInterfaceRepository;

    @Autowired
    public TransactionService(UserInterfaceRepository userInterfaceRepository) {
        this.userInterfaceRepository = userInterfaceRepository;
    }

    public List<OptionDTO> getAccount() {
        return userInterfaceRepository.findByRoleIdInOrderById(new ArrayList<>(Arrays.asList(ROLE_MANAGER, ROLE_OPM))).stream().filter(c -> !c.getAccountEntity().getBankNumber().equalsIgnoreCase("") && !c.getAccountEntity().getBankAccountName().equalsIgnoreCase("")).map(c -> new OptionDTO(c.getAccountEntity().getBankAccountName(), String.valueOf(c.getAccountEntity().getBankNumber()))).collect(Collectors.toList());
    }
}
