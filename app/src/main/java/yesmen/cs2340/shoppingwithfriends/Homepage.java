package yesmen.cs2340.shoppingwithfriends;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class Homepage extends ActionBarActivity and implements View.OnClickListener,
 * is all the code that pertains to the homepage in android.
 *
 * @author Luka Antolic-Soban, Resse Aitken, Ratchapong Tangkijvorakul, Matty Attokaren, Sunny Patel
 * @version 1.6
 */
public class Homepage extends Activity implements View.OnClickListener {

    private Button logoutButton, viewButton, wishlistButton, viewwishlistButton;
    private ImageButton blue_button;
    //private TextView notificaiton;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        logoutButton = (Button) findViewById(R.id.homepage_logout_button);
        logoutButton.setOnClickListener(this);

        viewButton = (Button) findViewById(R.id.homepage_view_friends_button);
        viewButton.setOnClickListener(this);

        wishlistButton = (Button) findViewById(R.id.wishlist_button);
        wishlistButton.setOnClickListener(this);

        viewwishlistButton = (Button) findViewById(R.id.home_view_wish_list);
        viewwishlistButton.setOnClickListener(this);

        blue_button = (ImageButton) findViewById(R.id.blue_button);
        //notificaiton = (TextView) findViewById(R.id.notifiaction);

        new AsyncTask<Void, Void, Void>() {

            protected Void doInBackground(Void... params) {

                String myUser = CurrentUser.getCurrentUser().getUsername();
                try {

                    Wishlist myWishList = DatabaseInterfacer.getWishlist(myUser);
                    ArrayList<Item> itemArray = myWishList.getWishlist();

                    ItemReport[] reportedSales = DatabaseInterfacer.retrieveItemReports();
                    for (ItemReport obj : reportedSales) {
                        for (int i = 0; i < itemArray.size(); i++) {
                            if (obj.getProductName().equals(itemArray.get(i).getName()) && obj.getPrice() <= itemArray.get(i).getPrice()) {
                                if (obj.getRead() == 0) {
                                    count++;
                                }

                            }

                        }
                    }

                    if (count > 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                blue_button.setImageResource(R.drawable.notification_icon);

                            }
                        });
                    }

                } catch (DatabaseErrorException e) {

                }
                return null;
            }

        }.execute();
        //notificaiton.setText(count + " Notification");


    }

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.homepage_logout_button) {
            Intent intention = new Intent(this, LoginPage.class);
            finish();
            startActivity(intention);
        } else if (v.getId() == R.id.homepage_view_friends_button) {
            Intent intention = new Intent(this, ViewFriends.class);
            startActivity(intention);
        } else if (v.getId() == R.id.wishlist_button) {
            Intent intention = new Intent(this, AddWishList.class);
            startActivity(intention);
        } else if (v.getId() == R.id.home_view_wish_list) {
            Intent intention = new Intent(this, ViewWishList.class);
            startActivity(intention);
        }

    }


    /**
     * Called when the user clicks the Add Friend button
     *
     * @param view
     */
    public void addFriend(View view) {
        Intent intent = new Intent(this, AddFriendPage.class);
        startActivity(intent);
    }

    public void myProfile(View view) {
        Intent intent = new Intent(this, MyProfilePage.class);
        startActivity(intent);
    }

    public void myReports(View view) {
        Intent intent = new Intent(this, ReportedSales.class);
        startActivity(intent);
    }

    public void salesForMe(View view) {
        Intent intent = new Intent(this, ViewSales.class);
        startActivity(intent);
    }
}
