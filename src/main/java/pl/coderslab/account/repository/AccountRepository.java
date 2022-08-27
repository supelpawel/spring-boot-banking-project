package pl.coderslab.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.account.data.Account;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUserId(long id);
}
