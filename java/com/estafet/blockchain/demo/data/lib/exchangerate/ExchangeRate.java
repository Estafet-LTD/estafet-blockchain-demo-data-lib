package com.estafet.blockchain.demo.data.lib.exchangerate;

import java.util.ArrayList;
import java.util.List;


import org.springframework.web.client.RestTemplate;

import com.estafet.demo.commons.lib.properties.PropertyUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRate {

	private String currency;
	
	private Double rate;

	public String getCurrency() {
		return currency;
	}

	ExchangeRate setCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	public Double getRate() {
		return rate;
	}

	ExchangeRate setRate(Double rate) {
		this.rate = rate;
		return this;
	}
	
	
	public static ExchangeRate getExchangeRate(String currency) {
		Object object = new RestTemplate().getForObject(PropertyUtils.instance().getProperty("EXCHANGE_RATE_API_SERVICE_URI") + "/exchange-rate/" + currency ,
				List.class);
		ObjectMapper mapper = new ObjectMapper();
		
			ExchangeRate exchangeRate = mapper.convertValue(object, new TypeReference<ExchangeRate>() {});
		
		return exchangeRate;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<ExchangeRate> getExchangeRates() {
		List objects = new RestTemplate().getForObject(PropertyUtils.instance().getProperty("EXCHANGE_RATE_API_SERVICE_URI") + "/exchange-rates",
				List.class);
		List<ExchangeRate> exchangeRates = new ArrayList<ExchangeRate>();
		ObjectMapper mapper = new ObjectMapper();
		for (Object object : objects) {
			ExchangeRate exchangeRate = mapper.convertValue(object, new TypeReference<ExchangeRate>() {
			});
			exchangeRates.add(exchangeRate);
		}
		return exchangeRates;
	}
	
	public static void setExchangeRate(String currency, double rate) {
		//Need to check if we have the currency already
		for (ExchangeRate exchangeRate : getExchangeRates()) {
			if (exchangeRate.getCurrency().equals(currency)) {
				//If the currency exists already then we use put
				new RestTemplate().put(PropertyUtils.instance().getProperty("EXCHANGE_RATE_API_SERVICE_URI") + "/exchange-rate",
						new ExchangeRate().setCurrency(currency).setRate(rate));
			} else {
				//Else if we need to create it we use post
				new RestTemplate().postForObject(PropertyUtils.instance().getProperty("EXCHANGE_RATE_API_SERVICE_URI") + "/exchange-rate",
						new ExchangeRate().setCurrency(currency).setRate(rate),ExchangeRate.class);
			}
		}
		return;
	}
	
}
