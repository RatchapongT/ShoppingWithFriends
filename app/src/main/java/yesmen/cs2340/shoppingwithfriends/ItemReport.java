package yesmen.cs2340.shoppingwithfriends;

/**
 * Created by Reese on 3/12/2015.
 * Item Report object, holds the price of the item, then umber of products left,
 * and the ItemReport's rating
 *
 * ********MUST IMPLEMENT THIS LATER
 *
 */
@SuppressWarnings("ALL")
public class ItemReport {

    private String originator;
    private String productName;
    private String location;
    private double price;
    private int quantity;
    private int read;
    private double latitude;
    private double longitude;

    /**
     * Creates an ItemReport object
     * @param originator origin user of the ItemReport
     * @param productName Name of the Item
     * @param location Location of the Item
     * @param price Price of the Item
     * @param quantity Quantity of the Item
     */
    public ItemReport (String originator, String productName, String location, double price,
                       int quantity, int read, double latitude, double longitude) {
        this.originator = originator;
        this.productName = productName;
        this.location = location;
        this.price = price;
        this.quantity = quantity;
        this.read = read;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
