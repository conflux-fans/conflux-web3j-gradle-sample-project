package org.example;

import conflux.web3j.Account;
import conflux.web3j.AccountManager;
import conflux.web3j.Cfx;
import conflux.web3j.request.Epoch;
import conflux.web3j.response.Block;
import conflux.web3j.response.BlockSummary;
import conflux.web3j.response.Receipt;
import conflux.web3j.response.Transaction;
import conflux.web3j.types.Address;
import conflux.web3j.types.CfxAddress;

import java.math.BigInteger;
import java.util.Optional;

public class Basics {
    public static void run(String rpc, String privateKey) {
        // Init client, account, accountManager
        Cfx cfx = Cfx.create(rpc);
        Account account = Account.create(cfx, privateKey);
        System.out.printf("Account base32 address: %s\n", account.getAddress().getAddress());
        System.out.printf("Account hex address: %s\n", account.getHexAddress());
        System.out.printf("Account next usable nonce: %d\n", account.getPoolNonce());

        System.out.println("Examples of calling RPC methods");
        rpcMethodExample(cfx, account);
    }

    public static void rpcMethodExample(Cfx cfx, Account account) {
        BigInteger epoch = cfx.getEpochNumber().sendAndGet();
        System.out.printf("Current epoch number %d \n", epoch);

        Address address = account.getAddress();
        BigInteger nonce = cfx.getNonce(address).sendAndGet();
        System.out.printf("Nonce of %s is %d \n", address, nonce);

        BigInteger balance = cfx.getBalance(address).sendAndGet();
        System.out.printf("Balance of %s is %d \n", address, balance);

        // cfx also can be used to get block, transaction, receipt and logs
        // API doc of Cfx: https://javadoc.io/doc/io.github.conflux-chain/conflux.web3j/latest/index.html
        // Conflux RPC API doc: https://developer.confluxnetwork.org/conflux-doc/docs/json_rpc/
    }

    public static void getBlockExample(Cfx cfx) {
        Epoch epoch = Epoch.numberOf(1000000);
        Optional<BlockSummary> b1 = cfx.getBlockSummaryByEpoch(epoch).sendAndGet();
        // block may get failed
        boolean blockRetriveSuccess = b1.isPresent();
        Optional<Block> blockWithTxDetail = cfx.getBlockByEpoch(epoch).sendAndGet();

        String blockHash = "0xe27f5f566d3f450855e0455ae84c6723ebb477891ffa3ee68af9be518d5b150c";
        Optional<BlockSummary> b3 = cfx.getBlockSummaryByHash(blockHash).sendAndGet();
    }

    public static void getTxAndReceiptExample(Cfx cfx) {
        String txhash = "0x1aed92e97aa70dbc629ae37879915340f47b936a15529bd1e3952783a2efbfcd";
        Optional<Transaction> tx = cfx.getTransactionByHash(txhash).sendAndGet();

        Optional<Receipt> receipt = cfx.getTransactionReceipt(txhash).sendAndGet();
    }

    public static void accountManagerExample(String pk) throws Exception {
        // New accountManager
        AccountManager ac = new AccountManager(1);
        // Import account by private key
        String pwd = "keystore-pwd";
        Optional<Address> addr = ac.imports(pk, pwd);
        // Create new account
        Address addrNew = ac.create(pwd);
        // Check whether an account exist
        boolean exist = ac.exists(addr.get());
        // export account private key
        String privateKey = ac.exportPrivateKey(addrNew, pwd);
//        ac.signMessage()
//        ac.signTransaction()
    }

    public static void accountExample(Cfx cfx, String privateKey) throws Exception {
        Account account = Account.create(cfx, privateKey);

        AccountManager ac = new AccountManager(1);

        Account account2 = Account.unlock(cfx, ac, account.getAddress(), "");

//        account.transfer();
//
//        account.send();
//
//        account.deploy();
//
//        account.call()
    }

    public static void transferCfx(Cfx cfx, Account account) throws Exception {
        String hash = account.transfer(new Address("cfxtest:aat9v7xdvszbj68x083kjjt52z32g94nwjaj7vdpxf"), BigInteger.valueOf(100));
        System.out.printf("Transfer hash is %s\n", hash);
    }

    public static void sendTransaction(Cfx cfx, Account account) {
//        String hash = account.send();
    }
}
