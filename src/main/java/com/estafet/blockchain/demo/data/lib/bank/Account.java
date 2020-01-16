package com.estafet.blockchain.demo.data.lib.bank;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


import org.springframework.web.client.RestTemplate;

import com.estafet.demo.commons.lib.properties.PropertyUtils;
import com.estafet.demo.commons.lib.wait.WaitUntil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)


public class Account {

	private Integer id;

	private String walletAddress;

	private String accountName;

	private String currency;

	private Set<Transaction> transactions = new HashSet<Transaction>();

	public Integer getId() {
		return id;
	}

	public Account setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getWalletAddress() {
		return walletAddress;
	}

	public Account setWalletAddress(String walletAddress) {
		this.walletAddress = walletAddress;
		return this;
	}

	public String getAccountName() {
		return accountName;
	}

	public Account setAccountName(String accountName) {
		this.accountName = accountName;
		return this;
	}

	public String getCurrency() {
		return currency;
	}

	public Account setCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public Account setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
		return this;
	}

	public  double getBalance(int accountId) {
		double balance = 0;
		Set<Transaction> transactions = getAccount(accountId).getTransactions();
		for (Transaction transaction : transactions) {
			if (transaction.isCleared()) {
				balance += transaction.getAmount();
			}
		}
		return balance;
	}
	
	public static Account createAccount(String accountName, String currency ) {
		Account account =  new RestTemplate().postForObject(PropertyUtils.instance().getProperty("BANK_API_SERVICE_URI") + "/account",
					new Account().setAccountName(accountName).setCurrency(currency),
					Account.class);
			return account;
		}
	
	public static Account creditAccount(Account account, double amount, boolean waitForClearance ) {
		account =  new RestTemplate().postForObject(PropertyUtils.instance().getProperty("BANK_API_SERVICE_URI") + "/account/"
			+ account.getId() + "/credit", new Money().setAmount(amount), Account.class);
		if (waitForClearance) {
			account.transactionClearedWait(account.getId());
		}
		return account;
		}
	
	public static Account debitAccount(Account account, double amount,  boolean waitForClearance ) {
		account =  new RestTemplate().postForObject(PropertyUtils.instance().getProperty("BANK_API_SERVICE_URI") + "/account/"
				+ account.getId() + "/debit", new Money().setAmount(amount), Account.class);
		if (waitForClearance) {
			account.transactionClearedWait(account.getId());
		}
		return account;
		}
	
	
	public static Account createCreditedAccount(String accountName, String currency, double amount ) {
		Account account =  createAccount(accountName, currency );
		account = creditAccount(account,amount, true);
		return account;
		}
	
	public static  Account getAccount(int id) {
		return new RestTemplate().getForObject(PropertyUtils.instance().getProperty("BANK_API_SERVICE_URI") + "/account/{id}",
				Account.class, id);
	}
	
	public static Transaction getLastTransaction(int accountId) {
		Set<Transaction> transactions = getAccount(accountId).getTransactions();								
		return Collections.max(transactions);
	}
	
	public void transactionClearedWait(int accountId) {
		new WaitUntil() {
			public boolean success() {
				return getLastTransaction(accountId).getStatus().equals("CLEARED");
			}
		}.start();
	}
}
