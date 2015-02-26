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
public class ViewFriends extends FragmentActivity implements View.OnClickListener{

    private ProgressDialog progressDialog;
    JSONParser jsonParser = new JSONParser();
    private Button cancelbutton;
    ListView listView ;
    private ArrayList<String> values = new ArrayList<String>();

    //private static final String SERVER_URL = "http://10.0.2.2:80/yesmen/view_friends.php";
    private static final String SERVER_URL = "http://wtfizlinux.com/yesmen/view_friends.php";

    //JSON element ids from response of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friends); // Set layout according to login.xml
        cancelbutton = (Button)findViewById(R.id.view_friend_cancel_button);
        cancelbutton.setOnClickListener(this);


        new ViewFriendsAttempt().execute();


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.view_friend_cancel_button) {
            Intent intention = new Intent(this, Homepage.class);
            startActivity(intention);
        }
    }
    /**
     * Class ViewFriendsAttempt extends AsyncTask, checks the attempt to view friends.
     *
     */
    class ViewFriendsAttempt extends AsyncTask<String, String, String> {

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

            int viewFriendSuccess;
            String myUser = CurrentUser.getCurrentUser().getUsername();
            myUser = myUser.toLowerCase();

            try {
                // Building Parameters
                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("UserID", myUser));


                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(SERVER_URL, "POST", parameters);

                // check your log for json response
                JSONArray jArray = json.getJSONArray(TAG_MESSAGE);




                // json success tag
                viewFriendSuccess = json.getInt(TAG_SUCCESS);
                if (viewFriendSuccess == 1) {
                    for(int i = 0; i < jArray.length(); ++i) {
                        values.add(jArray.getString(i));

                    }

                    return "Here are list of friends.";
                } else {
                    return "No Friends.";

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }
        /**
         * When a post is executed
         *
         * @param file_url
         */
        protected void onPostExecute(String file_url) {
            listView = (ListView) findViewById(R.id.view_friend_list);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewFriends.this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, values);
            listView.setAdapter(adapter);
            // dismiss the dialog once product deleted
            progressDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(ViewFriends.this, file_url, Toast.LENGTH_LONG).show();
            }
        }



    }

}
