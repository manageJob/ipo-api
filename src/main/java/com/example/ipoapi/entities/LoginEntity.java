package com.example.ipoapi.entities;


import lombok.Data;

import javax.persistence.*;
@Entity
@Data
@Table(name = "login")
public class LoginEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;
}
