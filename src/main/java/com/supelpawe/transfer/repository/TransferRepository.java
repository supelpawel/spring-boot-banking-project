package com.supelpawe.transfer.repository;

import com.supelpawe.transfer.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

}
