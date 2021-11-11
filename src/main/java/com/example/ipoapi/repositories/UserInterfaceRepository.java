package com.example.ipoapi.repositories;

import com.example.ipoapi.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserInterfaceRepository extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByUsername(String user);
    List<UserEntity> findByRoleIdInOrderById(List<String> ids);
    void deleteByIdIn(List<Integer> ids);
}
