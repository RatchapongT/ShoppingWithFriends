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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

/**
 * Class ViewFriends extends ActionBarActivity and implements View.OnClickListener,
 * is all the code that pertains to the view friends page in android.
 */
public class ViewWishList extends FragmentActivity implements View.OnClickListener {

    private ProgressDialog progressDialog;
    private Button cancelbutton;
    ListView listView;
    private ArrayList<String> values = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wishlist); // Set layout according to login.xml
        cancelbutton = (Button) findViewById(R.id.view_wish_list_cancel_button);
        cancelbutton.setOnClickListener(this);


        new ViewFriendsAttempt().execute();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.view_wish_list_cancel_button) {
            Intent intention = new Intent(this, Homepage.class);
            startActivity(intention);
        }
    }

    /**
     * Class ViewFriendsAttempt extends AsyncTask, checks the attempt to view friends.
     */
    class ViewFriendsAttempt extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ViewWishList.this);
            progressDialog.setMessage("Refreshing Friends...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String myUser = CurrentUser.getCurrentUser().getUsername();
            try {
                Wishlist myWishList = DatabaseInterfacer.getWishlist(myUser);
                ArrayList<Item> itemArray = myWishList.getWishlist();
                for(Item obj : itemArray) {
                    values.add(obj.getName() + " " + obj.getPrice());
                }


            } catch (DatabaseErrorException e) {
                return e.getMessage();
            }
        return "Success!";
        }

        /**
         * When a post is executed
         *
         * @param file_url
         */
        protected void onPostExecute(String file_url) {
            listView = (ListView) findViewById(R.id.view_wish_list);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewWishList.this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, values);
            listView.setAdapter(adapter);
            /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // ListView Clicked item index
                    int itemPosition = position;

                    // ListView Clicked item value
                    String itemValue = (String) listView.getItemAtPosition(position);

                    // Show Alert
                    Toast.makeText(getApplicationContext(),
                            "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                            .show();

                    Intent intent = new Intent(getBaseContext(), DetailedFriend.class);
                    intent.putExtra("Username", itemValue);
                    startActivity(intent);

                }
            });*/

            // dismiss the dialog once product deleted
            progressDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(ViewWishList.this, file_url, Toast.LENGTH_LONG).show();
            }
        }


    }

}
