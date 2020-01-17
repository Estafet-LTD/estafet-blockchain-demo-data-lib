package com.estafet.blockchain.demo.data.lib.wallet;

import com.estafet.blockchain.demo.data.lib.bank.Account;
import com.estafet.blockchain.demo.data.lib.bank.Money;
import com.estafet.demo.commons.lib.properties.PropertyUtils;
import com.estafet.demo.commons.lib.wait.WaitUntil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Wallet {

    private String walletAddress;

    private String walletName;

    private int balance;

    private String status;

    public String getWalletAddress() {
        return walletAddress;
    }

    public String getWalletName() {
        return walletName;
    }

    public int getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }

	public Wallet setWalletAddress(String walletAddress) {
		this.walletAddress = walletAddress;
		return this;
	}
	
	public Wallet setWalletName(String walletName) {
		this.walletName = walletName;
		return this;
	}
	
	public Wallet setBalance(int balance) {
		this.balance = balance;
		return this;
	}
	
	public Wallet setStatus(String status) {
		this.status = status;
		return this;
	}

    public static Wallet getWallet(String walletAddress) {
        return new RestTemplate().getForObject(PropertyUtils.instance().getProperty("WALLET_MS_SERVICE_URI") + "/wallet/{walletAddress}",
                Wallet.class, walletAddress);
    }

    @SuppressWarnings("rawtypes")
	public static List<Wallet> getWallets() {
        List objects = new RestTemplate().getForObject(PropertyUtils.instance().getProperty("WALLET_MS_SERVICE_URI") + "/wallets",
                List.class);
        List<Wallet> wallets = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for(Object object:objects){
            Wallet wallet = mapper.convertValue(object, new TypeReference<Wallet>(){

            });
            wallets.add(wallet);
        }
        return wallets;
    }
    
    public static Wallet createCreditedWallet(String accountName, String bankCurrency, Double bankBalance  ) {
    	Account account =  Account.createCreditedAccount(accountName, bankCurrency, bankBalance );
    	return Wallet.getWallet(account.getWalletAddress());
    }
    
    public static Wallet createWallet(String accountName, String bankCurrency, Double bankBalance  ) {
    	Account account =  Account.createCreditedAccount(accountName, bankCurrency, bankBalance );
    	return Wallet.getWallet(account.getWalletAddress());
    }
    
    public static Wallet banktoWalletTransfer(String walletAdrress, Double amount, boolean waitForClearance  ) {
    	Wallet wallet =  new RestTemplate().postForObject(PropertyUtils.instance().getProperty("WALLET_MS_SERVICE_URI") + "/wallet/"
    			+ walletAdrress + "/currency-transfer/" + amount, new Wallet().setWalletAddress(walletAdrress), Wallet.class);
		if (waitForClearance) {
			wallet.walletClearedWait(wallet.getWalletAddress());
		}
		return wallet;    	
    }
    
	public void walletClearedWait(String walletAdrress) {
		new WaitUntil() {
			public boolean success() {
				return getWallet(walletAdrress).getStatus().equals("CLEARED");
			}
		}.start();
	}
}
