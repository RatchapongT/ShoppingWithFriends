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
            String myUser = CurrentUser.getCurrentUser().getUserName();

            return DatabaseInterfacer.addFriend(friendUser, myUser);
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
