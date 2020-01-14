package com.estafet.blockchain.demo.data.lib.wallet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    public static Wallet getWallet(String walletAddress) {
        return new Wallet();
    }

    public static List<Wallet> getWallets() {
        return new ArrayList<>();
    }
}
