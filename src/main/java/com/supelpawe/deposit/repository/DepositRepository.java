package com.supelpawe.deposit.repository;

import com.supelpawe.deposit.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<Deposit, Long> {

}
