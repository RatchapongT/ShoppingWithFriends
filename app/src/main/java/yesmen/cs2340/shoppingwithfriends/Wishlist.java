package yesmen.cs2340.shoppingwithfriends;
import java.util.ArrayList;

/**
 * Created by Reese on 3/5/2015.
 */
public class Wishlist {
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
