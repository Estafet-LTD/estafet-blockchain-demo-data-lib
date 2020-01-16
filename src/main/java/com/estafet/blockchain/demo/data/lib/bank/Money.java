package com.estafet.blockchain.demo.data.lib.bank;

public class Money {

	private String walletTransactionId;

	private double amount;

	public String getWalletTransactionId() {
		return walletTransactionId;
	}

	public Money setWalletTransactionId(String walletTransactionId) {
		this.walletTransactionId = walletTransactionId;
		return this;
	}

	public double getAmount() {
		return amount;
	}

	public Money setAmount(double amount) {
		this.amount = amount;
		return this;
	}
	
}
