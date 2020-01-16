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
	
	private double rate;

	public String getCurrency() {
		return currency;
	}

	ExchangeRate setCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	public double getRate() {
		return rate;
	}

	ExchangeRate setRate(double rate) {
		this.rate = rate;
		return this;
	}
	
	/*
	public static ExchangeRate getExchangeRate(String currency) {
		
		Object object = new RestTemplate().getForObject(PropertyUtils.instance().getProperty("CURRENCY_CONVERTER_SERVICE_URI") + "/exchange-rate/" + currency ,
				List.class);
		ObjectMapper mapper = new ObjectMapper();
		
			ExchangeRate exchangeRate = mapper.convertValue(object, new TypeReference<ExchangeRate>() {});
		
			
			
			for (ExchangeRate exchangeRate : getExchangeRates()) {
				if (exchangeRate.getCurrency() == currency) {
					return exchangeRate;
				}
			}
			return null;

	}*/
	
	public static void deleteExchangeRates() {
		 new RestTemplate().delete(PropertyUtils.instance().getProperty("CURRENCY_CONVERTER_SERVICE_URI") + "/exchange-rates");
	return;
	}
	
	
	public static  ExchangeRate getExchangeRate(String currency) {
		return new RestTemplate().getForObject(PropertyUtils.instance().getProperty("CURRENCY_CONVERTER_SERVICE_URI") + "/exchange-rate/{currency}",
				ExchangeRate.class, currency);
	}
	
	@SuppressWarnings("rawtypes")
	public static List<ExchangeRate> getExchangeRates() {
		List objects = new RestTemplate().getForObject(PropertyUtils.instance().getProperty("CURRENCY_CONVERTER_SERVICE_URI") + "/exchange-rates",
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
		//First we need to check if we have the currency already
		Boolean currencyExists = false; 
		 System.out.println("Currency is: " + currency); 

		for (ExchangeRate exchangeRate : getExchangeRates()) {
			 System.out.println("Currency: " + exchangeRate.getCurrency()); 

			if (exchangeRate.getCurrency().equals(currency)) {
				//Record if the currency exists
				currencyExists = true;

			}
		}
		 System.out.println( "Exists: " + currencyExists);
		if(currencyExists) {
			//If it exists use put
			System.out.println("Updating currency (put)");
			 RestTemplate restTemplate = new RestTemplate();
			restTemplate.put(PropertyUtils.instance().getProperty("CURRENCY_CONVERTER_SERVICE_URI") + "/exchange-rate",
				 new ExchangeRate().setCurrency(currency).setRate(rate));
		} else {
			//If not exist use post
			System.out.println("Creating currency (post)");
			 new RestTemplate().postForObject(PropertyUtils.instance().getProperty("CURRENCY_CONVERTER_SERVICE_URI") + "/exchange-rate",
				new ExchangeRate().setCurrency(currency).setRate(rate),ExchangeRate.class);
		}
		
		return;
	}
	
}
