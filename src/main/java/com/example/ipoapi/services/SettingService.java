package com.example.ipoapi.services;

import com.example.ipoapi.dtos.UserInfoDTO;
import com.example.ipoapi.entities.UserEntity;
import com.example.ipoapi.repositories.UserInterfaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
}