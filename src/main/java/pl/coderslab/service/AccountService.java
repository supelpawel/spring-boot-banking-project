package pl.coderslab.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.entity.Account;
import pl.coderslab.repository.AccountRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public String getNewAccountNumber() {

        Random r = new Random();
        String accountNumber = "";

        for (int i = 1; i <= 26; i++) {
            accountNumber += Integer.toString(r.nextInt(10) + 0);
        }

        return accountNumber;
    }

    @Transactional
    public void save(Account account) {
        accountRepository.save(account);
    }

    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    public List<Account> findByUserId(long id) {
        return accountRepository.findByUserId(id);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Transactional
    public void update(Account account) {
        accountRepository.save(account);
    }
}

