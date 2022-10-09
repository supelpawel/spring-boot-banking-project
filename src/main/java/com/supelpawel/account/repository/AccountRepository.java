package com.supelpawel.account.repository;

import com.supelpawel.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

  List<Account> findByUserId(long id);
}