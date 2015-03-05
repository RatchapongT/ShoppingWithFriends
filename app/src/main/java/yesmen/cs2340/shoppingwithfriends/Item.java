package yesmen.cs2340.shoppingwithfriends;

/**
 * Created by Reese on 3/5/2015.
 */
public class Item {
    private String name;
    private int price;
    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
