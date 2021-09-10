package com.example.ipoapi.services;

import com.example.ipoapi.daos.specification.ManageUserSpecification;
import com.example.ipoapi.dtos.*;
import com.example.ipoapi.entities.UserEntity;
import com.example.ipoapi.repositories.RoleInterfaceRepository;
import com.example.ipoapi.repositories.UserInterfaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "application")
public class ManageUserService {

    private final UserInterfaceRepository userInterfaceRepository;

    private final RoleInterfaceRepository roleInterfaceRepository;

    private final ManageUserSpecification manageUserSpecification;

    @Autowired
    public ManageUserService(UserInterfaceRepository userInterfaceRepository, ManageUserSpecification manageUserSpecification, RoleInterfaceRepository roleInterfaceRepository) {
        this.userInterfaceRepository = userInterfaceRepository;
        this.roleInterfaceRepository = roleInterfaceRepository;
        this.manageUserSpecification = manageUserSpecification;
    }

    public List<UserInfoResponseDTO> searchManageUser(ManageUserCriteriaDTO manageUserCriteriaDTO) {
        List<UserEntity> userEntities = userInterfaceRepository.findAll(manageUserSpecification.manageUserSpecification(manageUserCriteriaDTO), Sort.by(Sort.Direction.ASC, "name"));
        return userEntities.stream().map(t -> new UserInfoResponseDTO(
                t.getId(),
                t.getName(),
                t.getLastname(),
                t.getTelephoneNumber(),
                t.getBankName(),
                t.getBankNumber(),
                t.getRoleEntity().getName()
        )).collect(Collectors.toList());
    }

    public List<OptionDTO> getRole() {
        return roleInterfaceRepository.findAll(Sort.by(Sort.Direction.ASC, "name")).stream().map(c -> new OptionDTO(String.valueOf(c.getId()), c.getName())).collect(Collectors.toList());
    }

    @Transactional
    public Integer createUser(ManageUserDTO manageUserDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(manageUserDTO.getName());
        userEntity.setLastname(manageUserDTO.getLastname());
        userEntity.setUsername(manageUserDTO.getUsername());
        userEntity.setTelephoneNumber(manageUserDTO.getTelephoneNumber());
        userEntity.setBankName(manageUserDTO.getBankName());
        userEntity.setBankNumber(manageUserDTO.getBankNumber());
        userEntity.setRoleId(manageUserDTO.getRoleId());
        return userInterfaceRepository.saveAndFlush(userEntity).getId();
    }

    @Transactional
    public Integer updateUser(Integer id, ManageUserDTO manageUserDTO) {
        Optional<UserEntity> userEntityOptional = userInterfaceRepository.findById(id);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            userEntity.setName(manageUserDTO.getName());
            userEntity.setLastname(manageUserDTO.getLastname());
            userEntity.setUsername(manageUserDTO.getUsername());
            userEntity.setPassword(userEntityOptional.get().getPassword());
            userEntity.setTelephoneNumber(manageUserDTO.getTelephoneNumber());
            userEntity.setBankName(manageUserDTO.getBankName());
            userEntity.setBankNumber(manageUserDTO.getBankNumber());
            userEntity.setRoleId(manageUserDTO.getRoleId());
            return userInterfaceRepository.saveAndFlush(userEntity).getId();
        } else {
            throw new NoResultException("User is not found.");
        }
    }
}
