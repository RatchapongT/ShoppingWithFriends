package yesmen.cs2340.shoppingwithfriends;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Class Homepage extends ActionBarActivity and implements View.OnClickListener,
 * is all the code that pertains to the homepage in android.
 *
 * @author Luka Antolic-Soban, Resse Aitken, Ratchapong Tangkijvorakul, Matty Attokaren, Sunny Patel
 * @version 1.6
 */
public class MyProfilePage extends Activity implements View.OnClickListener {

    private ProgressDialog progressDialog;
    private EditText enteredName, enteredBiography, enteredLocation, enteredEmail, enteredPhoneNumber;
    private User userObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        Button cancelButton = (Button) findViewById(R.id.my_profile_cancel_button);
        cancelButton.setOnClickListener(this);

        Button submitButton = (Button) findViewById(R.id.my_profile_execute_button);
        submitButton.setOnClickListener(this);

        enteredName = (EditText) findViewById(R.id.my_profile_name_input);
        enteredBiography = (EditText) findViewById(R.id.my_profile_biography_input);
        enteredLocation = (EditText) findViewById(R.id.my_profile_location_input);
        enteredEmail = (EditText) findViewById(R.id.my_profile_email_input);
        enteredPhoneNumber = (EditText) findViewById(R.id.my_profile_phone_number_input);

        new RetrieveProfileAttempt().execute();
    }

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.my_profile_cancel_button) {
            Intent intention = new Intent(this, Homepage.class);
            finish();
            startActivity(intention);
        } else if (v.getId() == R.id.my_profile_execute_button) {
            new UpdateProfileAttempt().execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    class UpdateProfileAttempt extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MyProfilePage.this);
            progressDialog.setMessage("Updating...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String myUser = CurrentUser.getCurrentUser().getUsername();
            String name = enteredName.getText().toString();
            String biography = enteredBiography.getText().toString();
            String location = enteredLocation.getText().toString();
            String email = enteredEmail.getText().toString();
            String phoneNumber = enteredPhoneNumber.getText().toString();

            String ret = DatabaseInterfacer.updateProfile(myUser, name, biography, location, email,phoneNumber);

            if (ret.equals("Success!")) {
                return ret;
            } else {
                return ret;
            }
        }


        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            progressDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(MyProfilePage.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
    }


    class RetrieveProfileAttempt extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MyProfilePage.this);
            progressDialog.setMessage("Retrieving Profile...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String myUser = CurrentUser.getCurrentUser().getUsername();

            userObject = DatabaseInterfacer.retrieveProfile(myUser);
            if (userObject != null) {
                return "Success";
            } else {
                return "Fail";
            }
        }

        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            progressDialog.dismiss();
            if (file_url != null) {
                enteredName.setText(userObject.getName());

                enteredBiography.setText(userObject.getBiography());
                enteredLocation.setText(userObject.getLocation());
                enteredEmail.setText(userObject.getEmail());
                enteredPhoneNumber.setText(userObject.getPhoneNumber());
                Toast.makeText(MyProfilePage.this, file_url, Toast.LENGTH_LONG).show();
            }
        }


    }
}
