package yesmen.cs2340.shoppingwithfriends;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Class Homepage extends ActionBarActivity and implements View.OnClickListener,
 * is all the code that pertains to the homepage in android.
 *
 * @author Luka Antolic-Soban, Resse Aitken, Ratchapong Tangkijvorakul, Matty Attokaren, Sunny Patel
 * @version 1.6
 */
@SuppressWarnings("ALL")
public class Homepage extends Activity implements View.OnClickListener {

    private ImageButton blue_button;
    private TextView notification;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        Button logoutButton = (Button) findViewById(R.id.homepage_logout_button);
        logoutButton.setOnClickListener(this);

        ImageButton viewButton = (ImageButton) findViewById(R.id.homepage_view_friends_button);
        viewButton.setOnClickListener(this);

        ImageButton wishlistButton = (ImageButton) findViewById(R.id.wishlist_button);
        wishlistButton.setOnClickListener(this);

        ImageButton viewwishlistButton = (ImageButton) findViewById(R.id.home_view_wish_list);
        viewwishlistButton.setOnClickListener(this);

        blue_button = (ImageButton) findViewById(R.id.sales_for_me_button);
        notification = (TextView) findViewById(R.id.notifiaction);

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
                                notification.setText(count + " Notification");
                            }
                        });
                    }

                } catch (DatabaseErrorException e) {
                    Log.d("Database Exception", e.getMessage());
                }
                return null;
            }

   

        }.execute();
        //


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
     * @param view the current view
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
