package com.example.ipoapi.entities;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "news")
public class NewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "detail")
    private String detail;

}
