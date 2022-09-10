package pl.coderslab.deposit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.deposit.model.Deposit;

public interface DepositRepository extends JpaRepository<Deposit, Long> {

}
