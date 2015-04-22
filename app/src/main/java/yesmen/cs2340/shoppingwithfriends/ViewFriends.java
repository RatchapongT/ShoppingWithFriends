package yesmen.cs2340.shoppingwithfriends;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class ViewFriends extends ActionBarActivity and implements View.OnClickListener,
 * is all the code that pertains to the view friends page in android.
 */
public class ViewFriends extends FragmentActivity implements View.OnClickListener {

    private ProgressDialog progressDialog;
    private ListView listView;
    private final ArrayList<String> values = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friends); // Set layout according to login.xml
        Button cancelButton = (Button) findViewById(R.id.view_friend_cancel_button);
        cancelButton.setOnClickListener(this);


        new ViewFriendsAttempt().execute();


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.view_friend_cancel_button) {
            Intent intention = new Intent(this, Homepage.class);
            startActivity(intention);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);

        }
    }

    /**
     * Class ViewFriendsAttempt extends AsyncTask, checks the attempt to view friends.
     */
    private class ViewFriendsAttempt extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ViewFriends.this);
            progressDialog.setMessage("Refreshing Friends...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String myUser = CurrentUser.getCurrentUser().getUsername();
            String[] list = DatabaseInterfacer.viewFriends(myUser);
            if (list.length > 1) {
                Collections.addAll(values, list);
            }
            return list[0];
        }

        @Override
        protected void onPostExecute(String file_url) {
            listView = (ListView) findViewById(R.id.view_friend_list);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(ViewFriends.this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, values);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // ListView Clicked item value
                    String itemValue = (String) listView.getItemAtPosition(position);

                    // Show Alert
                    Toast.makeText(getApplicationContext(),
                            "Position :" + position + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                            .show();

                    Intent intent = new Intent(getBaseContext(), DetailedFriend.class);
                    intent.putExtra("Username", itemValue);
                    startActivity(intent);

                }
            });

            // dismiss the dialog once product deleted
            progressDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(ViewFriends.this, file_url, Toast.LENGTH_LONG).show();
            }
        }


    }

}
