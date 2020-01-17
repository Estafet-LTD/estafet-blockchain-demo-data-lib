package com.estafet.blockchain.demo.data.lib.gateway;

import java.math.BigInteger;

import org.springframework.web.client.RestTemplate;

import com.estafet.demo.commons.lib.properties.PropertyUtils;

public class WalletBalance {

	private String address;
	
	private BigInteger balance;

	public String getAddress() {
		return address;
	}

	public WalletBalance setAddress(String address) {
		this.address = address;
		return this;
	}

	public BigInteger getBalance() {
		return balance;
	}

	public WalletBalance setBalance(BigInteger balance) {
		this.balance = balance;
		return this;

	}
	
	public static  WalletBalance getAccount(String address) {
		return new RestTemplate().getForObject(PropertyUtils.instance().getProperty("BANK_API_SERVICE_URI") + "/balance/{address}",
				WalletBalance.class, address);
	}
	
}
