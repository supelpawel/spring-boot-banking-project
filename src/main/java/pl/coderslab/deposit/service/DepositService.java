package pl.coderslab.deposit.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.deposit.data.Deposit;
import pl.coderslab.deposit.repository.DepositRepository;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class DepositService {

    private final DepositRepository depositRepository;

    @Transactional
    public void save(Deposit deposit) {
        depositRepository.save(deposit);
    }
}