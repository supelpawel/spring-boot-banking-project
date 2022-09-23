package com.supelpawe.exchange.service;

import com.supelpawe.exchange.dto.ExchangeRateDto;
import com.supelpawe.exchange.dto.ExchangeRateDtoTable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

  private static final String NBP_API_TABLE = "http://api.nbp.pl/api/exchangerates/tables/a?format=json";
  public static final int SCALE = 5;

  @Override
  public List<String> getCurrencies() {
    List<String> currencies;
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<ExchangeRateDtoTable[]> forEntity = restTemplate.getForEntity(NBP_API_TABLE,
        ExchangeRateDtoTable[].class);
    ExchangeRateDtoTable[] body = forEntity.getBody();

    currencies = Arrays.stream(Objects.requireNonNull(body))
        .map(ExchangeRateDtoTable::getExchangeRateDtoList)
        .flatMap(Collection::stream)
        .map(ExchangeRateDto::getCode)
        .collect(Collectors.toList());
    Collections.sort(currencies);
    return currencies;
  }

  @Override
  public BigDecimal getCurrentExchangeRate(String currency) {
    BigDecimal currentExchangeRate;
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<ExchangeRateDtoTable[]> forEntity = restTemplate.getForEntity(NBP_API_TABLE,
        ExchangeRateDtoTable[].class);
    ExchangeRateDtoTable[] body = forEntity.getBody();

    currentExchangeRate = Arrays.stream(Objects.requireNonNull(body))
        .map(ExchangeRateDtoTable::getExchangeRateDtoList)
        .flatMap(Collection::stream)
        .filter(e -> e.getCode().equals(currency))
        .map(ExchangeRateDto::getCurrentExchangeRate)
        .findFirst()
        .get();

    currentExchangeRate = currentExchangeRate.setScale(SCALE, RoundingMode.HALF_UP);
    return currentExchangeRate;
  }
}

