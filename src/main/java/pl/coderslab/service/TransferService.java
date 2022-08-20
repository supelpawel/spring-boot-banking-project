package pl.coderslab.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.entity.Transfer;
import pl.coderslab.repository.TransferRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@AllArgsConstructor
public class TransferService {

    private TransferRepository transferRepository;
    private ExchangeRateService exchangeRateService;

    @Transactional
    public BigDecimal calculateFinalAmount(BigDecimal originalAmount, String fromCurrency, String toCurrency) {

        BigDecimal finalAmount;
        BigDecimal temporaryAmount;

        BigDecimal fromCurrencyExchangeRate = exchangeRateService.getCurrentExchangeRate(fromCurrency);

        temporaryAmount = originalAmount.multiply(fromCurrencyExchangeRate);

        BigDecimal toCurrencyExchangeRate = exchangeRateService.getCurrentExchangeRate(toCurrency);

        if (temporaryAmount.compareTo(toCurrencyExchangeRate) > 0) {

            finalAmount = temporaryAmount.divide(toCurrencyExchangeRate, 2, RoundingMode.HALF_UP);

        } else {

            finalAmount = temporaryAmount.multiply(toCurrencyExchangeRate);
        }

        return finalAmount;
    }

    @Transactional
    public void save(Transfer transfer) {
        transferRepository.save(transfer);
    }

    public List<Transfer> findAll() {
        return transferRepository.findAll();
    }
}
