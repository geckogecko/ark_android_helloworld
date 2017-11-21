package steinbacher.georg.arkandroidtestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.ark.core.Crypto;
import io.ark.core.Network;
import io.ark.core.Transaction;

public class MainActivity extends AppCompatActivity {

    private static final String PASSPHRASE = "this is a top secret passphrase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Crypto.setNetworkVersion(Network.getDevnet().getPrefix());
        System.out.println(newAddressFromPassphrase(PASSPHRASE));

        //Create a transaction on the devnet account
        System.out.println(createDummyTransaction(PASSPHRASE));
    }

    public static String newAddressFromPassphrase(String passphrase) {
        return Crypto.getAddress(Crypto.getKeys(passphrase));
    }

    public static Transaction createDummyTransaction(String passphrase) {
        return Transaction.createTransaction("AN7BURQn5oqBRBADeWhmmUMJGQTy5Seey3", 100000000000L, "vendorField", passphrase);
    }
}
