package pl.coderslab.service;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeRateService {

    List<String> getCurrencies();

    BigDecimal getCurrentExchangeRate(String currency);
}
