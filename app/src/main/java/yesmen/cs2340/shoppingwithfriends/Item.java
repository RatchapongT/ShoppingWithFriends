package yesmen.cs2340.shoppingwithfriends;

/**
 * Created by Reese on 3/5/2015.
 */
public class Item implements Comparable<Item> {
    private String name;
    private int price;

    /**
     * Creates a new Item object
     * @param name name of the item
     * @param price price of the item
     */
    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Returns the name of the item
     * @return item name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the item
     * @return item price
     */
    public int getPrice() {
        return price;
    }

    @Override
    public int compareTo(Item other) {
        return this.name.compareTo(other.getName());
    }
}
