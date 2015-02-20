/**
 * @author      Luka Antolic-Soban, Resse Aitken, Ratchapong Tangkijvorakul, Matty Attokaren, Sunny Patel
 * @version     1.4
 */
package yesmen.cs2340.shoppingwithfriends;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationPage extends ActionBarActivity implements OnClickListener {

    private EditText enteredUsername, enteredPassword, enteredConfirmed;
    private Button registerButton, cancelbutton;

    private ProgressDialog progressDialog;
	
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
            new CreateUser().execute();
        } else if (v.getId() == R.id.cancelbutton) {
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
                return "Password Mismatch!";
            }
            String ret = DatabaseInterfacer.register(username, password);
            Log.d("WTF", ret);
            if (ret.equals("Username Successfully Added!")) {
                finish();
                return ret;
            } else {
                return ret;
            }
		}

        protected void onPostExecute(String file_url) {
            progressDialog.dismiss();
            if (file_url != null){
            	Toast.makeText(RegistrationPage.this, file_url, Toast.LENGTH_LONG).show();
            }
 
        }
		
	}
		 

}
