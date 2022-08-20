package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.entity.Deposit;

public interface DepositRepository extends JpaRepository<Deposit, Long> {
}
