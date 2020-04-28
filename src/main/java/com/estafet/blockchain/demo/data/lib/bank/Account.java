package com.estafet.blockchain.demo.data.lib.bank;


import java.util.*;


import org.springframework.web.client.RestTemplate;

import com.estafet.demo.commons.lib.properties.PropertyUtils;
import com.estafet.demo.commons.lib.wait.WaitUntil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)


public class Account {

	private String id;

	private String walletAddress;

	private String accountName;

	private String currency;

	private Set<Transaction> transactions = new HashSet<Transaction>();

	public String getId() {
		return id;
	}

	public Account setId(String id) {
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

	public  double getBalance(String accountId) {
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
	
	public static  Account getAccount(String id) {
		return new RestTemplate().getForObject(PropertyUtils.instance().getProperty("BANK_API_SERVICE_URI") + "/account/{id}",
				Account.class, id);
	}
	
	public static  Account getAccountBalanceWait(String id, Double balance) {
		Account account = new RestTemplate().getForObject(PropertyUtils.instance().getProperty("BANK_API_SERVICE_URI") + "/account/{id}",
				Account.class, id);
		account.accountBalanceWait(account.getId(), balance);
		return account;
	}
	
	
	@SuppressWarnings("rawtypes")
	public static List<Account> getAccounts() {
		return Arrays.asList(new RestTemplate().getForObject(PropertyUtils.instance().getProperty("BANK_API_SERVICE_URI") + "/accounts", Account[].class));
	}
	
	public static Account getAccountByName(String accountName) {
		for (Account account : getAccounts()) {
			if (account.getAccountName().equals(accountName)) {
				return account;
			}
		}
		return null;
	}
	
	public static void deleteAccounts() {
		 new RestTemplate().delete(PropertyUtils.instance().getProperty("BANK_API_SERVICE_URI") + "/accounts");
	}
	
	public static Transaction getLastTransaction(String accountId) {
		Set<Transaction> transactions = getAccount(accountId).getTransactions();								
		return Collections.max(transactions);
	}
	
	public void transactionClearedWait(String accountId) {
		new WaitUntil(120000) {
			public boolean success() {
				return getLastTransaction(accountId).getStatus().equals("CLEARED");
			}
		}.start();
	}
	
	public void accountCreatedWait(String accountId) {
		new WaitUntil(120000) {
			public boolean success() {
				return getAccount(accountId).getId().equals(accountId);
			}
		}.start();
	}
	
	public void accountBalanceWait(String accountId, Double balance) {
		new WaitUntil(120000) {
			public boolean success() {
				return getBalance(accountId) == balance;
			}
		}.start();
	}
}
