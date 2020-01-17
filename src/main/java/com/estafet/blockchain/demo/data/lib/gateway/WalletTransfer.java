package com.estafet.blockchain.demo.data.lib.gateway;


import java.math.BigInteger;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


import org.springframework.web.client.RestTemplate;

import com.estafet.blockchain.demo.data.lib.wallet.Wallet;
import com.estafet.demo.commons.lib.properties.PropertyUtils;
import com.estafet.demo.commons.lib.wait.WaitUntil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)


public class WalletTransfer {


	private BigInteger amount;
	
	private String fromAddress;
	
	private String toAddress;



	public BigInteger getAmount() {
		return amount;
	}

	public WalletTransfer setAmount(BigInteger amount) {
		this.amount = amount;
		return this;

	}

	public String getFromAddress() {
		return fromAddress;
	}

	public WalletTransfer setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
		return this;
	}
	


	public String getToAddress() {
		return toAddress;
	}

	public WalletTransfer setToAddress(String toAddress) {
		this.toAddress = toAddress;
		return this;

	}


	
	public static WalletTransfer transferEstacoinFromBank(BigInteger amount, String toAddress ) {
		WalletTransfer walletTransfer =  new RestTemplate().postForObject(PropertyUtils.instance().getProperty("GATEWAY_API_SERVICE_URI") + "/transfer-from-bank",
					new WalletTransfer().setAmount(amount).setFromAddress(PropertyUtils.instance().getProperty("BANK_ADDRESS")).setToAddress(toAddress),
					WalletTransfer.class);
			return walletTransfer;
		}
	

}
