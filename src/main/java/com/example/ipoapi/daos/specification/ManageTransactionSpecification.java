package com.example.ipoapi.daos.specification;


import com.example.ipoapi.dtos.ManageTransactionCriteriaDTO;
import com.example.ipoapi.dtos.ManageUserCriteriaDTO;
import com.example.ipoapi.entities.TransactionEntity;
import com.example.ipoapi.entities.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ManageTransactionSpecification {

    public Specification<TransactionEntity> manageTransactionSpecification(ManageTransactionCriteriaDTO manageTransactionCriteriaDTO) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(manageTransactionCriteriaDTO.getBankAccountName())) {
                predicates.add(cb.like(root.get("bankAccountName"), "%" + manageTransactionCriteriaDTO.getBankAccountName() + "%"));
            }
            query.distinct(true);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
