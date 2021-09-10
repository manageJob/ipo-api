package com.example.ipoapi.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ManageUserCriteriaDTO {
    private String name;
    private String lastname;
}
