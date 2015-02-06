package yesmen.cs2340.shoppingwithfriends;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginPage extends ActionBarActivity {

    EditText username, password;
    TextView loginFail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.txtusername);
        password = (EditText) findViewById(R.id.txtpassword);

        final Button loginbutton = (Button) findViewById(R.id.buttonlogin);
        final Button registerbutton = (Button) findViewById(R.id.buttonregister);

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!username.getText().toString().trim().equals("") && !password.getText().toString().trim().equals("")) {
                    loginbutton.setEnabled(true);
                } else {
                    loginbutton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!username.getText().toString().trim().equals("") && !password.getText().toString().trim().equals("")) {
                    loginbutton.setEnabled(true);
                } else {
                    loginbutton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    //user login method to allow user to go to main menu of application
    //***for now made credentials "admin" "admin" for M3
    public void login(View view) {
        if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
        } else {
            loginFail = (TextView) findViewById(R.id.loginFail);
            loginFail.setVisibility(View.VISIBLE);
        }
    }

}
