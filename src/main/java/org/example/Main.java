package org.example;

import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.contract.internals.SponsorWhitelistControl;
import conflux.web3j.types.Address;
import org.jetbrains.annotations.NotNull;

public class Main {
    static Cfx cfx = Cfx.create(Contants.TEST_NET_RPC);

    static Account account = Account.create(cfx, Contants.PRIVATE_KEY);

    public static void main(String[] args) throws Exception {
        try {
            System.out.println("Hello conflux-web3j");
    //        Basics.run(Contants.TEST_NET_RPC, Contants.PRIVATE_KEY);
    //        SendingTx.run(cfx, account);
//            ContractInteraction.run(cfx, account);
            ABI.event();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @NotNull
    public static Cfx getCfx(boolean useMain) {
        String url = useMain ? Contants.MAIN_NET_RPC : Contants.TEST_NET_RPC;
        return Cfx.create(url);
    }

    public static Account getAccount() {
        return Account.create(cfx, Contants.PRIVATE_KEY);
    }

    public static void sponsorWhiteListControl(Cfx cfx) {
        SponsorWhitelistControl sponsorWhitelistControl = new SponsorWhitelistControl(cfx);
        String contractAddr = "cfx:acdbgvym86jmf75vfkgc2t74tt8tctn0buc1f88yvf";
        String userAddr = "cfx:aasdcss5zuydyzwm0cz23vbp5h5ntvd6pjvjcg8c9s";
        boolean whiteListed = sponsorWhitelistControl.isWhitelisted(new Address(contractAddr).getABIAddress(), new Address(userAddr).getABIAddress());
        System.out.println(whiteListed);
    }
}