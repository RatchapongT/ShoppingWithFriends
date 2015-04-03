package yesmen.cs2340.shoppingwithfriends;

/**
 * General exception to be thrown by DatabaseInterfacer
 */
public class DatabaseErrorException extends Exception {

    /**
     * Creates a new Database Error Exception with a message
     * @param message the message you want to give
     */
    public DatabaseErrorException(String message) {
        super(message);
    }
}
