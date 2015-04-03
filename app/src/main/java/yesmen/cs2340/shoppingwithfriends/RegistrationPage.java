package yesmen.cs2340.shoppingwithfriends;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Class RegistrationPage extends ActionBarActivity and implements View.OnClickListener,
 * is all the code that pertains to the registration page in android.
 *
 * @author      Luka Antolic-Soban, Resse Aitken, Ratchapong Tangkijvorakul, Matty Attokaren, Sunny Patel
 * @version     1.6
 */

public class RegistrationPage extends Activity implements OnClickListener {

    private EditText enteredUsername, enteredPassword, enteredConfirmed;
    private ProgressDialog progressDialog;
    public String response;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

        enteredUsername = (EditText)findViewById(R.id.register_username_input);
        enteredPassword = (EditText)findViewById(R.id.register_password_input);
        enteredConfirmed = (EditText)findViewById(R.id.register_confirm_password_input);


        Button registerButton = (Button) findViewById(R.id.register_execute_button);
        Button cancelbutton = (Button) findViewById(R.id.register_cancel_button);
        registerButton.setOnClickListener(this);
        cancelbutton.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
        if (v.getId() == R.id.register_execute_button) {
            new CreateUser().execute();
        } else if (v.getId() == R.id.register_cancel_button) {
            Intent intention = new Intent(this, LoginPage.class);
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
    /**
     * Class LoginAttempt extends AsyncTask, checks the user being created.
     *
     */
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
            String username = enteredUsername.getText().toString();
            String password = enteredPassword.getText().toString();
            String confirm = enteredConfirmed.getText().toString();

            if (!password.equals(confirm)) {
                return "Password and confirmation do not match!";
            }

            String ret = DatabaseInterfacer.register(username, password);
            if (ret.equals("User Created!")) {
                Log.d("User Created!", ret);
                finish();
            } else {
                Log.d("User Registration Fail!", ret);
            }
            return ret;

		}

        @Override
        protected void onPostExecute(String file_url) {
            response = file_url;
            progressDialog.dismiss();
            if (file_url != null){
            	Toast.makeText(RegistrationPage.this, file_url, Toast.LENGTH_LONG).show();
            }
 
        }
		
	}
		 

}
