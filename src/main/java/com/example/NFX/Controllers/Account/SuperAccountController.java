package com.example.NFX.Controllers.Account;


import com.example.NFX.Dtos.Account.SuperAccountDto.SuperAccountDto;
import com.example.NFX.Services.Account.SuperAccountService;
import com.example.NFX.Utility.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sup")
@CrossOrigin
public class SuperAccountController {
    private final SuperAccountService superAccountService;

    @Autowired
    public SuperAccountController(SuperAccountService superAccountService) {
        this.superAccountService = superAccountService;
    }

    @PostMapping("create-journal-single")
    public CommonResponse saveSingleEntry(@RequestBody SuperAccountDto superAccountDto){
       return superAccountService.saveSingleEntry(superAccountDto); }
    @PostMapping("create-journal")
    public CommonResponse saveDoubleEntry(@RequestBody List<SuperAccountDto> journalEntries) {
       return superAccountService.saveDoubleEntry(journalEntries);
    }
}
