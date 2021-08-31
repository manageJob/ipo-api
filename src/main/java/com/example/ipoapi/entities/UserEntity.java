package com.example.ipoapi.entities;


import lombok.Data;

import javax.persistence.*;
@Entity
@Data
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "user")
    private String user;

    @Column(name = "password")
    private String password;
}
