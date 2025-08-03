package com.example.NFX.Repo.Account;

import com.example.NFX.Entity.Account.SuperAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperAccountRepo extends JpaRepository<SuperAccountEntity,Long> {
}
