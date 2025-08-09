package com.example.NFX.Dtos.Account.AccountDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountRequestDto {
    private String name;
    private String description;
    private String isCurrent;
    private String createdBy;
    private String updatedBy;
    private String catCode;
    private Long catId;
    private BigDecimal openingBalance;
    private BigDecimal currentBalance;
    private BigDecimal creditLimit;

}
