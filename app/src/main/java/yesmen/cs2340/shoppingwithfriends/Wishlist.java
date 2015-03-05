package yesmen.cs2340.shoppingwithfriends;
import java.util.ArrayList;

/**
 * Created by Reese on 3/5/2015.
 */
public class Wishlist {
    ArrayList<Item> items;
    public Wishlist() {
        items = new ArrayList<>();
    }

    public String addToWishlist(Item item) {
        if (!items.contains(item)) {
            items.add(item);
            return "Item added to Wishlist";
        } else {
            return "Item already in Wishlist";
        }
    }

    public ArrayList<Item> getWishlist() {
        return items;
    }
}
