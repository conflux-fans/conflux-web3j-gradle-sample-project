package org.example;

import conflux.web3j.types.CfxAddress;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;

import java.util.ArrayList;
import java.util.List;

public class ABI {
    public static void event() {
        // event Transfer(address indexed from, address indexed to, uint256 value)
        List<TypeReference<?>> paramList = new ArrayList<>();
        paramList.add(TypeReference.create(Address.class, true));
        paramList.add(TypeReference.create(Address.class, true));
        paramList.add(TypeReference.create(Uint256.class, false));
        Event transferEvent = new Event("Transfer", paramList);
        String signature = EventEncoder.encode(transferEvent);
        System.out.printf("Transfer event signature %s\n", signature);

        String signature2 = EventEncoder.buildEventSignature("Transfer(address,address,uint256)");
        System.out.println(signature2);
    }

    public static void primitiveEncode() {
        CfxAddress from = new CfxAddress("cfxtest:aak7fsws4u4yf38fk870218p1h3gxut3ku00u1k1da");
        Address a = new Address(from.getHexAddress());
        String encodedValue = "0x" + TypeEncoder.encode(a);
        System.out.println(encodedValue);
    }
}
