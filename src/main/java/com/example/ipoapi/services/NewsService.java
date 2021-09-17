package com.example.ipoapi.services;

import com.example.ipoapi.daos.specification.ManageUserSpecification;
import com.example.ipoapi.daos.specification.NewsSpecification;
import com.example.ipoapi.dtos.*;
import com.example.ipoapi.entities.NewsEntity;
import com.example.ipoapi.entities.UserEntity;
import com.example.ipoapi.repositories.NewsInterfaceRepository;
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

import static java.lang.Integer.parseInt;

@Service
@Slf4j(topic = "application")
public class NewsService {

    private final NewsSpecification newsSpecification;

    private final NewsInterfaceRepository newsInterfaceRepository;

    @Autowired
    public NewsService(NewsSpecification newsSpecification,  NewsInterfaceRepository newsInterfaceRepository) {
        this.newsSpecification = newsSpecification;
        this.newsInterfaceRepository = newsInterfaceRepository;
    }

    public List<NewsResponseDTO> searchNews(NewsCriteriaDTO newsCriteriaDTO) {
        List<NewsEntity> newsEntities = newsInterfaceRepository.findAll(newsSpecification.newsSpecification(newsCriteriaDTO), Sort.by(Sort.Direction.ASC, "name"));
        return newsEntities.stream().map(t -> new NewsResponseDTO(
                t.getId(),
                t.getName(),
                t.getDetail()
        )).collect(Collectors.toList());
    }

    @Transactional
    public Integer createNews(NewsDTO newsDTO) {
        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setName(newsDTO.getName());
        newsEntity.setDetail(newsDTO.getDetail());
        return newsInterfaceRepository.saveAndFlush(newsEntity).getId();
    }

    @Transactional
    public Integer updateNews(Integer id, NewsDTO newsDTO) {
        Optional<NewsEntity> newsEntityOptional = newsInterfaceRepository.findById(id);
        if (newsEntityOptional.isPresent()) {
            NewsEntity newsEntity = newsEntityOptional.get();
            newsEntity.setName(newsDTO.getName());
            newsEntity.setDetail(newsDTO.getDetail());
            return newsInterfaceRepository.saveAndFlush(newsEntity).getId();
        } else {
            throw new NoResultException("News is not found.");
        }
    }

    public NewsDTO getUserById(String userId) {
        Optional<NewsEntity> newsEntityOptional = newsInterfaceRepository.findById(parseInt(userId));
        if (newsEntityOptional.isPresent()) {
            NewsEntity newsEntity = newsEntityOptional.get();
            return wrapperNewsDTO(newsEntity);
        } else {
            throw new NoResultException("News is not found.");
        }
    }

    private NewsDTO wrapperNewsDTO(NewsEntity newsEntity) {
        return new NewsDTO(String.valueOf(newsEntity.getId()), newsEntity.getName(), newsEntity.getDetail());
    }

    @Transactional
    public void delete(List<Integer> ids) {
        List<NewsEntity> newsEntities = newsInterfaceRepository.findAllById(ids);
        if (newsEntities.isEmpty()) {
            throw new NoResultException("News is not found.");
        }
        newsInterfaceRepository.deleteByIdIn(ids);
    }
}
