package com.example.NFX.Dtos.Account.AccountCategoryDto;

import com.example.NFX.Constant.CommonStatus;
import com.example.NFX.Dtos.Account.AccountDto.AccountResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCategoryResponseDto {
    private Long id;
    private String name;
    private String code;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CommonStatus commonStatus;
    private List<AccountResponseDto> accounts;
}
