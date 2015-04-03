package yesmen.cs2340.shoppingwithfriends;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;

/**
 * AddToWishListPageTest checks the add to Wishlist feature.
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
     * Test case of adding an item to the wishlist
     * @throws Throwable
     */
    public void testAddWishlist() throws Throwable {
        DatabaseInterfacer.login("sunny", "sunny");
        activity.runOnUiThread(new Runnable() {
            public void run() {
                enteredItem.setText("ps4");
                enteredPrice.setText("200");
            }
        });
        TouchUtils.clickView(this,submitButton);
        assertEquals("ps4 successfully added to wishlist", activity.response);
    }
    /**
     * Test case of adding an item with no name
     * @throws Throwable
     */
    public void testNoItemNameWishlist() throws Throwable {
        DatabaseInterfacer.login("sunny", "sunny");
        activity.runOnUiThread(new Runnable() {
            public void run() {
                enteredItem.setText("");
                enteredPrice.setText("7");
            }
        });
        TouchUtils.clickView(this,submitButton);
        assertEquals(" successfully added to wishlist", activity.response);
    }
    /**
     * Test case of adding an item with no name
     * @throws Throwable
     */
    public void testNoItemPriceWishlist() throws Throwable {
        boolean error = false;
        try {
            DatabaseInterfacer.login("sunny", "sunny");
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    enteredItem.setText("");
                    enteredPrice.setText("");
                }
            });
            TouchUtils.clickView(this, submitButton);
        } catch(NumberFormatException e) {
            error = true;
        }
        assertTrue(error);
    }
}