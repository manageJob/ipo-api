package com.example.ipoapi.repositories;

import com.example.ipoapi.entities.TransactionEntity;
import com.example.ipoapi.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionInterfaceRepository extends JpaRepository<TransactionEntity, Integer>, JpaSpecificationExecutor<TransactionEntity> {
    List<TransactionEntity> findByUserIdOrderById(Integer accountId);
}
