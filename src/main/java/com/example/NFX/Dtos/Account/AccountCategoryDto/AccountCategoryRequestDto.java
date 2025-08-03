package com.example.NFX.Dtos.Account.AccountCategoryDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCategoryRequestDto {
    private String name;
    private String code;
}
