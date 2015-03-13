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

    String originator;
    String productName;
    String location;
    double price;
    int quantity;
    int read;

    /**
     * Creates an ItemReport object
     * @param originator origin user of the ItemReport
     * @param productName Name of the Item
     * @param location Location of the Item
     * @param price Price of the Item
     * @param quantity Quantity of the Item
     */
    public ItemReport (String originator, String productName, String location, double price, int quantity, int read) {
        this.originator = originator;
        this.productName = productName;
        this.location = location;
        this.price = price;
        this.quantity = quantity;
        this.read = read;
    }

    /*
    public ItemReport(int reportId) {
        ItemReport copy = DatabaseInterfacer.getItemReport(reportId);
        this.productName = copy.productName;
        this.location = copy.location;
        this.price = copy.price;
        this.quantity = copy.quantity;
    }
    */

    public String getOriginator() {
        return originator;
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
    public int getRead() {
        return read;
    }
}
