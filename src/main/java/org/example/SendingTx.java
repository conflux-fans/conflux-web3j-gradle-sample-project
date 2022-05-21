package org.example;

import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.CfxUnit;
import conflux.web3j.types.Address;
import conflux.web3j.types.RawTransaction;
import conflux.web3j.types.SendTransactionResult;
import conflux.web3j.types.TransactionBuilder;
import java.math.BigInteger;

/**
 *
 * */
public class SendingTx {
    public static Address to = new Address("cfxtest:aat9v7xdvszbj68x083kjjt52z32g94nwjaj7vdpxf");

    public static void run(Cfx cfx, Account account) {

    }

    public static void transfer(Account account) throws Exception {
        String hash = account.transfer(to, BigInteger.valueOf(100));
        System.out.printf("Tx hash %s", hash);

        // transfer with custom option
        Account.Option op = new Account.Option();
        op.withGasPrice(CfxUnit.DEFAULT_GAS_PRICE);
//        op.withChainId();
//        op.withEpochHeight();
//        op.withStorageLimit();
        hash = account.transfer(op, to, CfxUnit.cfx2Drip(1));
        System.out.printf("Second Tx hash %s", hash);
    }

    public static void howToBuildRawTx(Cfx cfx, Account account) throws Exception {
        BigInteger nonce = cfx.getNonce(account.getAddress()).sendAndGet();
        BigInteger value = CfxUnit.cfx2Drip(1);
        BigInteger currentEpochNumber = cfx.getEpochNumber().sendAndGet();

        // 1. New and set tx field
        RawTransaction rawTx = new RawTransaction();
        rawTx.setTo(to);
        rawTx.setValue(value);
        rawTx.setGasPrice(CfxUnit.DEFAULT_GAS_PRICE);
        // ...

        // 2. Use create method
        RawTransaction rawTx2 = RawTransaction.create(
                nonce,  // nonce
                CfxUnit.DEFAULT_GAS_LIMIT, // gas
                to,                        // to
                value,       // value
                BigInteger.valueOf(0),     // storageLimit
                currentEpochNumber,   // epochHeight
                ""                         // data
        );

        // 3. Use transfer method
        RawTransaction rawTx3 = RawTransaction.transfer(nonce, to, value, currentEpochNumber);

        // 4. Contract deploy tx
        BigInteger gas = BigInteger.valueOf(300000); // gas should be got through estimate method
        BigInteger storageLimit = BigInteger.valueOf(1000); // storage should be got through estimate method
        String bytecode = "0xxxx"; // The bytecode of contract need to deploy
        RawTransaction rawTx4 = RawTransaction.deploy(nonce, gas, storageLimit, currentEpochNumber, bytecode);

        // 5. Contract method call tx
        String data = "0x0000";  // contract method and params abi encode result
        RawTransaction rawTx5 = RawTransaction.call(nonce, gas, to, storageLimit, currentEpochNumber, data);

        String signedRawTx = account.sign(rawTx5);
    }

    public static void transactionBuilderExample(Cfx cfx, Account account) {
        TransactionBuilder tb = new TransactionBuilder(account.getAddress());
        BigInteger currentEpochNumber = cfx.getEpochNumber().sendAndGet();
        tb.withEpochHeight(currentEpochNumber);
        tb.withValue(CfxUnit.cfx2Drip(1));
        tb.withGasPrice(CfxUnit.DEFAULT_GAS_PRICE);
        tb.withData("");
        RawTransaction rawTx = tb.build(cfx);
    }

    public static void send(Account account) throws Exception {
        RawTransaction rawTx = new RawTransaction();
        rawTx.setTo(to);
        rawTx.setValue(CfxUnit.cfx2Drip(1));
        SendTransactionResult sendRes = account.send(rawTx);
    }
}
