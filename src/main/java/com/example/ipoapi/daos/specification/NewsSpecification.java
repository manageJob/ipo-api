package com.example.ipoapi.daos.specification;


import com.example.ipoapi.dtos.ManageUserCriteriaDTO;
import com.example.ipoapi.dtos.NewsCriteriaDTO;
import com.example.ipoapi.entities.NewsEntity;
import com.example.ipoapi.entities.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class NewsSpecification {

    public Specification<NewsEntity> newsSpecification(NewsCriteriaDTO newsCriteriaDTO) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(newsCriteriaDTO.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + newsCriteriaDTO.getName() + "%"));
            }
            if (StringUtils.isNotEmpty(newsCriteriaDTO.getDetail())) {
                predicates.add(cb.like(root.get("detail"), "%" + newsCriteriaDTO.getDetail() + "%"));
            }
            query.distinct(true);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
