package com.estafet.blockchain.demo.data.lib.wallet;



import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.List;

import org.junit.Test;

import com.estafet.blockchain.demo.data.lib.bank.Account;

public class WalletTest { 

	@Test
	public void testCreateWallet() {
		Account account =  Account.createAccount("Michael Ruse", "GBP");
		Wallet wallet = Wallet.getWallet(account.getWalletAddress());
		assertEquals("Michael Ruse", wallet.getWalletName());
	}
	
	
	@Test
	public void testcreateCreditedWallet() {
		Wallet wallet =  Wallet.createCreditedWallet("Dennis Williams", BigInteger.valueOf(30));
		Wallet createdWallet = Wallet.getWallet(wallet.getWalletAddress());
		assertEquals("Dennis Williams", createdWallet.getWalletName());
		assertEquals(300, createdWallet.getBalance());
		assertEquals("CLEARED", createdWallet.getStatus());

	}
	
	@Test
	public void testWalletToWallet() {
		Wallet fromWallet =  Wallet.createCreditedWallet("Dennis Williams", BigInteger.valueOf(30));
		Account account =  Account.createAccount("Michael Ruse", "GBP");
		Wallet toWallet = Wallet.getWallet(account.getWalletAddress());
		Wallet.wallettoWalletTransfer(fromWallet.getWalletAddress(), toWallet.getWalletAddress(), BigInteger.valueOf(10), true);
		toWallet = Wallet.getWallet(toWallet.getWalletAddress());
		assertEquals("Michael Ruse", toWallet.getWalletName());
		assertEquals(10, toWallet.getBalance());
		assertEquals("CLEARED", toWallet.getStatus());

	}
	
}