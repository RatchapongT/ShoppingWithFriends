/**
 * @author      Luka Antolic-Soban, Resse Aitken, Ratchapong Tangkijvorakul, Matty Attokaren, Sunny Patel
 * @version     1.4
 */
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

public class LoginPage extends ActionBarActivity implements View.OnClickListener {

    private EditText enteredUsername, enteredPassword;
    private Button loginButton, registerButton;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login); // Set layout according to login.xml


        enteredUsername = (EditText) findViewById(R.id.usernameField);
        enteredPassword = (EditText) findViewById(R.id.passwordField);

        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registrationButton);

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
        if (v.getId() == R.id.loginButton) {
            new LoginAttempt().execute();
        } else if (v.getId() == R.id.registrationButton) {
            Intent intention = new Intent(this, RegistrationPage.class);
            startActivity(intention);
        }
    }

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

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            progressDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(LoginPage.this, file_url, Toast.LENGTH_LONG).show();
            }
        }

    }

}
