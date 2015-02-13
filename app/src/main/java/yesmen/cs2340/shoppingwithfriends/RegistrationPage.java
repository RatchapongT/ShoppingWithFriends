package yesmen.cs2340.shoppingwithfriends;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegistrationPage extends ActionBarActivity implements OnClickListener {

    private EditText enteredUsername, enteredPassword, enteredConfirmed;
    private Button registerButton, cancelbutton;

    private ProgressDialog progressDialog;

    JSONParser jsonParser = new JSONParser();

    //private static final String LOGIN_URL = "http://10.0.2.2:80/yesmen/register.php";
    private static final String LOGIN_URL = "http://71.236.14.188:80/yesmen/register.php";

    //JSON element ids from response of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

        enteredUsername = (EditText)findViewById(R.id.usernameField);
        enteredPassword = (EditText)findViewById(R.id.passwordField);
        enteredConfirmed = (EditText)findViewById(R.id.confirmField);


        registerButton = (Button)findViewById(R.id.registrationButton);
        cancelbutton = (Button)findViewById(R.id.cancelbutton);
        registerButton.setOnClickListener(this);
        cancelbutton.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
        if (v.getId() == R.id.registrationButton) {
            new CreateUser().execute();;
        } else if (v.getId() == R.id.cancelbutton) {
            Intent intention = new Intent(this, LoginPage.class);
            finish();
            startActivity(intention);
        }
	}
	
	class CreateUser extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RegistrationPage.this);
            progressDialog.setMessage("Creating User...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }
		
		@Override
		protected String doInBackground(String... args) {
            int registrationSuccess;
            String username = enteredUsername.getText().toString();
            String password = enteredPassword.getText().toString();
            String confirm = enteredConfirmed.getText().toString();
            try {
                if (!password.equals(confirm)) {
                    String mismatch = new String("Password Mismatch!");
                    return mismatch;
                }
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));
 
                Log.d("request!", "starting");

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

                Log.d("Login attempt", json.toString());

                registrationSuccess = json.getInt(TAG_SUCCESS);
                if (registrationSuccess == 1) {
                	Log.d("User Created!", json.toString());              	
                	finish();
                	return json.getString(TAG_MESSAGE);
                }else{
                	Log.d("User Registration Failure!", json.getString(TAG_MESSAGE));
                	return json.getString(TAG_MESSAGE);
                	
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
			
		}

        protected void onPostExecute(String file_url) {
            progressDialog.dismiss();
            if (file_url != null){
            	Toast.makeText(RegistrationPage.this, file_url, Toast.LENGTH_LONG).show();
            }
 
        }
		
	}
		 

}
