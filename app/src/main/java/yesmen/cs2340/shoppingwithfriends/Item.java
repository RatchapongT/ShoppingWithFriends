package yesmen.cs2340.shoppingwithfriends;

import android.support.annotation.NonNull;

/**
 * This class represents an Item object
 */
public class Item implements Comparable<Item> {
    private final String name;
    private final int price;

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

    /**
     * Compares two Items
     * @param other Item to compare with this
     * @return integer result of comparison
     */
    public int compareTo(@NonNull Item other) {
        return this.name.compareTo(other.getName());
    }
}
