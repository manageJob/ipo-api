package com.example.ipoapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Integer accountId;
    private Integer amount;
    private String type;
    private String status;
    private LocalDateTime transactionTime;
}
