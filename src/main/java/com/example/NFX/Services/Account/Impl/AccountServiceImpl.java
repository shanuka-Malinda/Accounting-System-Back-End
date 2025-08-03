package com.example.NFX.Services.Account.Impl;

import com.example.NFX.Constant.CommonMsg;
import com.example.NFX.Constant.CommonStatus;
import com.example.NFX.Dtos.Account.AccountCategoryDto.AccountCategoryResponseDto;
import com.example.NFX.Dtos.Account.AccountDto.AccountRequestDto;
import com.example.NFX.Dtos.Account.AccountDto.AccountResponseDto;
import com.example.NFX.Entity.Account.AccountCategoryEntity;
import com.example.NFX.Entity.Account.AccountEntity;
import com.example.NFX.Repo.Account.AccountCategoryRepo;
import com.example.NFX.Repo.Account.AccountRepo;
import com.example.NFX.Services.Account.AccountCategoryService;
import com.example.NFX.Services.Account.AccountService;
import com.example.NFX.Utility.CommonResponse;
import com.example.NFX.Utility.CommonValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;
    private final AccountCategoryRepo accountCategoryRepo;

    @Autowired
    public AccountServiceImpl(AccountRepo accountRepo, AccountCategoryRepo accountCategoryRepo) {
        this.accountRepo = accountRepo;
        this.accountCategoryRepo = accountCategoryRepo;
    }

    @Override
    public CommonResponse create(AccountRequestDto accountRequestDto) {
        CommonResponse commonResponse= new CommonResponse();
        try {
            List<String> validationList= AccountValidation(accountRequestDto);
            if (!validationList.isEmpty()){
                commonResponse.setStatus(false);
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }
            AccountEntity accountEntity= dtoCastToEntity(accountRequestDto);
            AccountEntity saveAccountEntity= accountRepo.save(accountEntity);

            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList("SuccessFully saved"));
        }catch (Exception e){
            e.printStackTrace();
            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(Collections.singletonList("Error Occurred While Account Saving"));

        }
        return commonResponse;
    }

    @Override
    public CommonResponse getAll() {
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<AccountResponseDto> accountResponseDtoList = accountRepo.findAll()
                    .stream()
                    .filter(accountEntity -> accountEntity.getCommonStatus() == CommonStatus.ACTIVE)
                    .map(this::entityCastToDto)
                    .toList();
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(accountResponseDtoList)); // Direct list instead of singleton
        } catch (DataAccessException e) {
            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(List.of("Database error while retrieving account: " + e.getMessage()));
        } catch (Exception e) {

            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(List.of("Unexpected error while retrieving account: " + e.getMessage()));
        }
        return commonResponse;
    }

    private AccountEntity dtoCastToEntity(AccountRequestDto accountRequestDto){
        AccountEntity accountEntity= new AccountEntity();
        String catCode=accountRequestDto.getCatCode();
        accountEntity.setCatCode(catCode);
        accountEntity.setAccNo(generateAccountNumber(catCode));
        accountEntity.setName(accountRequestDto.getName());
        accountEntity.setDescription(accountRequestDto.getDescription());

        accountEntity.setCreditLimit(accountRequestDto.getCreditLimit());
        accountEntity.setOpeningBalance(accountRequestDto.getOpeningBalance());
        accountEntity.setCurrentBalance(accountRequestDto.getCurrentBalance());

        accountEntity.setCreatedBy(accountRequestDto.getCreatedBy());
        accountEntity.setUpdatedBy(accountRequestDto.getUpdatedBy());
        accountEntity.setCommonStatus(CommonStatus.ACTIVE);

        AccountCategoryEntity category = accountCategoryRepo.findById(accountRequestDto.getCatId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + accountRequestDto.getCatId()));
        accountEntity.setAccountCategory(category);
        return accountEntity;
    }

    private AccountResponseDto entityCastToDto(AccountEntity accountEntity){
        AccountResponseDto accountResponseDto=new AccountResponseDto();
        accountResponseDto.setId(accountEntity.getId());
        accountResponseDto.setCatCode(accountEntity.getCatCode());
        accountResponseDto.setAccNo(accountEntity.getAccNo());
        accountResponseDto.setName(accountEntity.getName());
        accountResponseDto.setCurrentBalance(accountEntity.getCurrentBalance());
        accountResponseDto.setOpeningBalance(accountEntity.getOpeningBalance());
        accountResponseDto.setCreditLimit(accountEntity.getCreditLimit());
        accountResponseDto.setCreatedAt(accountEntity.getCreatedAt());
        accountResponseDto.setUpdatedAt(accountEntity.getUpdatedAt());
        accountResponseDto.setCreatedBy(accountEntity.getCreatedBy());
        accountResponseDto.setUpdatedBy(accountEntity.getUpdatedBy());

        return accountResponseDto;
    }

    private boolean CheckAccNameExited(String name){
        return accountRepo.existsByName(name);
    }

    private List<String> AccountValidation(AccountRequestDto accountRequestDto){
        List<String> validationList=new ArrayList<>();
        if(!CommonValidation.isNotNullOrEmpty(accountRequestDto.getName())){
            validationList.add(CommonMsg.EMPTY_ACCOUNT_NAME);
        }
        if (this.CheckAccNameExited(accountRequestDto.getName())){
            validationList.add(CommonMsg.EXITED_ACC_NAME);
        }

        return validationList;
    }


    private String generateAccountNumber(String code) {
        final String PREFIX = "AC"+code+"M";
        final int SEQUENCE_LENGTH = 4;
        try {
            // Get the latest account number from database
            Optional<String> maxAccountNumber = accountRepo.findMaxAccountNumber();

            int nextSequence = 1; // Default starting sequence

            if (maxAccountNumber.isPresent() && maxAccountNumber.get() != null) {
                String lastAccNo = maxAccountNumber.get();

                // Extract the sequence part (last 4 digits)
                if (lastAccNo.length() >= SEQUENCE_LENGTH && lastAccNo.startsWith(PREFIX)) {
                    String sequencePart = lastAccNo.substring(PREFIX.length());
                    try {
                        int lastSequence = Integer.parseInt(sequencePart);
                        nextSequence = lastSequence + 1;
                    } catch (NumberFormatException e) {
                        // If parsing fails, start from 1
                        nextSequence = 1;
                    }
                }
            }

            // Format the sequence number with leading zeros
            String sequenceFormatted = String.format("%0" + SEQUENCE_LENGTH + "d", nextSequence);
            String generatedAccNo = PREFIX + sequenceFormatted;

            // Double-check if the generated number already exists (safety check)
            int attempts = 0;
            while (accountRepo.existsByAccNo(generatedAccNo) && attempts < 1000) {
                nextSequence++;
                sequenceFormatted = String.format("%0" + SEQUENCE_LENGTH + "d", nextSequence);
                generatedAccNo = PREFIX + sequenceFormatted;
                attempts++;
            }

            if (attempts >= 1000) {
                throw new RuntimeException("Unable to generate unique account number after 1000 attempts");
            }

            return generatedAccNo;

        } catch (Exception e) {
            // Fallback: generate with current timestamp
            long timestamp = System.currentTimeMillis() % 10000;
            return PREFIX + String.format("%04d", timestamp);
        }
    }
}
