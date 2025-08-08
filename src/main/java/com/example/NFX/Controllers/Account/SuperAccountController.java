package com.example.NFX.Controllers.Account;


import com.example.NFX.Dtos.Account.SuperAccountDto.DateFilterRequest;
import com.example.NFX.Dtos.Account.SuperAccountDto.SuperAccountDto;
import com.example.NFX.Services.Account.SuperAccountService;
import com.example.NFX.Utility.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("all")
    public CommonResponse getAll(){
        return superAccountService.getAll();
    }
    @PostMapping("/all/filter")
    public CommonResponse getAllSuperAccountsWithDateFilterPost(
            @RequestBody DateFilterRequest request) {

        return superAccountService.getAll(request);
    }

    @PostMapping("/all/filter/acc")
    public CommonResponse getAllWithAcc(@RequestBody DateFilterRequest dateFilterRequest){
        return superAccountService.getAllWithAcc(dateFilterRequest);
    }

}
