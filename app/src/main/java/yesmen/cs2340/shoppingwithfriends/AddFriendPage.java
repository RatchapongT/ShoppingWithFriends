package yesmen.cs2340.shoppingwithfriends;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class AddFriendPage extends ActivityBarActivity and implements View.OnClickListener, is
 * all the code that pertains to adding a friend in android. It checks if you are already a
 * friend, and lets you add a new friend.
 *
 * @author Luka Antolic-Soban, Resse Aitken, Ratchapong Tangkijvorakul, Matty Attokaren, Sunny Patel
 * @version 1.2
 */
public class AddFriendPage extends ActionBarActivity implements View.OnClickListener {

    private EditText enteredFriend;
    private Button submitButton;
    private Button cancelButton;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        enteredFriend = (EditText) findViewById(R.id.add_friend_username_input);
        submitButton = (Button) findViewById(R.id.add_friend_execute_button);
        cancelButton = (Button) findViewById(R.id.add_friend_cancel_button);

        submitButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_friend_execute_button) {
            new AddFriendAttempt().execute();
        } else if (v.getId() == R.id.add_friend_cancel_button) {
            Intent intention = new Intent(this, Homepage.class);
            finish();
            startActivity(intention);
        }
    }
    /**
     * Class AddFriendAttempt extends AsyncTask, checks if you are already a
     * friend, and all the code when attempting to add a friend.
     *
     */
    class AddFriendAttempt extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AddFriendPage.this);
            progressDialog.setMessage("Checking Friend...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            int addFriendSuccess = 0;
            String friendUser = enteredFriend.getText().toString();
            String myUser = CurrentUser.getCurrentUser().getUsername();

            return DatabaseInterfacer.addFriend(friendUser, myUser);
        }

        /**
         * When a post is executed
         *
         * @param file_url
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            progressDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(AddFriendPage.this, file_url, Toast.LENGTH_LONG).show();
            }
        }


    }
}
