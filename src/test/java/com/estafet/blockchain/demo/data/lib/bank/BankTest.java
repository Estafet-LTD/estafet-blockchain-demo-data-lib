package com.estafet.blockchain.demo.data.lib.bank;



import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.estafet.blockchain.demo.data.lib.exchangerate.ExchangeRate;

public class BankTest { 

	@Test
	public void testCreateAccount() {
		Account account =  Account.createAccount("Michael Ruse", "GBP");
		Account createdAccount = Account.getAccount(account.getId());
		assertEquals("Michael Ruse", createdAccount.getAccountName());
	}
	
	@Test
	public void testCreditAccount() {
		Account account =  Account.createAccount("Iryna Poplavska", "BGN");
		Account.creditAccount(account,3000,false);
		Transaction transaction = Account.getLastTransaction(account.getId());
		assertEquals(3000.0, transaction.getAmount(),0.1);
		assertEquals("CLEARED", transaction.getStatus());

	}
	/*test wait needs something to update to pending
	@Test
	public void testDebitAccount() {
		Account account =  Account.createCreditedAccount("Shukri Shukriev", "USD",60000);
		Account.debitAccount(account,250,true);
		Transaction transaction = Account.getLastTransaction(account.getId());
		assertEquals(-250.0, transaction.getAmount(),0.1);
		assertEquals("CLEARED", transaction.getStatus());
	}
*/
	
	@Test
	public void testDebitAccount() {
		Account account =  Account.createCreditedAccount("Shukri Shukriev", "USD",60000);
		Account.debitAccount(account,250,false);
		Transaction transaction = Account.getLastTransaction(account.getId());
		assertEquals(-250.0, transaction.getAmount(),0.1);
		assertEquals("PENDING", transaction.getStatus());
	}
	
	@Test
	public void testGetLastTransaction() {
		Account account =  Account.createAccount("Desilava Ivanova", "CAD");
		Account.creditAccount(account,3000,false);
		Account.creditAccount(account,300,false);
		Account.creditAccount(account,2500,false);
		Transaction transaction = Account.getLastTransaction(account.getId());
		assertEquals(2500.0, transaction.getAmount(),0.1);
		assertEquals("CLEARED", transaction.getStatus());
		assertEquals(5800.0, account.getBalance(account.getId()),0.1);


	}
	
	@Test
	public void testCreateCreditedAccount() {
		Account account =  Account.createCreditedAccount("Dennis Williams", "USD",1050);
		Account createdAccount = Account.getAccount(account.getId());
		Transaction transaction = Account.getLastTransaction(account.getId());
		assertEquals("Dennis Williams", createdAccount.getAccountName());
		assertEquals(1050, transaction.getAmount(),0.1);
		assertEquals("CLEARED", transaction.getStatus());

	}
	
	@Test
	public void testDeleteAccounts() {
		Account.createCreditedAccount("Georgi Petkov", "USD",1050);
		Account.deleteAccounts();
		 List<Account> accounts = Account.getAccounts();
		assert(accounts.isEmpty());
	}
	
	@Test
	public void testGetAccountByName() {
		Account account =  Account.createAccount("Michael Ruse", "USD");
		Account createdAccount = Account.getAccountByName(account.getAccountName());
		assertEquals("Michael Ruse", createdAccount.getAccountName());
	}
	
}