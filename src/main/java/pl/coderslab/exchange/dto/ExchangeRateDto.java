package pl.coderslab.exchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class ExchangeRateDto {

    private String currency;

    private String code;

    @JsonProperty("mid")
    private BigDecimal currentExchangeRate;
}
