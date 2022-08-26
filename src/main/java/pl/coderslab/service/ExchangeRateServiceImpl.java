package pl.coderslab.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.dto.ExchangeRateDto;
import pl.coderslab.dto.ExchangeRateDtoTable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private static final String NBP_API_TABLE= "http://api.nbp.pl/api/exchangerates/tables/a?format=json";

    @Override
    public List<String> getCurrencies() {

        List<String> currencies;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ExchangeRateDtoTable[]> forEntity = restTemplate.getForEntity(NBP_API_TABLE, ExchangeRateDtoTable[].class);

        ExchangeRateDtoTable[] body = forEntity.getBody();

        currencies = Arrays.stream(body)
                .map(ExchangeRateDtoTable::getExchangeRateDtoList)
                .flatMap(e -> e.stream())
                .map(e -> e.getCode())
                .collect(Collectors.toList());

        Collections.sort(currencies);

        return currencies;
    }

    @Override
    public BigDecimal getCurrentExchangeRate(String currency) {

        BigDecimal currentExchangeRate;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ExchangeRateDtoTable[]> forEntity = restTemplate.getForEntity(NBP_API_TABLE, ExchangeRateDtoTable[].class);

        ExchangeRateDtoTable[] body = forEntity.getBody();

        currentExchangeRate = Arrays.stream(body)
                .map(ExchangeRateDtoTable::getExchangeRateDtoList)
                .flatMap(e -> e.stream())
                .filter(e -> e.getCode().equals(currency))
                .map(ExchangeRateDto::getCurrentExchangeRate)
                .findFirst()
                .get();

        currentExchangeRate = currentExchangeRate.setScale(5, RoundingMode.HALF_UP);

        return currentExchangeRate;
    }
}

