package com.example.ipoapi.services;

import com.example.ipoapi.dtos.LoginDTO;
import com.example.ipoapi.entities.LoginEntity;
import com.example.ipoapi.repositories.LoginInterfaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Service
@Slf4j(topic = "application")
public class LoginService {

    private final LoginInterfaceRepository loginInterfaceRepository;

    @Autowired
    public LoginService(LoginInterfaceRepository loginInterfaceRepository) {
        this.loginInterfaceRepository = loginInterfaceRepository;

    }

    public LoginDTO getById(Integer id) {
        Optional<LoginEntity> loginEntityOptional = loginInterfaceRepository.findById(id);
        if (loginEntityOptional.isPresent()) {
            LoginEntity loginEntity = loginEntityOptional.get();
            return wrapperLoginDTO(loginEntity);
        }
        return null;
    }

    private LoginDTO wrapperLoginDTO(LoginEntity loginEntity) {
        return new LoginDTO(loginEntity.getName(),loginEntity.getLastname());
    }
}
