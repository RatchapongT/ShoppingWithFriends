package yesmen.cs2340.shoppingwithfriends;

/**
 * Class User has all the information regarding the user
 *
 * @author Luka Antolic-Soban, Resse Aitken, Ratchapong Tangkijvorakul, Matty Attokaren, Sunny Patel
 * @version 1.6
 */
public class User {

    private String userName;
    private String name;
    private String biography;
    private String location;
    private String email;
    private String phoneNumber;

    /**
     * Sets the username
     *
     * @param userName
     */
    public User(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the username.
     *
     * @return username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the biography.
     *
     * @param biography
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     * Gets the biography.
     *
     * @return biography
     */
    public String getBiography() {
        return biography;
    }

    /**
     * Sets the location.
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the location.
     *
     *
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the email.
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the email.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the phone number.
     *
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the phone number.
     *
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
