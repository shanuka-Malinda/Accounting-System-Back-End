package com.example.NFX.Services.Account;

import com.example.NFX.Dtos.Account.SuperAccountDto.SuperAccountDto;
import com.example.NFX.Utility.CommonResponse;

public interface SuperAccountService {
    CommonResponse saveDoubleEntry(SuperAccountDto superAccountDto);
}
