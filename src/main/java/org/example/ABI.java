package org.example;

import conflux.web3j.contract.abi.DecodeUtil;
import conflux.web3j.types.CfxAddress;
import org.web3j.abi.*;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static void decodeDynamicArray() {
        // abi encode [1, 2]
        String abiEncoded = "0x0000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000002";
        TypeReference<DynamicArray<Uint256>> t = new TypeReference<DynamicArray<Uint256>>() {};
        List<Uint256> nums = DecodeUtil.decode(abiEncoded, t);
        System.out.println(nums);
    }

    public static void decodeTxData() {
        // ERC721 transferFrom data
        String data = "0x42842e0e000000000000000000000000167cd6a8c5b31e348a634b4f0788fa2990a180e40000000000000000000000001deffe0e4aab25767f62e0f71424f8d6375899da0000000000000000000000000000000000000000000000000000000000008ba9";
        TypeReference fromTypeReference = TypeReference.create(Address.class);
        TypeReference toTypeReference = TypeReference.create(Address.class);
        TypeReference tokenIdTypeReference = TypeReference.create(Uint256.class);
        // use DefaultFunctionReturnDecoder to data,
        List<Type> list = DefaultFunctionReturnDecoder.decode(data.substring(10), Arrays.asList(fromTypeReference, toTypeReference, tokenIdTypeReference));
        System.out.println(list.get(2).getValue());
    }
}
