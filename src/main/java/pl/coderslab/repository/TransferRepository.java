package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.entity.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
