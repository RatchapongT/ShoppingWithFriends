package yesmen.cs2340.shoppingwithfriends;

import android.os.AsyncTask;

import java.util.Date;

/**
 * Created by Reese on 3/12/2015.
 */
public class DealAlert {
    Date expirationDate;
    String productName;
    String user;
    int productID;

    public DealAlert(Date expirationDate, String productName, String user) {
        this.expirationDate = expirationDate;
        this.productName = productName;
        this.user = user;
    }

    public void send() {
        new sendDealAlert().execute();
    }

    class sendDealAlert extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... args) {
            return DatabaseInterfacer.sendAlert(user, expirationDate.toString(), productName, productID);
        }
    }
}
