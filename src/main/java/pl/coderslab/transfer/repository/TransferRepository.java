package pl.coderslab.transfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.transfer.data.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
