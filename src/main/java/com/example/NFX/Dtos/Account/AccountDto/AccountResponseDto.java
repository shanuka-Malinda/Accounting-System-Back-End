package com.example.NFX.Dtos.Account.AccountDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDto {
    private Long id;
    private String accNo;
    private String name;
    private String catCode;
    private String description;
    private String isCurrent;
    private BigDecimal openingBalance;
    private BigDecimal currentBalance;
    private BigDecimal CreditLimit;

    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

 