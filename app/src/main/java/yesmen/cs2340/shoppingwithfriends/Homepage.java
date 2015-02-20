/**
 * @author      Luka Antolic-Soban, Resse Aitken, Ratchapong Tangkijvorakul, Matty Attokaren, Sunny Patel
 * @version     1.4
 */
package yesmen.cs2340.shoppingwithfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

//the main class for the Homepage Activity
public class Homepage extends ActionBarActivity implements View.OnClickListener {

    private Button logoutButton, viewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        logoutButton = (Button) findViewById(R.id.homepage_logout_button);
        logoutButton.setOnClickListener(this);

        viewButton = (Button) findViewById(R.id.homepage_view_friends_button);
        viewButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.homepage_logout_button) {
            Intent intention = new Intent(this, LoginPage.class);
            finish();
            startActivity(intention);
        } else if (v.getId() == R.id.homepage_view_friends_button) {
            Intent intention = new Intent(this, ViewFriends.class);
            finish();
            startActivity(intention);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /** Called when the user clicks the Add Friend button */
    public void addFriend(View view) {
        Intent intent = new Intent(this, AddFriendPage.class);
        finish();
        startActivity(intent);
    }
}
