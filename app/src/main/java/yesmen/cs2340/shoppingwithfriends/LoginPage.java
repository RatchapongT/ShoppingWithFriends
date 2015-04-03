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
 * Class LoginPage extends ActionBarActivity and implements View.OnClickListener,
 * is all the code that pertains to the login page in android.
 *
 * @author Luka Antolic-Soban, Resse Aitken, Ratchapong Tangkijvorakul, Matty Attokaren, Sunny Patel
 * @version 1.6
 */

public class LoginPage extends Activity implements View.OnClickListener {

    private EditText enteredUsername, enteredPassword;

    private ProgressDialog progressDialog;
    public String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login); // Set layout according to login.xml


        enteredUsername = (EditText) findViewById(R.id.login_username_input);
        enteredPassword = (EditText) findViewById(R.id.login_password_input);

        Button loginButton = (Button) findViewById(R.id.login_execute_button);
        Button registerButton = (Button) findViewById(R.id.login_register_button);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

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
        if (v.getId() == R.id.login_execute_button) {
            new LoginAttempt().execute();
        } else if (v.getId() == R.id.login_register_button) {
            Intent intention = new Intent(this, RegistrationPage.class);
            startActivity(intention);
        }
    }
    /**
     * Class LoginAttempt extends AsyncTask, checks the Login Attempt that is made by the user
     *
     */
    class LoginAttempt extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginPage.this);
            progressDialog.setMessage("Validating login...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String username = enteredUsername.getText().toString();
            String password = enteredPassword.getText().toString();
            String ret = DatabaseInterfacer.login(username, password);
            if (ret.equals("Login successful!")) {
                Intent intention = new Intent(LoginPage.this, Homepage.class);
                finish();
                startActivity(intention);
                return ret;
            } else {
                return ret;
            }
        }

        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            response = file_url;
            progressDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(LoginPage.this, file_url, Toast.LENGTH_LONG).show();
            }
        }

    }

}
