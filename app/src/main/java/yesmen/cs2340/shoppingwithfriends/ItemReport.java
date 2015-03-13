package yesmen.cs2340.shoppingwithfriends;

/**
 * Created by Reese on 3/12/2015.
 * Item Report object, holds the price of the item, then umber of products left,
 * and the ItemReport's rating
 *
 * ********MUST IMPLEMENT THIS LATER
 *
 */
public class ItemReport {

    int reportId;
    String productName;
    String location;
    double price;
    int quantity;

    public ItemReport (String productName, String location, double price, int quantity) {
        this.productName = productName;
        this.location = location;
        this.price = price;
        this.quantity = quantity;
    }

    public ItemReport(int reportId) {
        ItemReport copy = DatabaseInterfacer.getItemReport(reportId);
        this.productName = copy.productName;
        this.location = copy.location;
        this.price = copy.price;
        this.quantity = copy.quantity;
    }

    public String getProductName() {
        return productName;
    }

    public String getLocation() {
        return location;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
