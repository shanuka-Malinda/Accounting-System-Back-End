package com.example.NFX.Controllers.Account;

import com.example.NFX.Dtos.Account.AccountDto.AccountRequestDto;
import com.example.NFX.Services.Account.AccountService;
import com.example.NFX.Utility.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("acc")
@CrossOrigin
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("create")
    public CommonResponse createAccount(@RequestBody AccountRequestDto accountRequestDto){
        return accountService.create(accountRequestDto);
    }

    @GetMapping("all")
    public CommonResponse getAllAccount(){
        return accountService.getAll();
    }
}
