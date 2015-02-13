package yesmen.cs2340.shoppingwithfriends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class Homepage extends ActionBarActivity implements View.OnClickListener {

    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        logoutButton = (Button) findViewById(R.id.logoutbutton);
        logoutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intention = new Intent(this, LoginPage.class);
        finish();
        startActivity(intention);
    }
}
