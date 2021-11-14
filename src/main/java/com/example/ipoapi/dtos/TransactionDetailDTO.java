package com.example.ipoapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailDTO {
    private String amount;
    private String type;
    private String status;
    private LocalDateTime transactionTime;
}
