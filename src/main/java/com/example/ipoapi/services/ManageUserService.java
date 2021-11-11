package com.example.ipoapi.services;

import com.example.ipoapi.daos.specification.ManageUserSpecification;
import com.example.ipoapi.dtos.*;
import com.example.ipoapi.entities.AccountEntity;
import com.example.ipoapi.entities.UserEntity;
import com.example.ipoapi.repositories.AccountInterfaceRepository;
import com.example.ipoapi.repositories.RoleInterfaceRepository;
import com.example.ipoapi.repositories.UserInterfaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@Service
@Slf4j(topic = "application")
public class ManageUserService {

    private final UserInterfaceRepository userInterfaceRepository;

    private final AccountInterfaceRepository accountInterfaceRepository;

    private final RoleInterfaceRepository roleInterfaceRepository;

    private final ManageUserSpecification manageUserSpecification;

    @Autowired
    public ManageUserService(UserInterfaceRepository userInterfaceRepository, AccountInterfaceRepository accountInterfaceRepository, ManageUserSpecification manageUserSpecification, RoleInterfaceRepository roleInterfaceRepository) {
        this.userInterfaceRepository = userInterfaceRepository;
        this.accountInterfaceRepository = accountInterfaceRepository;
        this.roleInterfaceRepository = roleInterfaceRepository;
        this.manageUserSpecification = manageUserSpecification;
    }

    public List<UserInfoResponseDTO> searchManageUser(ManageUserCriteriaDTO manageUserCriteriaDTO) {
        List<UserEntity> userEntities = userInterfaceRepository.findAll(manageUserSpecification.manageUserSpecification(manageUserCriteriaDTO), Sort.by(Sort.Direction.ASC, "name"));
        return userEntities.stream().map(t -> new UserInfoResponseDTO(
                t.getId(),
                t.getName(),
                t.getLastname(),
                t.getUsername(),
                t.getTelephoneNumber(),
                t.getAccountEntity().getBankName(),
                t.getAccountEntity().getBankNumber(),
                t.getRoleEntity().getName()
        )).collect(Collectors.toList());
    }

    public List<OptionDTO> getRole() {
        return roleInterfaceRepository.findAll(Sort.by(Sort.Direction.ASC, "name")).stream().map(c -> new OptionDTO(c.getName(), String.valueOf(c.getId()))).collect(Collectors.toList());
    }

    @Transactional
    public Integer createUser(ManageUserDTO manageUserDTO) {
        UserEntity userEntity = new UserEntity();
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setBankName("");
        accountEntity.setBankAccountName("");
        accountEntity.setBankNumber("");
        accountEntity.setBalance(0);
        Integer idAccount = accountInterfaceRepository.saveAndFlush(accountEntity).getId();
        userEntity.setUsername(manageUserDTO.getUsername());
        userEntity.setPassword("ipo1234");
        userEntity.setRoleId(manageUserDTO.getRoleId());
        userEntity.setAccountId(idAccount);
        return userInterfaceRepository.saveAndFlush(userEntity).getId();
    }

    @Transactional
    public Integer updateUser(Integer id, ManageUserDTO manageUserDTO) {
        Optional<UserEntity> userEntityOptional = userInterfaceRepository.findById(id);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            userEntity.setUsername(manageUserDTO.getUsername());
            userEntity.setRoleId(manageUserDTO.getRoleId());
            return userInterfaceRepository.saveAndFlush(userEntity).getId();
        } else {
            throw new NoResultException("User is not found.");
        }
    }

    @Transactional
    public Integer resetUserPassword(Integer id) {
        Optional<UserEntity> userEntityOptional = userInterfaceRepository.findById(id);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            userEntity.setPassword("ipo1234");
            return userInterfaceRepository.saveAndFlush(userEntity).getId();
        } else {
            throw new NoResultException("User is not found.");
        }
    }

    public ManageUserDTO getUserById(String userId) {
        Optional<UserEntity> userEntityOptional = userInterfaceRepository.findById(parseInt(userId));
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            return wrapperManageUserDTO(userEntity);
        } else {
            throw new NoResultException("User is not found.");
        }
    }

    private ManageUserDTO wrapperManageUserDTO(UserEntity userEntity) {
        return new ManageUserDTO(String.valueOf(userEntity.getId()), userEntity.getName(), userEntity.getLastname(), userEntity.getUsername(),
                userEntity.getPassword(), userEntity.getTelephoneNumber(), userEntity.getAccountEntity().getBankName(), userEntity.getAccountEntity().getBankNumber(), userEntity.getRoleId());
    }

    @Transactional
    public void delete(List<Integer> ids) {
        List<UserEntity> userEntities = userInterfaceRepository.findAllById(ids);
        if (userEntities.isEmpty()) {
            throw new NoResultException("User is not found.");
        }
        List<Integer> accountIds = new ArrayList<>();
        for (UserEntity userEntity: userEntities) {
            accountIds.add(userEntity.getAccountId());
        }
        userInterfaceRepository.deleteByIdIn(ids);
        accountInterfaceRepository.deleteByIdIn(accountIds);
    }
}
