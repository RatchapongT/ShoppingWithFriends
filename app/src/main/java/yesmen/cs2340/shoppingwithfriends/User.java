package yesmen.cs2340.shoppingwithfriends;

/**
 * Class User has all the information about the user.
 *
 * @author      Luka Antolic-Soban, Resse Aitken, Ratchapong Tangkijvorakul, Matty Attokaren, Sunny Patel
 * @version     1.6
 */
public class User {

    private String username;
    private String name;
    private String biography;
    private String location;
    private String email;
    private String phoneNumber;

    /**
     * Constructor for username
     *
     * @param userName
     */
    public User(String userName) {
        this.username = userName;
    }

    /**
     * Gets the username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Constructor for setting name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Constructor for setting biography
     *
     * @param biography
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     *  Gets user biography
     *
     * @return biography
     */
    public String getBiography() {
        return biography;
    }

    /**
     * Constructor for setting location
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets user location
     *
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Constructor for setting user email
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the user email
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Constructor for setting user phone number
     *
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the user phone number
     *
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
