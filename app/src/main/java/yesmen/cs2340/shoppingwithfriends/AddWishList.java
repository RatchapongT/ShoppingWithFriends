package yesmen.cs2340.shoppingwithfriends;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This class allows one to add new items to the wishlist
 */
public class AddWishList extends Activity implements View.OnClickListener {

    private EditText enteredItem, enteredPrice;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wishlist);
        Button submitButton = (Button) findViewById(R.id.wishlist_submit_button);
        submitButton.setOnClickListener(this);

        Button cancelButton = (Button) findViewById(R.id.wishlist_cancel_button);
        cancelButton.setOnClickListener(this);

        enteredItem = (EditText) findViewById(R.id.my_item_input);
        enteredPrice = (EditText) findViewById(R.id.my_price_input);
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.wishlist_submit_button) {
            new AddWishlistAttempt().execute();
        } else if (v.getId() == R.id.wishlist_cancel_button) {
            Intent intention = new Intent(this, Homepage.class);
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


    class AddWishlistAttempt extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AddWishList.this);
            progressDialog.setMessage("Submitting...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String newItem = enteredItem.getText().toString();
            String newPrice = enteredPrice.getText().toString();
            int threshold = Integer.parseInt(newPrice);
            return DatabaseInterfacer.addToWishlist(newItem, threshold);
        }

        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            progressDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(AddWishList.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
    }

}
