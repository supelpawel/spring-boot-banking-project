package com.supelpawel.deposit.repository;

import com.supelpawel.deposit.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<Deposit, Long> {

}
