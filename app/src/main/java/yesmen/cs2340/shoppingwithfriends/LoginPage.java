package yesmen.cs2340.shoppingwithfriends;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
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

public class LoginPage extends ActionBarActivity implements View.OnClickListener {

    private EditText enteredUsername, enteredPassword;
    private Button loginButton, registerButton;

    private ProgressDialog progressDialog;

    JSONParser jsonParser = new JSONParser();

    //private static final String LOGIN_URL = "http://10.0.2.2:80/yesmen/login.php";
    private static final String LOGIN_URL = "http://71.236.14.188:80/yesmen/login.php";

    //JSON element ids from response of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login); // Set layout according to login.xml

        enteredUsername = null;
        enteredPassword = null;

        enteredUsername = (EditText) findViewById(R.id.usernameField);
        enteredPassword = (EditText) findViewById(R.id.passwordField);

        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registrationButton);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
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
            int loginSuccess = 0;
            String username = enteredUsername.getText().toString();
            String password = enteredPassword.getText().toString();
            try {
                // Building Parameters
                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("username", username));
                parameters.add(new BasicNameValuePair("password", password));

                Log.d("Request!", "Starting the validation process");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", parameters);

                // check your log for json response
                Log.d("Login attempt", json.toString());

                // json success tag
                loginSuccess = json.getInt(TAG_SUCCESS);
                if (loginSuccess == 1) {
                    loginSuccess = 0;
                    Log.d("Login Successful!", json.toString());
                    Intent intention = new Intent(LoginPage.this, Homepage.class);
                    finish();
                    startActivity(intention);
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Login Failure!", json.toString());
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
                Toast.makeText(LoginPage.this, file_url, Toast.LENGTH_LONG).show();
            }
        }

    }

}
