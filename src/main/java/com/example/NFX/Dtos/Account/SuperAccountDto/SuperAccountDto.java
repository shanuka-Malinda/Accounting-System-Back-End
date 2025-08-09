package com.example.NFX.Dtos.Account.SuperAccountDto;

import com.example.NFX.Constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuperAccountDto {
        private Long id;
        private String ledgerCode;
        private String reference;
        private String accNo;
        private String catCode;
        private String description;
        private BigDecimal amount;
        private int creditDebit; //cr=1,dr=0
        private Date date;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private CommonStatus commonStatus;
    }
