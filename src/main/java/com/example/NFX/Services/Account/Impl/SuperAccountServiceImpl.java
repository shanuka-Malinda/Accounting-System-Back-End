package com.example.NFX.Services.Account.Impl;

import com.example.NFX.Constant.CommonMsg;
import com.example.NFX.Constant.CommonStatus;
import com.example.NFX.Dtos.Account.AccountDto.AccountRequestDto;
import com.example.NFX.Dtos.Account.SuperAccountDto.SuperAccountDto;
import com.example.NFX.Entity.Account.AccountEntity;
import com.example.NFX.Entity.Account.SuperAccountEntity;
import com.example.NFX.Repo.Account.SuperAccountRepo;
import com.example.NFX.Services.Account.SuperAccountService;
import com.example.NFX.Utility.CommonResponse;
import com.example.NFX.Utility.CommonValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SuperAccountServiceImpl implements SuperAccountService {
    private final SuperAccountRepo superAccountRepo;

    @Autowired
    public SuperAccountServiceImpl(SuperAccountRepo superAccountRepo) {
        this.superAccountRepo = superAccountRepo;
    }

    @Override
    public CommonResponse saveDoubleEntry(SuperAccountDto superAccountDto) {
        CommonResponse commonResponse= new CommonResponse();
        try {
            List<String> validationList= SupperAccountValidation(superAccountDto);
            if (!validationList.isEmpty()){
                commonResponse.setStatus(false);
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }
            SuperAccountEntity superAccountEntity= dtoCastToEntity(superAccountDto);
            SuperAccountEntity saveSuperAccountEntity= superAccountRepo.save(superAccountEntity);

            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(saveSuperAccountEntity));
        }catch (Exception e){
            e.printStackTrace();
            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(Collections.singletonList("Error Occurred While Account Saving"));

        }
        return commonResponse;
    }

    private SuperAccountEntity dtoCastToEntity(SuperAccountDto superAccountDto){
        SuperAccountEntity superAccountEntity=new SuperAccountEntity();
        superAccountEntity.setDescription(superAccountDto.getDescription());
        superAccountEntity.setAccNo(superAccountDto.getAccNo());
        superAccountEntity.setAmount(superAccountDto.getAmount());
        superAccountEntity.setCreditDebit(superAccountDto.getCreditDebit());
        superAccountEntity.setDate(superAccountDto.getDate());
        superAccountEntity.setCreditDebit(superAccountDto.getCreditDebit());
        superAccountEntity.setReference(superAccountDto.getReference());
        superAccountEntity.setCommonStatus(CommonStatus.ACTIVE);

        return superAccountEntity;
    }
    private SuperAccountDto entityCastToDto(SuperAccountEntity superAccountEntity){
        SuperAccountDto superAccountDto=new SuperAccountDto();
        superAccountDto.setId(superAccountEntity.getId());
        superAccountDto.setAccNo(superAccountEntity.getAccNo());
        superAccountDto.setAmount(superAccountEntity.getAmount());
        superAccountDto.setDescription(superAccountEntity.getDescription());
        superAccountDto.setDate(superAccountEntity.getDate());
        superAccountDto.setCreditDebit(superAccountEntity.getCreditDebit());
        superAccountDto.setReference(superAccountEntity.getReference());
        superAccountDto.setCommonStatus(superAccountEntity.getCommonStatus());
        superAccountDto.setCreatedAt(superAccountEntity.getCreatedAt());
        superAccountDto.setUpdatedAt(superAccountEntity.getUpdatedAt());

        return superAccountDto;
    }

    private List<String> SupperAccountValidation(SuperAccountDto superAccountDto){
        List<String> validationList=new ArrayList<>();
        if(!CommonValidation.isNotNullOrEmpty(superAccountDto.getAccNo())){
            validationList.add(CommonMsg.EMPTY_ACCOUNT_NO);
        }
        if(!CommonValidation.isNotNullOrEmpty(superAccountDto.getReference())){
            validationList.add(CommonMsg.EMPTY_REFERENCE);
        }

        if(!CommonValidation.isNotNullOrEmpty(superAccountDto.getDate())){
            validationList.add(CommonMsg.EMPTY_INVOICE_DATE);
        }
        if(!CommonValidation.isNotNullOrEmpty(superAccountDto.getDescription())){
            validationList.add(CommonMsg.EMPTY_DESCRIPTION);
        }
        if(!CommonValidation.isNotNullOrEmpty(String.valueOf(superAccountDto.getAmount()))){
            validationList.add(CommonMsg.EMPTY_AMOUNT);
        }
        if(!CommonValidation.isNotNullOrEmpty(String.valueOf(superAccountDto.getCreditDebit()))){
            validationList.add(CommonMsg.EMPTY_CRDR);
        }


        return validationList;
    }
}
