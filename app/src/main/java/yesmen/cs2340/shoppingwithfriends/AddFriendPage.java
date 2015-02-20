package yesmen.cs2340.shoppingwithfriends;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
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


public class AddFriendPage extends ActionBarActivity implements View.OnClickListener {

    private EditText enteredFriend;
    private Button submitButton;
    private Button cancelButton;

    private ProgressDialog progressDialog;

    JSONParser jsonParser = new JSONParser();

    //private static final String SERVER_URL = "http://10.0.2.2:80/yesmen/add_friend.php";
    private static final String SERVER_URL = "http://73.207.216.173:80/yesmen/add_friend.php";

    //JSON element ids from response of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        enteredFriend = (EditText) findViewById(R.id.friendUser);
        submitButton = (Button) findViewById(R.id.submitButton);
        cancelButton = (Button) findViewById(R.id.cancelbutton_friend);

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
        if (v.getId() == R.id.submitButton) {
            new AddFriendAttempt().execute();
        } else if (v.getId() == R.id.cancelbutton_friend) {
            Intent intention = new Intent(this, Homepage.class);
            finish();
            startActivity(intention);
        }
    }

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



            friendUser = friendUser.toLowerCase();
            myUser = myUser.toLowerCase();


            try {
                // Building Parameters
                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("FriendID", friendUser));
                parameters.add(new BasicNameValuePair("UserID", myUser));


                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(SERVER_URL, "POST", parameters);

                // check your log for json response

                // json success tag
                addFriendSuccess = json.getInt(TAG_SUCCESS);
                if (addFriendSuccess == 1) {
                    // Write to database
                    return json.getString(TAG_MESSAGE);
                } else {
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            progressDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(AddFriendPage.this, file_url, Toast.LENGTH_LONG).show();
            }
        }


    }
}
