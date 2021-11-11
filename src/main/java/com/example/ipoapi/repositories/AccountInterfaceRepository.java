package com.example.ipoapi.repositories;

import com.example.ipoapi.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountInterfaceRepository extends JpaRepository<AccountEntity, Integer>, JpaSpecificationExecutor<AccountEntity> {
    void deleteByIdIn(List<Integer> ids);
}
