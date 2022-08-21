package pl.coderslab.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.entity.Deposit;
import pl.coderslab.repository.DepositRepository;

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
