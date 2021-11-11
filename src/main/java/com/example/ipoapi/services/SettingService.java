package com.example.ipoapi.services;

import com.example.ipoapi.dtos.NewPasswordDTO;
import com.example.ipoapi.dtos.UserInfoDTO;
import com.example.ipoapi.entities.AccountEntity;
import com.example.ipoapi.entities.UserEntity;
import com.example.ipoapi.repositories.AccountInterfaceRepository;
import com.example.ipoapi.repositories.UserInterfaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.Base64;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@Service
public class SettingService  {

    private final UserInterfaceRepository userInterfaceRepository;

    private final AccountInterfaceRepository accountInterfaceRepository;

    @Autowired
    public SettingService(UserInterfaceRepository userInterfaceRepository, AccountInterfaceRepository accountInterfaceRepository) {
        this.userInterfaceRepository = userInterfaceRepository;
        this.accountInterfaceRepository = accountInterfaceRepository;
    }

    public UserInfoDTO getUserById(String userId) {
        byte[] decodedBytes = Base64.getDecoder().decode(userId);
        String decodedUserId = new String(decodedBytes);
        Optional<UserEntity> userEntityOptional = userInterfaceRepository.findById(parseInt(decodedUserId));
        if (userEntityOptional.isPresent()) {
            UserEntity userInfoDTO = userEntityOptional.get();
            return wrapperUserInfoDTO(userInfoDTO);
        } else {
            throw new NoResultException("User is not found.");
        }
    }

    private UserInfoDTO wrapperUserInfoDTO(UserEntity userEntity) {
        return new UserInfoDTO(String.valueOf(userEntity.getId()), userEntity.getName(), userEntity.getLastname(), userEntity.getUsername(),
                userEntity.getPassword(), userEntity.getTelephoneNumber(), userEntity.getAccountEntity().getBankName(), userEntity.getAccountEntity().getBankAccountName(), userEntity.getAccountEntity().getBankNumber());
    }

    @Transactional
    public Integer updatePassword(Integer id, NewPasswordDTO newPasswordDTO) {
        Optional<UserEntity> userEntityOptional = userInterfaceRepository.findById(id);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            userEntity.setPassword(newPasswordDTO.getNewPassword());
            return userInterfaceRepository.saveAndFlush(userEntity).getId();
        } else {
            throw new NoResultException("User is not found.");
        }
    }

    @Transactional
    public Integer updateUser(Integer id, UserInfoDTO userInfoDTO) {
        Optional<UserEntity> userEntityOptional = userInterfaceRepository.findById(id);
        if (userEntityOptional.isPresent()) {
            Optional<AccountEntity> accountEntityOptional = accountInterfaceRepository.findById(userEntityOptional.get().getAccountId());
            if (!accountEntityOptional.isPresent()) {
                throw new NoResultException("Account is not found.");
            }
            AccountEntity accountEntity = accountEntityOptional.get();
            accountEntity.setBankName(userInfoDTO.getBankName());
            accountEntity.setBankAccountName(userInfoDTO.getBankAccountName());
            accountEntity.setBankNumber(userInfoDTO.getBankNumber());
            accountInterfaceRepository.saveAndFlush(accountEntity);
            UserEntity userEntity = userEntityOptional.get();
            userEntity.setName(userInfoDTO.getName());
            userEntity.setLastname(userInfoDTO.getLastname());
            userEntity.setUsername(userInfoDTO.getUsername());
            userEntity.setPassword(userEntity.getPassword());
            userEntity.setTelephoneNumber(userInfoDTO.getTelephoneNumber());
            userEntity.setRoleEntity(userEntity.getRoleEntity());
            return userInterfaceRepository.saveAndFlush(userEntity).getId();
        } else {
            throw new NoResultException("User is not found.");
        }
    }
}