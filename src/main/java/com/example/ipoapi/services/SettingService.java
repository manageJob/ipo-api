package com.example.ipoapi.services;

import com.example.ipoapi.dtos.NewPasswordDTO;
import com.example.ipoapi.dtos.UserInfoDTO;
import com.example.ipoapi.entities.UserEntity;
import com.example.ipoapi.repositories.UserInterfaceRepository;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@Service
public class SettingService  {

    private final UserInterfaceRepository userInterfaceRepository;

    @Autowired
    public SettingService(UserInterfaceRepository userInterfaceRepository) {
        this.userInterfaceRepository = userInterfaceRepository;
    }

    public UserInfoDTO getUserById(String userId) {
        byte[] decodedBytes = Base64.getDecoder().decode(userId);
        String decodedUserId = new String(decodedBytes);
        Optional<UserEntity> userEntityOptional = userInterfaceRepository.findById(parseInt(decodedUserId));
        if (userEntityOptional.isPresent()) {
            UserEntity userInfoDTO = userEntityOptional.get();
            return wrapperUserInfoDTO(userInfoDTO);
        }
        return null;
    }

    private UserInfoDTO wrapperUserInfoDTO(UserEntity userEntity) {
        return new UserInfoDTO(String.valueOf(userEntity.getId()), userEntity.getName(), userEntity.getLastname(), userEntity.getUsername(),
                userEntity.getPassword(), userEntity.getTelephoneNumber(), userEntity.getBankName(), userEntity.getBankNumber());
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
            UserEntity userEntity = userEntityOptional.get();
            userEntity.setName(userInfoDTO.getName());
            userEntity.setLastname(userInfoDTO.getLastname());
            userEntity.setUsername(userInfoDTO.getUsername());
            userEntity.setTelephoneNumber(userInfoDTO.getTelephoneNumber());
            userEntity.setBankName(userInfoDTO.getBankName());
            userEntity.setBankNumber(userInfoDTO.getBankNumber());
            return userInterfaceRepository.saveAndFlush(userEntity).getId();
        } else {
            throw new NoResultException("User is not found.");
        }
    }
}