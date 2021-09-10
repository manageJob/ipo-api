package com.example.ipoapi.daos.specification;


import com.example.ipoapi.dtos.ManageUserCriteriaDTO;
import com.example.ipoapi.entities.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ManageUserSpecification {

    public Specification<UserEntity> manageUserSpecification(ManageUserCriteriaDTO manageUserCriteriaDTO) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(manageUserCriteriaDTO.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + manageUserCriteriaDTO.getName() + "%"));
            }
            if (StringUtils.isNotEmpty(manageUserCriteriaDTO.getLastname())) {
                predicates.add(cb.like(root.get("lastname"), "%" + manageUserCriteriaDTO.getLastname() + "%"));
            }
            query.distinct(true);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
