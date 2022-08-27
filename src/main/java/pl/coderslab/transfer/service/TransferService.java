package pl.coderslab.transfer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.account.data.Account;
import pl.coderslab.account.service.AccountService;
import pl.coderslab.exchange.service.ExchangeRateService;
import pl.coderslab.transfer.data.Transfer;
import pl.coderslab.transfer.repository.TransferRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@AllArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;
    private final ExchangeRateService exchangeRateService;
    private final AccountService accountService;

    @Transactional
    public void processMakeTransferForm(Transfer transfer) {

        Account fromAccount = transfer.getFromAccount();
        Account toAccount = transfer.getToAccount();

        String fromCurrency = fromAccount.getCurrency();
        String toCurrency = toAccount.getCurrency();

        BigDecimal originalAmount = transfer.getOriginalAmount();
        BigDecimal finalAmount = calculateFinalAmount(originalAmount, fromCurrency, toCurrency);

        transfer.setFinalAmount(finalAmount);
        save(transfer);

        fromAccount.setBalance(fromAccount.getBalance().subtract(originalAmount));
        toAccount.setBalance(toAccount.getBalance().add(finalAmount));

        LocalDateTime transferDate = LocalDateTime.now(ZoneId.of("Europe/Warsaw"));
        transfer.setTransferDate(transferDate);

        accountService.update(fromAccount);
        accountService.update(toAccount);
    }

    @Transactional
    public BigDecimal calculateFinalAmount(BigDecimal originalAmount, String fromCurrency, String toCurrency) {

        BigDecimal finalAmount;
        BigDecimal temporaryAmount;
        BigDecimal fromCurrencyExchangeRate;
        BigDecimal toCurrencyExchangeRate;
        BigDecimal temporaryExchangeRate;

        fromCurrencyExchangeRate = exchangeRateService.getCurrentExchangeRate(fromCurrency);
        toCurrencyExchangeRate = exchangeRateService.getCurrentExchangeRate(toCurrency);

        temporaryAmount = originalAmount.multiply(fromCurrencyExchangeRate);
        temporaryExchangeRate = fromCurrencyExchangeRate.multiply(temporaryAmount);

        if (temporaryExchangeRate.compareTo(toCurrencyExchangeRate) > 0) {

            finalAmount = temporaryAmount.divide(toCurrencyExchangeRate, 5, RoundingMode.HALF_UP);

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
