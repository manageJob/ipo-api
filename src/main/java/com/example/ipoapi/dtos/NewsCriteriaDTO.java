package com.example.ipoapi.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class NewsCriteriaDTO {
    private String name;
    private String detail;
}
