package org.example;

import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.types.Address;

import java.math.BigInteger;

public class QuickSendTx {
    public static void quickSendTx(Cfx cfx, Account account) throws Exception {
        Address receiver = new Address("cfx:aak7fsws4u4yf38fk870218p1h3gxut3ku67dht7hm");
        for(int i = 0; i < 500; i++) {
            try {
                String hash = account.transfer(receiver, BigInteger.valueOf(100));
                System.out.printf("Sending %d %s\n", i, hash);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
