package com.example.ipoapi.repositories;

import com.example.ipoapi.entities.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface LoginInterfaceRepository extends JpaRepository<LoginEntity, Integer>, JpaSpecificationExecutor<LoginEntity> {
}
