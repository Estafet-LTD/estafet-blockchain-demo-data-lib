package com.estafet.blockchain.demo.data.lib.exchangerate;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class ExchangeRateTest {

	@Test
	public void testSetGetExchangeRate() {
		ExchangeRate.setExchangeRate("GBP", 25);
		ExchangeRate exchangeRate = ExchangeRate.getExchangeRate("GBP");
		assertEquals(25.0, exchangeRate.getRate(),0.1);
	}
	
	@Test
	public void testDeleteExchangeRate() {
		ExchangeRate.setExchangeRate("USD", 150); 
		ExchangeRate.deleteExchangeRates();
		 List<ExchangeRate> exchangeRates = ExchangeRate.getExchangeRates();
		assert(exchangeRates.isEmpty());
	}
}