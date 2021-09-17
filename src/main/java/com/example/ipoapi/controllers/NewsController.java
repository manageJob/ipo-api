package com.example.ipoapi.controllers;

import com.example.ipoapi.dtos.*;
import com.example.ipoapi.services.ManageUserService;
import com.example.ipoapi.services.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Slf4j(topic = "application")
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news")
    public ResponseEntity<?> search(@RequestParam(name = "name", defaultValue = "") String name,
                                    @RequestParam(name = "detail", defaultValue = "") String detail
    ) {
        NewsCriteriaDTO newsCriteriaDTO = NewsCriteriaDTO.builder()
                .name(name)
                .detail(detail)
                .build();
        try {
            return ResponseEntity.ok(newsService.searchNews(newsCriteriaDTO));
        } catch (NoResultException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }

    @PostMapping("/news")
    public ResponseEntity<?> createNews(@RequestBody NewsDTO newsDTO) {
        try {
            Integer createNewsId = newsService.createNews(newsDTO);
            return ResponseEntity.ok().body(createNewsId);
        } catch (NoResultException ex) {
            log.warn("Api POST : /news : Have Error {}, {}", ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        } catch (Exception ex) {
            log.error("Api POST : /news : Have Error {}, {}", ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }

    @PutMapping("/news/{id}")
    public ResponseEntity<?> updateNews(@PathVariable("id") Integer id, @RequestBody NewsDTO newsDTO) {
        try {
            Integer updatedId = newsService.updateNews(id, newsDTO);
            return ResponseEntity.ok().body(updatedId);
        } catch (NoResultException ex) {
            log.warn("Api PUT : /news/{} : Have Error {}, {}", id, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        } catch (Exception ex) {
            log.error("Api PUT : /news/{} : Have Error {}, {}", id, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }

    @GetMapping("/news/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable("id") String id) {
        try {
            NewsDTO newsDTO = newsService.getUserById(id);
            return ResponseEntity.ok().body(newsDTO);
        } catch (NoResultException ex) {
            log.warn("Api GET : /news/{} : Have Error {}, {}", id, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        } catch (Exception ex) {
            log.error("Api GET : /news/{} : Have Error {}, {}", id, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }

    @DeleteMapping("/news")
    public ResponseEntity<?> delete(@RequestParam(name = "ids", defaultValue = "") List<Integer> ids) {
        String idString = ids.stream().map(Objects::toString).collect(Collectors.joining(","));
        try {
            newsService.delete(ids);
            return ResponseEntity.ok().build();
        } catch (NoResultException ex) {
            log.warn("Api DELETE : /news : Have Error {}, {} with ids: {}", idString, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        } catch (Exception ex) {
            log.warn("Api DELETE : /news : Have Error {}, {} with ids: {}", idString, ex.getMessage(), ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }
}
