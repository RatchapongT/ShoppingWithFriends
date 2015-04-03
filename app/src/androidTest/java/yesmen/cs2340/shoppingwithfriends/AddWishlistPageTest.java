package yesmen.cs2340.shoppingwithfriends;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;

/**
 * AddToWishListPageTest checks the add to Wish list feature.
 */
public class AddWishlistPageTest extends ActivityInstrumentationTestCase2<AddWishList> {

    public AddWishList activity;
    public Button submitButton, cancelButton;
    private EditText enteredItem, enteredPrice;

    @SuppressWarnings("deprecation")
    public AddWishlistPageTest() {
        super("yesmen.cs2340.shoppingwithfriends", AddWishList.class);
    }

    /**
     * Get Activity method
     * @throws Exception
    */
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        enteredItem = (EditText) activity.findViewById(R.id.my_item_input);
        enteredPrice = (EditText) activity.findViewById(R.id.my_price_input);
        submitButton = (Button) activity.findViewById(R.id.wishlist_submit_button);
        cancelButton = (Button) activity.findViewById(R.id.wishlist_cancel_button);
    }
    /**
     * Test case for item name
     * @throws Throwable
     */
    public void testItemName() throws Throwable {
            DatabaseInterfacer.login("sunny", "sunny");
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    enteredItem.setText("shirt");
                    enteredPrice.setText("7");
                }
            });
        Thread.sleep(2000);
        assertEquals("shirt", enteredItem.getText().toString());
    }
    /**
     * Test case for price
     * @throws Throwable
     */
    public void testPriceValue() throws Throwable {
        DatabaseInterfacer.login("sunny", "sunny");
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                enteredItem.setText("shirt");
                enteredPrice.setText("7");
            }
        });
        Thread.sleep(2000);
        assertEquals("7", enteredPrice.getText().toString());
    }
    /**
     * Test case for adding an item to the wishlist
     * @throws Throwable
     */
    public void testAddWishlist() throws Throwable {
        DatabaseInterfacer.login("sunny", "sunny");
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                enteredItem.setText("ps4");
                enteredPrice.setText("200");
            }
        });
        Thread.sleep(2000);
        TouchUtils.clickView(this,submitButton);
        assertEquals("ps4 successfully added to wishlist", activity.response);
    }
    /**
     * Test case for adding an item with no name
     * @throws Throwable
     */
    public void testNoItemName() throws Throwable {
        DatabaseInterfacer.login("sunny", "sunny");
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                enteredItem.setText("");
                enteredPrice.setText("7");
            }
        });
        Thread.sleep(2000);
        TouchUtils.clickView(this,submitButton);
        assertEquals(" successfully added to wishlist", activity.response);
    }
}