package com.estafet.blockchain.demo.data.lib.wallet;

import com.estafet.demo.commons.lib.properties.PropertyUtils;
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

    public static Wallet getWallet(String walletAddress) {
        return new RestTemplate().getForObject(PropertyUtils.instance().getProperty("WALLET_MS_SERVICE_URI") + "/wallet/{walletAddress}",
                Wallet.class, walletAddress);
    }

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
}
