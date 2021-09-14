package com.example.ipoapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponseDTO {
    private Integer id;
    private String name;
    private String lastname;
    private String username;
    private String telephoneNumber;
    private String bankName;
    private String bankNumber;
    private String role;
}
