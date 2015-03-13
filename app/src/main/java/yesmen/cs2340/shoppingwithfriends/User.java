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
    private int[] reportedSales;
    private int[] newAlerts;
    private int latestReport = 0;

    /**
     * Constructor for username
     *
     * @param userName
     */
    public User(String userName) {
        this.username = userName;
    }

    /**
     * Creates a user object with fields filled from args
     * @param args Username, name, biography, location, email, phoneNumber
     */
    public User(String... args) {
        username = args[0];
        name = args[1];
        biography = args[2];
        location = args[3];
        email = args[4];
        phoneNumber = args[5];
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

    public void setReportedSales(int[] reportedSales) {
        this.reportedSales = reportedSales;
    }

    public int[] getReportedSales() {
        return reportedSales;
    }

    public void setNewAlerts(int[] newAlerts) {
        this.newAlerts = newAlerts;
    }

    public int[] getNewAlerts() {
        return newAlerts;
    }

    public int getLatestReport() { return latestReport; }

    public void setLatestReport(int latestReport) { this.latestReport = latestReport; }
}
