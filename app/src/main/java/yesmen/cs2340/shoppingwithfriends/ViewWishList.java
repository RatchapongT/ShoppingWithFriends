package yesmen.cs2340.shoppingwithfriends;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Class ViewFriends extends ActionBarActivity and implements View.OnClickListener,
 * is all the code that pertains to the view friends page in android.
 */
@SuppressWarnings("ALL")
public class ViewWishList extends FragmentActivity implements View.OnClickListener {

    private ProgressDialog progressDialog;
    private ArrayList<String> values = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wishlist); // Set layout according to login.xml
        Button cancelButton = (Button) findViewById(R.id.view_wish_list_cancel_button);
        cancelButton.setOnClickListener(this);


        new ViewFriendsAttempt().execute();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.view_wish_list_cancel_button) {
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
                    values.add(obj.getName() + " $" + obj.getPrice());
                }


            } catch (DatabaseErrorException e) {
                return e.getMessage();
            }
        return "Success!";
        }

        @Override
        protected void onPostExecute(String file_url) {
            ListView listView = (ListView) findViewById(R.id.view_wish_list);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(ViewWishList.this,
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
