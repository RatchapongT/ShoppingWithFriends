package yesmen.cs2340.shoppingwithfriends;

/**
 * Class CurrentUser keeps track of which user is logged in.
 *
 * @author Luka Antolic-Soban, Resse Aitken, Ratchapong Tangkijvorakul, Matty Attokaren, Sunny Patel
 * @version 1.1
 */
public class CurrentUser {
    private static User current;
    /**
     * Constructor for current user
     *
     * @param u
     */
    public CurrentUser(User u) {
        current = u;
    }
    /**
     * Gets the current user
     *
     * @return current
     */
    public static User getCurrentUser() {
        return current;
    }
    /**
     * Sets the current user
     *
     * @param u
     */
    public static void setCurrentUser(User u) {
        current = u;
    }
}
