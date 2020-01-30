package com.estafet.blockchain.demo.data.lib.bank;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction implements Comparable<Transaction> {

	private Integer id;

	private String walletTransactionId;
	
	private Account transactionAccount;

	private double amount = 0;

	private String status = "CLEARED";
	
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWalletTransactionId() {
		return walletTransactionId;
	}

	public void setWalletTransactionId(String walletTransactionId) {
		this.walletTransactionId = walletTransactionId;
	}

	public Account getTransactionAccount() {
		return transactionAccount;
	}

	public void setTransactionAccount(Account transactionAccount) {
		this.transactionAccount = transactionAccount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public boolean isCleared() {
		return status.equals("CLEARED");
	}

	public boolean isPending() {
		return status.equals("PENDING");
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int compareTo(Transaction t) {
		if (getId() == null || t.getId() == null) {
		      return 0;
		    }
		    return getId().compareTo(t.getId());
	}
	
}
