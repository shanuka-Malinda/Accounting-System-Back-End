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

import java.math.BigDecimal;
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
    public CommonResponse saveSingleEntry(SuperAccountDto superAccountDto) {
        CommonResponse commonResponse= new CommonResponse();
        try {
            List<String> validationList= validateSingleEntry(superAccountDto);
            if (!validationList.isEmpty()){
                commonResponse.setStatus(false);
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }
            SuperAccountEntity superAccountEntity= dtoCastToEntity(superAccountDto);
            SuperAccountEntity saveSuperAccountEntity= superAccountRepo.save(superAccountEntity);

            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList("Successfully single entry saved"));
        }catch (Exception e){
            e.printStackTrace();
            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(Collections.singletonList("Error Occurred While Account Saving"));

        }
        return commonResponse;
    }



    @Override
    public CommonResponse saveDoubleEntry(List<SuperAccountDto> journalEntries) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            // Validate the journal entries
            List<String> validationList = validateJournalEntries(journalEntries);
            if (!validationList.isEmpty()) {
                commonResponse.setStatus(false);
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }

            // Convert DTOs to entities
            List<SuperAccountEntity> entities = new ArrayList<>();
            for (SuperAccountDto dto : journalEntries) {
                SuperAccountEntity entity = dtoCastToEntity(dto);
                entities.add(entity);
            }

            // Save all entries as a batch (transactional)
            List<SuperAccountEntity> savedEntities = superAccountRepo.saveAll(entities);

            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList("Entry successfully saved"));

        } catch (Exception e) {
            e.printStackTrace();
            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(Collections.singletonList("Error Occurred While Saving Journal Entries"));
        }
        return commonResponse;
    }

    // Updated validation method for journal entries
    private List<String> validateJournalEntries(List<SuperAccountDto> journalEntries) {
        List<String> validationList = new ArrayList<>();

        // Check if journal entries list is empty or null
        if (journalEntries == null || journalEntries.isEmpty()) {
            validationList.add("Journal entries cannot be empty");
            return validationList;
        }

        // Check if we have exactly 2 entries (debit and credit)
        if (journalEntries.size() != 2) {
            validationList.add("Journal entry must contain exactly 2 entries (debit and credit)");
            return validationList;
        }

        BigDecimal totalDebit = BigDecimal.ZERO;
        BigDecimal totalCredit = BigDecimal.ZERO;
        String reference = null;
        String date = null;

        // Validate each entry and calculate totals
        for (int i = 0; i < journalEntries.size(); i++) {
            SuperAccountDto dto = journalEntries.get(i);
            List<String> entryValidation = validateSingleEntry(dto, i + 1);
            validationList.addAll(entryValidation);

            // Set reference and date from first entry for consistency check
            if (i == 0) {
                reference = dto.getReference();
                date = dto.getDate();
            } else {
                // Check if reference and date are consistent across entries
                if (!reference.equals(dto.getReference())) {
                    validationList.add("All entries must have the same reference number");
                }
                if (!date.equals(dto.getDate())) {
                    validationList.add("All entries must have the same date");
                }
            }

            // Calculate totals
            if (dto.getAmount() != null) {
                if (dto.getCreditDebit() == 0) { // Debit
                    totalDebit = totalDebit.add(dto.getAmount());
                } else if (dto.getCreditDebit() == 1) { // Credit
                    totalCredit = totalCredit.add(dto.getAmount());
                }
            }
        }

        // Check if debits equal credits
        if (totalDebit.compareTo(totalCredit) != 0) {
            validationList.add("Total debits must equal total credits");
        }

        return validationList;
    }

    // Individual entry validation
    private List<String> validateSingleEntry(SuperAccountDto superAccountDto, int entryNumber) {
        List<String> validationList = new ArrayList<>();
        String prefix = "Entry " + entryNumber + ": ";

        if (!CommonValidation.isNotNullOrEmpty(superAccountDto.getAccNo())) {
            validationList.add(prefix + CommonMsg.EMPTY_ACCOUNT_NO);
        }
        if (!CommonValidation.isNotNullOrEmpty(superAccountDto.getReference())) {
            validationList.add(prefix + CommonMsg.EMPTY_REFERENCE);
        }
        if (!CommonValidation.isNotNullOrEmpty(superAccountDto.getDate())) {
            validationList.add(prefix + CommonMsg.EMPTY_INVOICE_DATE);
        }
        if (!CommonValidation.isNotNullOrEmpty(superAccountDto.getDescription())) {
            validationList.add(prefix + CommonMsg.EMPTY_DESCRIPTION);
        }
        if (superAccountDto.getAmount() == null || superAccountDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            validationList.add(prefix + CommonMsg.EMPTY_AMOUNT);
        }
        if (superAccountDto.getCreditDebit() != 0 && superAccountDto.getCreditDebit() != 1) {
            validationList.add(prefix + "Credit/Debit flag must be 0 (debit) or 1 (credit)");
        }

        return validationList;
    }


    private List<String> validateSingleEntry(SuperAccountDto superAccountDto){
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
    // Keep the existing dtoCastToEntity method as is
    private SuperAccountEntity dtoCastToEntity(SuperAccountDto superAccountDto) {
        SuperAccountEntity superAccountEntity = new SuperAccountEntity();
        superAccountEntity.setDescription(superAccountDto.getDescription());
        superAccountEntity.setAccNo(superAccountDto.getAccNo());
        superAccountEntity.setAmount(superAccountDto.getAmount());
        superAccountEntity.setCreditDebit(superAccountDto.getCreditDebit());
        superAccountEntity.setDate(superAccountDto.getDate());
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





}
