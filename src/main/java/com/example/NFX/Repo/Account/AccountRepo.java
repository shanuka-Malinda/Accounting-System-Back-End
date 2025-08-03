package com.example.NFX.Repo.Account;

import com.example.NFX.Entity.Account.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<AccountEntity,Long> {
    @Query("SELECT a FROM AccountEntity a WHERE a.accNo LIKE 'AC100M%' ORDER BY a.accNo DESC")
    Optional<AccountEntity> findLatestAccountNumber();

    // Check if account number already exists
    boolean existsByAccNo(String accNo);

    // Alternative query to get the maximum account number
    @Query("SELECT MAX(a.accNo) FROM AccountEntity a WHERE a.accNo LIKE 'AC100M%'")
    Optional<String> findMaxAccountNumber();

    boolean existsByName(String name);

//    boolean existsName(String name);
}
