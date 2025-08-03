package com.example.NFX.Controllers.Account;

import com.example.NFX.Dtos.Account.AccountCategoryDto.AccountCategoryRequestDto;
import com.example.NFX.Services.Account.AccountCategoryService;
import com.example.NFX.Utility.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("acc/cat")
@CrossOrigin("*")
public class AccountCategoryController {
    private final AccountCategoryService accountCategoryService;

    @Autowired
    public AccountCategoryController(AccountCategoryService accountCategoryService) {
        this.accountCategoryService = accountCategoryService;
    }

    @PostMapping("create")
    public CommonResponse createAccount(@RequestBody AccountCategoryRequestDto accountCategoryRequestDto){
        return accountCategoryService.createAccountCategory(accountCategoryRequestDto);
    }
    @GetMapping("all")
    public CommonResponse getAll(){
        return accountCategoryService.getAll();
    }

    @GetMapping("cat")
    public CommonResponse getCat(){
        return accountCategoryService.getCat();
    }
}
