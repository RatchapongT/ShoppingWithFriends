package yesmen.cs2340.shoppingwithfriends;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class DetailedFriend extends Activity implements View.OnClickListener{

    private ProgressDialog progressDialog;
    private User userObject;
    private TextView enteredLocation, enteredEmail, enteredPhoneNumber, enteredName;
    private Button backButton;
    private String requestedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_friend);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            requestedUser = extras.getString("Username");
        }

        backButton = (Button) findViewById(R.id.friend_back);
        backButton.setOnClickListener(this);
        enteredName = (TextView) findViewById(R.id.view_friend_list);
        enteredLocation = (TextView) findViewById(R.id.friend_location);
        enteredEmail = (TextView) findViewById(R.id.friend_email);
        enteredPhoneNumber = (TextView) findViewById(R.id.friend_phoneNum);


        new RetrieveFriendProfileAttempt().execute();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailed_friend, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.friend_back) {
            Intent intention = new Intent(this, ViewFriends.class);
            startActivity(intention);
        }
    }

    class RetrieveFriendProfileAttempt extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(DetailedFriend.this);
            progressDialog.setMessage("Retrieving Profile...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            userObject = DatabaseInterfacer.retrieveProfile(requestedUser);
            if (userObject != null) {
                return "Success";
            } else {
                return "Fail";
            }
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
                enteredName.setText(userObject.getName());
                enteredLocation.setText(userObject.getLocation());
                enteredEmail.setText(userObject.getEmail());
                enteredPhoneNumber.setText(userObject.getPhoneNumber());
                Toast.makeText(DetailedFriend.this, file_url, Toast.LENGTH_LONG).show();
            }
        }


    }
}
