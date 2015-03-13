package yesmen.cs2340.shoppingwithfriends;

/**
 * Created by Reese on 3/12/2015.
 * Item Report object, holds the price of the item, then umber of products left,
 * and the ItemReport's rating
 */
public class ItemReport {

    int reportId;
    String productName;
    double price;
    int productsLeft;

    public ItemReport (String productName, double price, int productsLeft) {
        this.productName = productName;
        this.price = price;
        this. productsLeft = productsLeft;
    }

    public ItemReport(int reportId) {
        ItemReport copy = DatabaseInterfacer.getItemReport(reportId);
        this.productName = copy.productName;
        this.price = copy.price;
        this.productsLeft = copy.productsLeft;
    }

}
