package yesmen.cs2340.shoppingwithfriends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
/**
 * Class Homepage extends ActionBarActivity and implements View.OnClickListener,
 * is all the code that pertains to the homepage in android.
 *
 * @author Luka Antolic-Soban, Resse Aitken, Ratchapong Tangkijvorakul, Matty Attokaren, Sunny Patel
 * @version 1.6
 */
public class Homepage extends Activity implements View.OnClickListener {

    private Button logoutButton, viewButton, wishlistButton;

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
}
