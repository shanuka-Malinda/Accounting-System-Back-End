package com.example.NFX.Entity.Account;

import com.example.NFX.Constant.CommonStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "account")
@Data
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String accNo;

    private String catCode;

    @Column(name = "name", length = 100)
    private String name;

    private String description;
    private String createdBy;
    private String updatedBy;

    private BigDecimal openingBalance;
    private BigDecimal currentBalance;
    private BigDecimal creditLimit;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "common_status")
    private CommonStatus commonStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private AccountCategoryEntity accountCategory;
}
