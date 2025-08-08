package com.example.NFX.Repo.Account;

import com.example.NFX.Constant.CommonStatus;
import com.example.NFX.Entity.Account.SuperAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SuperAccountRepo extends JpaRepository<SuperAccountEntity,Long> {


}
