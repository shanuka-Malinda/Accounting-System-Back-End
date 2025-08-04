package com.example.NFX.Services.Account;

import com.example.NFX.Dtos.Account.SuperAccountDto.SuperAccountDto;
import com.example.NFX.Utility.CommonResponse;

import java.util.List;

public interface SuperAccountService {
    CommonResponse saveDoubleEntry(List<SuperAccountDto> journalEntries);
    CommonResponse saveSingleEntry(SuperAccountDto superAccountDto);
}
