package com.example.ipoapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageUserPasswordDTO {
    private String username;
    private String roleId;
}
