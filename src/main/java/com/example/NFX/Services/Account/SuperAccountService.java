package com.example.NFX.Services.Account;

import com.example.NFX.Dtos.Account.SuperAccountDto.DateFilterRequest;
import com.example.NFX.Dtos.Account.SuperAccountDto.SuperAccountDto;
import com.example.NFX.Utility.CommonResponse;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SuperAccountService {
    CommonResponse saveDoubleEntry(List<SuperAccountDto> journalEntries);
    CommonResponse saveSingleEntry(SuperAccountDto superAccountDto);

    CommonResponse getAll();

    CommonResponse getAll(DateFilterRequest dateFilterRequest);

    CommonResponse getAllWithAcc(DateFilterRequest dateFilterRequest);


}
