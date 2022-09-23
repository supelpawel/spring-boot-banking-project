package com.supelpawel.exchange.service;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeRateService {

  List<String> getCurrencies();

  BigDecimal getCurrentExchangeRate(String currency);
}
