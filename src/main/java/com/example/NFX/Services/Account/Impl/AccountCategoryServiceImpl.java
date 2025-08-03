package com.example.NFX.Services.Account.Impl;

import com.example.NFX.Constant.CommonMsg;
import com.example.NFX.Constant.CommonStatus;
import com.example.NFX.Dtos.Account.AccountCategoryDto.AccountCategoryRequestDto;
import com.example.NFX.Dtos.Account.AccountCategoryDto.AccountCategoryResponseDto;
import com.example.NFX.Dtos.Account.AccountCategoryDto.OnlyAccCatDetailsResponseDto;
import com.example.NFX.Dtos.Account.AccountDto.AccountResponseDto;
import com.example.NFX.Entity.Account.AccountCategoryEntity;
import com.example.NFX.Repo.Account.AccountCategoryRepo;
import com.example.NFX.Services.Account.AccountCategoryService;
import com.example.NFX.Utility.CommonResponse;
import com.example.NFX.Utility.CommonValidation;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountCategoryServiceImpl implements AccountCategoryService {
    private static final Logger logger = LoggerFactory.getLogger(com.example.NFX.Services.Account.AccountCategoryService.class);

    private final AccountCategoryRepo accountCategoryRepo;

    @Autowired
    public AccountCategoryServiceImpl(AccountCategoryRepo accountCategoryRepo) {
        this.accountCategoryRepo = accountCategoryRepo;
    }

    @PostConstruct
    @Transactional
    public void initializeData() {
        initializeDefaultAccountCategories();
    }

    private void initializeDefaultAccountCategories() {
        Object[][] categoryData = {
                {"Assets", "10"},
                {"Liabilities", "20"},
                {"Equity", "30"},
                {"Revenue", "40"},
                {"Expenses", "50"}
        };

        for (Object[] category : categoryData) {
            String categoryName = (String) category[0];
            String categoryCode = (String) category[1];

            // Check for duplicates by both name and code
            if (!accountCategoryRepo.existsByName(categoryName) && !accountCategoryRepo.existsByCode(categoryCode)) {
                AccountCategoryEntity accountCategory = new AccountCategoryEntity();
                accountCategory.setName(categoryName);
                accountCategory.setCode(categoryCode);
                accountCategory.setCommonStatus(CommonStatus.ACTIVE);

                accountCategoryRepo.save(accountCategory);
                logger.info("Created default account category: {} (Code: {})", categoryName, categoryCode);
            } else {
                logger.info("Account category already exists: {} (Code: {})", categoryName, categoryCode);
            }
        }
    }

    @Override
    @Transactional
    public CommonResponse createAccountCategory(AccountCategoryRequestDto accountCategoryRequestDto) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<String> validationList = accountCategoryValidation(accountCategoryRequestDto);
            if (!validationList.isEmpty()) {
                commonResponse.setStatus(false);
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }

            AccountCategoryEntity accountCategoryEntity = dtoToEntity(accountCategoryRequestDto);
            AccountCategoryEntity savedAccountCategoryEntity = accountCategoryRepo.save(accountCategoryEntity);
            commonResponse.setStatus(true);
            commonResponse.setPayload(List.of(savedAccountCategoryEntity)); // Simplified payload
        } catch (DataAccessException e) {
            logger.error("Database error while creating account category: {}", e.getMessage(), e);
            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(List.of("Database error while creating account category: " + e.getMessage()));
        } catch (Exception e) {
            logger.error("Unexpected error while creating account category: {}", e.getMessage(), e);
            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(List.of("Unexpected error while creating account category: " + e.getMessage()));
        }
        return commonResponse;
    }

    //#################### GET CATEGORY DETAILS WITH ACC DETAILS##########################
    @Override
    public CommonResponse getAll() {
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<AccountCategoryResponseDto> accountCategoryResponseDtoList = accountCategoryRepo.findAll()
                    .stream()
                    .filter(accountCategoryEntity -> accountCategoryEntity.getCommonStatus() == CommonStatus.ACTIVE)
                    .map(this::entityToDto)
                    .toList();
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(accountCategoryResponseDtoList)); // Direct list instead of singleton
        } catch (DataAccessException e) {
            logger.error("Database error while retrieving account categories: {}", e.getMessage(), e);
            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(List.of("Database error while retrieving account categories: " + e.getMessage()));
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving account categories: {}", e.getMessage(), e);
            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(List.of("Unexpected error while retrieving account categories: " + e.getMessage()));
        }
        return commonResponse;
    }

    //#################### GET ONLY CATEGORY DETAILS ##########################
    @Override
    public CommonResponse getCat() {
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<OnlyAccCatDetailsResponseDto> accountCategoryResponseDtoList = accountCategoryRepo.findAll()
                    .stream()
                    .filter(accountCategoryEntity -> accountCategoryEntity.getCommonStatus() == CommonStatus.ACTIVE)
                    .map(this::entityToDtoOnlyCatDet)
                    .toList();
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(accountCategoryResponseDtoList)); // Direct list instead of singleton
        } catch (DataAccessException e) {
            logger.error("Database error while retrieving account categories: {}", e.getMessage(), e);
            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(List.of("Database error while retrieving account categories: " + e.getMessage()));
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving account categories: {}", e.getMessage(), e);
            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(List.of("Unexpected error while retrieving account categories: " + e.getMessage()));
        }
        return commonResponse;
    }

    private AccountCategoryEntity dtoToEntity(AccountCategoryRequestDto accountCategoryRequestDto) {
        AccountCategoryEntity accountCategoryEntity = new AccountCategoryEntity();
        accountCategoryEntity.setName(accountCategoryRequestDto.getName());
        accountCategoryEntity.setCode(accountCategoryRequestDto.getCode());
        accountCategoryEntity.setCommonStatus(CommonStatus.ACTIVE);
        return accountCategoryEntity;
    }

    private AccountCategoryResponseDto entityToDto(AccountCategoryEntity accountCategoryEntity) {
        AccountCategoryResponseDto accountCategoryResponseDto = new AccountCategoryResponseDto();
        accountCategoryResponseDto.setId(accountCategoryEntity.getId());
        accountCategoryResponseDto.setName(accountCategoryEntity.getName());
        accountCategoryResponseDto.setCode(accountCategoryEntity.getCode());
        accountCategoryResponseDto.setCommonStatus(accountCategoryEntity.getCommonStatus());
        accountCategoryResponseDto.setCreatedAt(accountCategoryEntity.getCreatedAt());
        accountCategoryResponseDto.setUpdatedAt(accountCategoryEntity.getUpdatedAt());

        if (accountCategoryEntity.getAccounts() != null && !accountCategoryEntity.getAccounts().isEmpty()) {
            List<AccountResponseDto> basicAccounts = accountCategoryEntity.getAccounts().stream()
                    .map(account -> {
                        AccountResponseDto accountResponseDto = new AccountResponseDto();
                        accountResponseDto.setAccNo(account.getAccNo());
                        accountResponseDto.setName(account.getName());
                        accountResponseDto.setOpeningBalance(account.getOpeningBalance());
                        accountResponseDto.setCurrentBalance(account.getCurrentBalance());
                        accountResponseDto.setCreditLimit(account.getCreditLimit());

                        accountResponseDto.setCreatedBy(account.getCreatedBy());
                        accountResponseDto.setUpdatedBy(account.getUpdatedBy());
                        accountResponseDto.setCreatedAt(account.getCreatedAt());
                        accountResponseDto.setUpdatedAt(account.getUpdatedAt());
                        return accountResponseDto;
                    })
                    .collect(Collectors.toList());
            accountCategoryResponseDto.setAccounts(basicAccounts);
        }

        return accountCategoryResponseDto;
    }

    private OnlyAccCatDetailsResponseDto entityToDtoOnlyCatDet(AccountCategoryEntity accountCategoryEntity){
        OnlyAccCatDetailsResponseDto onlyAccCatDetailsResponseDto = new OnlyAccCatDetailsResponseDto();
        onlyAccCatDetailsResponseDto.setId(accountCategoryEntity.getId());
        onlyAccCatDetailsResponseDto.setName(accountCategoryEntity.getName());
        onlyAccCatDetailsResponseDto.setCode(accountCategoryEntity.getCode());
        onlyAccCatDetailsResponseDto.setCommonStatus(accountCategoryEntity.getCommonStatus());
        onlyAccCatDetailsResponseDto.setCreatedAt(accountCategoryEntity.getCreatedAt());
        onlyAccCatDetailsResponseDto.setUpdatedAt(accountCategoryEntity.getUpdatedAt());

        return onlyAccCatDetailsResponseDto;
    }

    private List<String> accountCategoryValidation(AccountCategoryRequestDto accountCategoryRequestDto) {
        List<String> validationList = new ArrayList<>();
        if (CommonValidation.isNullOrEmpty(accountCategoryRequestDto.getName())) { // Simplified validation
            validationList.add(CommonMsg.EMPTY_NAME);
        }
        if (CommonValidation.isNullOrEmpty(accountCategoryRequestDto.getCode())) {
            validationList.add("Category code cannot be empty");
        }
        if (accountCategoryRepo.existsByCode(accountCategoryRequestDto.getCode())) {
            validationList.add("Category code already exists: " + accountCategoryRequestDto.getCode());
        }
        return validationList;
    }
}
