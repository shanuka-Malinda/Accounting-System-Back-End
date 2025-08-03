package com.example.NFX.Services.Account;

import com.example.NFX.Dtos.Account.AccountDto.AccountRequestDto;
import com.example.NFX.Utility.CommonResponse;

public interface AccountService {
    CommonResponse create(AccountRequestDto accountRequestDto);

    CommonResponse getAll();
}
