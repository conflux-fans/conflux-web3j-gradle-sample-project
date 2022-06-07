package org.example;

import conflux.web3j.Cfx;
import conflux.web3j.request.Epoch;
import conflux.web3j.request.LogFilter;
import conflux.web3j.response.Log;
import conflux.web3j.response.events.EpochNotification;
import conflux.web3j.response.events.LogNotification;
import conflux.web3j.response.events.NewHeadsNotification;
import conflux.web3j.types.Address;
import conflux.web3j.types.CfxAddress;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeEncoder;
import org.web3j.protocol.websocket.WebSocketService;

import java.math.BigInteger;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PubSub {
    public static void subLog () throws ConnectException {
        // initiate a WebSocketService and connect, then use it to create a Cfx
        WebSocketService wsService = new WebSocketService("wss://test.confluxrpc.com/ws", false);
        wsService.connect();
        Cfx cfx = Cfx.create(wsService);

        // construct the filter parameter
        LogFilter filter = new LogFilter();
        // To filter address
        List<Address> toFilterAddress = new ArrayList<Address>();
        toFilterAddress.add(new Address("cfxtest:acattk1yzt7b8z01m8urnmytst1z0wv0tuvczsyr32"));
        filter.setAddress(toFilterAddress);

        // Filter topics
//        List<List<String>> topics = new ArrayList<>();
//        List<String> sigTopic = new ArrayList<>();
//        String transferSig = EventEncoder.buildEventSignature("Transfer(address,address,uint256)");
//        sigTopic.add(transferSig);
//        topics.add(sigTopic);

        // first indexed parameter topic
//        CfxAddress from = new CfxAddress("cfxtest:aak7fsws4u4yf38fk870218p1h3gxut3ku00u1k1da");
//        org.web3j.abi.datatypes.Address a = new org.web3j.abi.datatypes.Address(from.getHexAddress());
//        String fromAddress = "0x" + TypeEncoder.encode(a);
//        List<String> firstParameter = new ArrayList<>();
//        firstParameter.add(fromAddress);

//        filter.setTopics(topics);

        // Add two or three
        /*final Flowable<EpochNotification> events1 = cfx.subscribeEpochs();
        final Disposable disposable1 = events1.subscribe(event -> {
            // You can get the detail through getters
            System.out.println(event.getParams().getResult());
//            System.out.println("epoch");
        });*/

        final Flowable<LogNotification> events2 = cfx.subscribeLogs(filter);
        final Disposable disposable2 = events2.subscribe(event -> {
//            Log log = event.getParams().getResult();
            System.out.println("log");
            // get and decode log topic or data
            // https://github.com/conflux-fans/crypto-knowledge/blob/main/blogs/java-sdk-abi-encode.md#event-%E7%BC%96%E8%A7%A3%E7%A0%81
        });
        disposable2.dispose();

        /*final Flowable<NewHeadsNotification> events = cfx.subscribeNewHeads();
        final Disposable disposable = events.subscribe(event -> {
            // You can get the detail through getters
//            System.out.println(event.getParams().getResult());
            System.out.println("hihi");
        });
        // close
        disposable.dispose();*/
    }
}
