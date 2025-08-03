package com.example.NFX.Repo.Account;

import com.example.NFX.Entity.Account.AccountCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountCategoryRepo extends JpaRepository<AccountCategoryEntity,Long> {
    boolean existsByName(String name);
    boolean existsByCode(String code);
}
