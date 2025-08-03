package com.example.NFX.Services.Account;

import com.example.NFX.Dtos.Account.AccountCategoryDto.AccountCategoryRequestDto;
import com.example.NFX.Utility.CommonResponse;

public interface AccountCategoryService {
    CommonResponse createAccountCategory(AccountCategoryRequestDto accountCategoryRequestDto);

    CommonResponse getAll();

    CommonResponse getCat();
}
