package com.example.ipoapi.services;

import com.example.ipoapi.dtos.LoginDTO;
import com.example.ipoapi.dtos.UserInfoDTO;
import com.example.ipoapi.entities.UserEntity;
import com.example.ipoapi.repositories.UserInterfaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j(topic = "application")
public class UserService {

    private final UserInterfaceRepository userInterfaceRepository;

    @Autowired
    public UserService(UserInterfaceRepository userInterfaceRepository) {
        this.userInterfaceRepository = userInterfaceRepository;

    }

    public LoginDTO getById(Integer id) {
        Optional<UserEntity> userEntityOptional = userInterfaceRepository.findById(id);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            return wrapperLoginDTO(userEntity);
        }
        return null;
    }

    private LoginDTO wrapperLoginDTO(UserEntity userEntity) {
        return new LoginDTO(userEntity.getName(), userEntity.getLastname());
    }

    public UserInfoDTO getUserByUser(String username) {
        Optional<UserEntity> userEntityOptional = userInterfaceRepository.findByUser(username);
        if (userEntityOptional.isPresent()) {
            UserEntity userInfoDTO = userEntityOptional.get();
            return wrapperUserInfoDTO(userInfoDTO);
        }
        return null;
    }

    private UserInfoDTO wrapperUserInfoDTO(UserEntity userEntity) {
        return new UserInfoDTO(userEntity.getName(), userEntity.getLastname(), userEntity.getUser(), userEntity.getPassword());
    }
}
