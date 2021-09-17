package com.example.ipoapi.repositories;

import com.example.ipoapi.entities.NewsEntity;
import com.example.ipoapi.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsInterfaceRepository extends JpaRepository<NewsEntity, Integer>, JpaSpecificationExecutor<NewsEntity> {
    void deleteByIdIn(List<Integer> ids);
}

