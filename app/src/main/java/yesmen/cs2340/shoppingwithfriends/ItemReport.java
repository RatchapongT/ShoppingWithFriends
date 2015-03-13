package yesmen.cs2340.shoppingwithfriends;

/**
 * Created by Reese on 3/12/2015.
 * Item Report object, holds the price of the item, then umber of products left,
 * and the ItemReport's rating
 */
public class ItemReport {

    double price;
    int productsLeft;
    int rating;

    public ItemReport (double price, int productsLeft) {
        this.price = price;
        this. productsLeft = productsLeft;
        rating = 0;
    }

}
