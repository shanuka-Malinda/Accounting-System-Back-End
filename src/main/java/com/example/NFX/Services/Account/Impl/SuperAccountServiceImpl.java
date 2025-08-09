package com.example.NFX.Services.Account.Impl;

import com.example.NFX.Constant.CommonMsg;
import com.example.NFX.Constant.CommonStatus;
import com.example.NFX.Dtos.Account.AccountDto.AccountRequestDto;
import com.example.NFX.Dtos.Account.AccountDto.AccountResponseDto;
import com.example.NFX.Dtos.Account.SuperAccountDto.DateFilterRequest;
import com.example.NFX.Dtos.Account.SuperAccountDto.SuperAccountDto;
import com.example.NFX.Entity.Account.AccountEntity;
import com.example.NFX.Entity.Account.SuperAccountEntity;
import com.example.NFX.Repo.Account.SuperAccountRepo;
import com.example.NFX.Services.Account.SuperAccountService;
import com.example.NFX.Utility.CommonResponse;
import com.example.NFX.Utility.CommonValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

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
    public CommonResponse getAll() {
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<SuperAccountDto> superAccountDtoList = superAccountRepo.findAll()
                    .stream()
                    .filter(superAccountEntity -> superAccountEntity.getCommonStatus() == CommonStatus.ACTIVE)
                    .map(this::entityCastToDto)
                    .toList();
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(superAccountDtoList)); // Direct list instead of singleton
        } catch (DataAccessException e) {
            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(List.of("Database error while retrieving account: " + e.getMessage()));
        } catch (Exception e) {

            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(List.of("Unexpected error while retrieving account: " + e.getMessage()));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse getAll(DateFilterRequest dateFilterRequest) {
        Date startDate=dateFilterRequest.getStartDate();
        Date endDate=dateFilterRequest.getEndDate();
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<SuperAccountDto> superAccountDtoList = superAccountRepo.findAll()
                    .stream()
                    .filter(entity -> entity.getCommonStatus() == CommonStatus.ACTIVE)
                    .filter(entity -> {
                        Date entityDate = entity.getDate();
                        if (entityDate == null) return false;

                        boolean afterStart = startDate == null || !entityDate.before(startDate);
                        boolean beforeEnd = endDate == null || !entityDate.after(endDate);

                        return afterStart && beforeEnd;
                    })
                    .map(this::entityCastToDto)
                    .toList();

            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(superAccountDtoList));


        } catch (DataAccessException e) {
            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(List.of("Database error while retrieving accounts: " + e.getMessage()));
        } catch (Exception e) {
            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(List.of("Unexpected error while retrieving accounts: " + e.getMessage()));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse getAllWithAcc(DateFilterRequest dateFilterRequest) {
        Date startDate = dateFilterRequest.getStartDate();
        Date endDate = dateFilterRequest.getEndDate();
        String accNo = dateFilterRequest.getAccNo();
        System.out.println(dateFilterRequest.getAccNo());
        CommonResponse commonResponse = new CommonResponse();

        try {
            List<SuperAccountDto> superAccountDtoList = superAccountRepo.findAll()
                    .stream()
                    .filter(entity -> entity.getCommonStatus() == CommonStatus.ACTIVE)
                    .filter(entity -> accNo != null && accNo.equals(entity.getAccNo())) // Use equals for string comparison
                    .filter(entity -> {
                        Date entityDate = entity.getDate();
                        if (entityDate == null) return false;

                        boolean afterStart = startDate == null || !entityDate.before(startDate);
                        boolean beforeEnd = endDate == null || !entityDate.after(endDate);

                        return afterStart && beforeEnd;
                    })
                    .map(this::entityCastToDto)
                    .toList();

            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(superAccountDtoList));

        } catch (DataAccessException e) {
            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(List.of("Database error while retrieving accounts: " + e.getMessage()));
        } catch (Exception e) {
            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(List.of("Unexpected error while retrieving accounts: " + e.getMessage()));
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
            commonResponse.setPayload(Collections.singletonList("Journal entries successfully saved"));

        } catch (Exception e) {
            e.printStackTrace();
            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(Collections.singletonList("Error Occurred While Saving Journal Entries"));
        }
        return commonResponse;
    }

    private List<String> validateJournalEntries(List<SuperAccountDto> journalEntries) {
        List<String> validationList = new ArrayList<>();

        // Check if journal entries list is empty or null
        if (journalEntries == null || journalEntries.isEmpty()) {
            validationList.add("Journal entries cannot be empty");
            return validationList;
        }

        // Check minimum entries (at least 2 entries required)
        if (journalEntries.size() < 2) {
            validationList.add("Journal entry must contain at least 2 entries");
            return validationList;
        }

        // Check maximum entries (reasonable limit to prevent abuse)
        if (journalEntries.size() > 50) {
            validationList.add("Journal entry cannot contain more than 50 entries");
            return validationList;
        }

        BigDecimal totalDebit = BigDecimal.ZERO;
        BigDecimal totalCredit = BigDecimal.ZERO;
        String reference = null;
        LocalDate entryDate = null;
        String description = null;

        Set<String> processedAccounts = new HashSet<>();
        boolean hasDebit = false;
        boolean hasCredit = false;

        // Validate each entry and calculate totals
        for (int i = 0; i < journalEntries.size(); i++) {
            SuperAccountDto dto = journalEntries.get(i);

            // Validate individual entry
            List<String> entryValidation = validateSingleEntry(dto, i + 1);
            validationList.addAll(entryValidation);

            // Set reference, date, and description from first entry for consistency check
            if (i == 0) {
                reference = dto.getReference();
                entryDate = dto.getDate().toLocalDate();
                description = dto.getDescription();
            } else {
                // Check if reference is consistent across entries (if provided)
//                if (reference != null && dto.getReference() != null &&
//                        !reference.equals(dto.getReference())) {
//                    validationList.add("All entries must have the same reference number");
//                }

                // Check if date is consistent across entries
//                if (entryDate != null && dto.getDate() != null &&
//                        !entryDate.equals(dto.getDate())) {
//                    validationList.add("All entries must have the same date");
//                }

                // Check if description is consistent across entries
//                if (description != null && dto.getDescription() != null &&
//                        !description.equals(dto.getDescription())) {
//                    validationList.add("All entries must have the same description");
//                }
            }

            // Check for duplicate accounts (optional - remove if same account can have multiple entries)
            String accountKey = dto.getAccNo() + "-" + dto.getCatCode() + "-" + dto.getCreditDebit();
            if (processedAccounts.contains(accountKey)) {
                validationList.add("Duplicate account entry found: " + dto.getAccNo() +
                        " with same debit/credit type");
            }
            processedAccounts.add(accountKey);

            // Calculate totals and check for debit/credit presence
            if (dto.getAmount() != null && dto.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                if (dto.getCreditDebit() == 0) { // Debit
                    totalDebit = totalDebit.add(dto.getAmount());
                    hasDebit = true;
                } else if (dto.getCreditDebit() == 1) { // Credit
                    totalCredit = totalCredit.add(dto.getAmount());
                    hasCredit = true;
                } else {
                    validationList.add("Invalid creditDebit value for entry " + (i + 1) +
                            ". Must be 0 (debit) or 1 (credit)");
                }
            }
        }

        // Check if we have both debits and credits
        if (!hasDebit) {
            validationList.add("Journal entry must contain at least one debit entry");
        }
        if (!hasCredit) {
            validationList.add("Journal entry must contain at least one credit entry");
        }

        // Check if debits equal credits (accounting equation)
        if (totalDebit.compareTo(totalCredit) != 0) {
            validationList.add("Total debits (" + totalDebit + ") must equal total credits (" + totalCredit + ")");
        }

        // Additional business rule validations
        validateBusinessRules(journalEntries, validationList);

        return validationList;
    }

    private List<String> validateSingleEntry(SuperAccountDto dto, int entryNumber) {
        List<String> validationList = new ArrayList<>();

        // Check required fields
        if (dto.getAccNo() == null || dto.getAccNo().trim().isEmpty()) {
            validationList.add("Entry " + entryNumber + ": Account number is required");
        }

        if (dto.getCatCode() == null || dto.getCatCode().trim().isEmpty()) {
            validationList.add("Entry " + entryNumber + ": Category code is required");
        }

        if (dto.getAmount() == null) {
            validationList.add("Entry " + entryNumber + ": Amount is required");
        } else if (dto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            validationList.add("Entry " + entryNumber + ": Amount must be greater than zero");
        }

        if (dto.getCreditDebit()!=0 && dto.getCreditDebit()!=1) {
            validationList.add("Entry " + entryNumber + ": Credit/Debit indicator is required");
        }

        if (dto.getDate() == null) {
            validationList.add("Entry " + entryNumber + ": Date is required");
        }

        if (dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
            validationList.add("Entry " + entryNumber + ": Description is required");
        }

        return validationList;
    }

    private void validateBusinessRules(List<SuperAccountDto> journalEntries, List<String> validationList) {
        // Add any specific business rules here

        // Example: Check if total amount is within reasonable limits
        BigDecimal totalAmount = journalEntries.stream()
                .filter(entry -> entry.getAmount() != null)
                .map(SuperAccountDto::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal maxAllowedAmount = new BigDecimal("1000000"); // 1 million limit
        if (totalAmount.divide(new BigDecimal("2")).compareTo(maxAllowedAmount) > 0) {
            validationList.add("Total transaction amount exceeds maximum allowed limit");
        }
    }

    private List<String> validateSingleEntry(SuperAccountDto superAccountDto){
        List<String> validationList=new ArrayList<>();
        if(!CommonValidation.isNotNullOrEmpty(superAccountDto.getAccNo())){
            validationList.add(CommonMsg.EMPTY_ACCOUNT_NO);
        }
        if(!CommonValidation.isNotNullOrEmpty(superAccountDto.getReference())){
            validationList.add(CommonMsg.EMPTY_REFERENCE);
        }

        if(!CommonValidation.isNotNullOrEmpty(String.valueOf(superAccountDto.getDate()))){
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
        superAccountEntity.setLedgerCode(this.GenerateLedgerCode());
        superAccountEntity.setCatCode(superAccountDto.getCatCode());
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
        superAccountDto.setLedgerCode(superAccountEntity.getLedgerCode());
        superAccountDto.setAccNo(superAccountEntity.getAccNo());
        superAccountDto.setCatCode(superAccountEntity.getCatCode());
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


      private String GenerateLedgerCode(){

          String prefix = "L";
          int codeLength = 6;

          // Get the latest or highest existing ledger code
          String lastCode = superAccountRepo.findMaxLedgerCode(); // e.g., "L000123"

          int nextNumber = 1; // default if no code exists

          if (lastCode!= null && lastCode.startsWith(prefix)) {
              String numericPart = lastCode.substring(1); // "000123"
              try {
                  nextNumber = Integer.parseInt(numericPart) + 1;
              } catch (NumberFormatException e) {
                  // Handle invalid format gracefully
                  nextNumber = 1;
              }
          }

          // Format with leading zeros
          String newCode = prefix + String.format("%0" + codeLength + "d", nextNumber);
          return newCode;

      }


}
