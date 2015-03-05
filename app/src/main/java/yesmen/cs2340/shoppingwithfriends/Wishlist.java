package yesmen.cs2340.shoppingwithfriends;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Reese on 3/5/2015.
 */
public class Wishlist extends Activity implements View.OnClickListener {

        private Button cancelButton,submitButton;
        private EditText enteredItem, enteredPrice;
        private User userObject;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.wishlist);
            submitButton = (Button) findViewById(R.id.wishlist_submit_button);
            submitButton.setOnClickListener(this);

            cancelButton = (Button) findViewById(R.id.wishlist_cancel_button);
            cancelButton.setOnClickListener(this);

            enteredItem = (EditText) findViewById(R.id.my_item_input);
            enteredPrice = (EditText) findViewById(R.id.my_price_input);
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.wishlist_cancel_button) {
                Intent intention = new Intent(this, Homepage.class);
                finish();
                startActivity(intention);
            }
//            else if (v.getId() == R.id.wishlist_submit_button) {
//                new Wishlist().addToWishlist();
//            }
        }

    ArrayList<Item> items;

    /**
     * Creates an empty Wishlist object
     */
    public Wishlist() {
        items = new ArrayList<>();
    }

    /**
     * Adds an item to this wishlist
     * does not add duplicate items
     * @param item Item to add to wishlist
     * @return String for success or failure
     */
    public String addToWishlist(Item item) {
        if (!items.contains(item)) {
            items.add(item);
            return "Item added to Wishlist";
        } else {
            return "Item already in Wishlist";
        }
    }

    /**
     * Returns the backing Arraylist of items in the wishlist
     * @return Arraylist of Items
     */
    public ArrayList<Item> getWishlist() {
        return items;
    }
}
