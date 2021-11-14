package com.example.ipoapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageTransactionResponseDTO {
    private Integer id;
    private String bankName;
    private String bankAccountName;
    private String bankNumber;
    private String amount;
    private String status;
    private String type;
}



