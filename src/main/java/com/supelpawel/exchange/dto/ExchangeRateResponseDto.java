package com.supelpawel.exchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeRateResponseDto {

  private String currency;
  private String code;
  @JsonProperty("mid")
  private BigDecimal currentExchangeRate;
}