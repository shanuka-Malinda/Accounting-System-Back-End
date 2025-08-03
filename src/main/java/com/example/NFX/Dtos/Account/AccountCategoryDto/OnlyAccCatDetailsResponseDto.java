package com.example.NFX.Dtos.Account.AccountCategoryDto;

import com.example.NFX.Constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlyAccCatDetailsResponseDto {
    private Long id;
    private String name;
    private String code;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CommonStatus commonStatus;
}
