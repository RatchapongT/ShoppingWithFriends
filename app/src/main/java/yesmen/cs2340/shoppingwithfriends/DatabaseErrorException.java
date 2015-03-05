package yesmen.cs2340.shoppingwithfriends;

/**
 * Created by Reese on 3/5/2015.
 */
public class DatabaseErrorException extends Exception {

    /**
     * Creates a new Database Error Exception
     */
    public DatabaseErrorException() {
        super();
    }

    /**
     * Creates a new Database Error Exception with a message
     * @param message the message you want to give
     */
    public DatabaseErrorException(String message) {
        super(message);
    }
}
