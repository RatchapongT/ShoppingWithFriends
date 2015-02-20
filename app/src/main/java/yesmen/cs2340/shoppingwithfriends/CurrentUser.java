package yesmen.cs2340.shoppingwithfriends;

/**
 * Created by Reese on 2/19/2015.
 */
public class CurrentUser {
    private static User current;
    public CurrentUser(User u) {
        current = u;
    }

    public User getCurrentUser() {
        return current;
    }

    public void setCurrentUser(User u) {
        current = u;
    }
}
