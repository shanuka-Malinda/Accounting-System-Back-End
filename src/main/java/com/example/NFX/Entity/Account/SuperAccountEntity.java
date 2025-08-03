package com.example.NFX.Entity.Account;

import com.example.NFX.Constant.CommonStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "supper_account")
@Data
public class SuperAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String reference;
    private String accNo;
    private String description;
    private BigDecimal amount;
    private int creditDebit; //cr=1,dr=0
    private String date;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "common_status")
    private CommonStatus commonStatus;

}
