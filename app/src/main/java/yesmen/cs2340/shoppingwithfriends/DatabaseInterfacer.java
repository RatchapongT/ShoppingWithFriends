package yesmen.cs2340.shoppingwithfriends;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class DatabaseInterfacer, is a wrapper for the database. Communicates with the database.
 *
 * @author Luka Antolic-Soban, Resse Aitken, Ratchapong Tangkijvorakul, Matty Attokaren, Sunny Patel
 * @version 1.1
 */
public class DatabaseInterfacer {


    private static final String REMOTE_IP = "http://wtfizlinux.com";

    private static final String LOGIN_URL = "/yesmen/login.php";
    private static final String REGISTER_URL = "/yesmen/register.php";
    private static final String ADD_FRIEND_URL = "/yesmen/add_friend.php";
    private static final String VIEW_FRIEND_URL = "/yesmen/view_friends.php";
    private static final String RETRIEVE_PROFILE_URL = "/yesmen/profile_retrieve.php";
    private static final String UPDATE_PROFILE_URL = "/yesmen/profile_update.php";
    private static final String DELETE_FRIEND_URL = "/yesmen/delete_friend.php";
    private static final String UPDATE_WISHLIST_URL = "/yesmen/wishlist_update.php";
    private static final String FETCH_WISHLIST_URL = "/yesmen/wishlist_retrieve.php";
    private static final String GET_ITEM_REPORT_URL = "/yesmen/get_item_report.php";
    private static final String SEND_ALERT_URL = "/yesmen/send_alert.php";
    private static final String GET_ALERTS_URL = "/yesmen/get_alerts.php";
    private static final String CREATE_ITEM_REPORT = "/yesmen/update_item_report.php";
    private static final String RETRIEVE_ITEM_REPORTS_URL = "/yesmen/retrieve_item_report.php";

    //JSON element ids from response of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_USER = "get_user";

    /**
     * Attempts to log in to the system
     * @param username Username
     * @param password User's Password
     * @return "Login Successful!" if successful, appropriate error message otherwise
     */
    public static String login(String username, String password) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = queryDatabase(LOGIN_URL, params);
        try {
            if (json != null) {
                if (json.getInt(TAG_SUCCESS) == 1) {
                    String usernameID = json.getString(TAG_USER);
                    CurrentUser.setCurrentUser(new User(usernameID));
                }
                return json.getString(TAG_MESSAGE);
            } else return "Login Failure (Connection)";
        } catch (JSONException e) {
            e.printStackTrace();
            return "Login Failure (Technical)";
        }
    }

    /**
     * Registers a new user
     * @param username Desired Username
     * @param password Desired Password
     * @return "Username Successfully Added!" if successful, appropriate error otherwise
     */
    public static String register(String username, String password) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = queryDatabase(REGISTER_URL, params);
        try {
            if (json != null) {
                return json.getString(TAG_MESSAGE);
            } else return "Register Failure (Connection)";
        } catch (JSONException e) {
            return "Register Failure (Technical)";
        }
    }

    /**
     *
     * @param friendUser User friend to add
     * @param myUser User self
     * @return String result of addFriend operation
     */
    public static String addFriend(String friendUser, String myUser) {
        friendUser = friendUser.toLowerCase();
        myUser = myUser.toLowerCase();

        // Building Parameters
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("FriendID", friendUser));
        params.add(new BasicNameValuePair("UserID", myUser));

        JSONObject json = queryDatabase(ADD_FRIEND_URL, params);

        try {
            if (json != null) {
                return json.getString(TAG_MESSAGE);
            } else return "Database error";
        } catch (JSONException e) {
            e.printStackTrace();
            return "Database error";
        }
    }

    /**
     * Returns a String[] with String[0] being success or failure message
     * In event of success, String[1] - String[length-1] will contain the list of friends
     * @param myUser Current username
     * @return String[], String[0] will be "Success" in event of success, otherwise will contain
     * and error message.
     */
    public static String[] viewFriends(String myUser) {
        myUser = myUser.toLowerCase();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("UserID", myUser));
        String[] ret = new String[1];
        try {
            JSONObject json = queryDatabase(VIEW_FRIEND_URL, params);
            if (json == null) {
                ret[0] = "Database Error: no json object returned";
                return ret;
            }
            // check your log for json response
            JSONArray jArray = json.getJSONArray(TAG_MESSAGE);
            if (jArray == null) {
                ret[0] = "Database Error: No Friends Found";
                return ret;
            }

            if (json.getInt(TAG_SUCCESS) == 1) {
                ret = new String[jArray.length() + 1];
                for(int i = 0; i < jArray.length(); i++) {
                    ret[i + 1] = jArray.getString(i);
                }

                ret[0] = "Success";
            } else {
                ret[0] =  "No Friends.";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            ret[0] = "No friends found!";
        }
        return ret;
    }



    public static User retrieveProfile(String username) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        JSONObject json = queryDatabase(RETRIEVE_PROFILE_URL, params);

        if (json != null) {
            try {
                JSONObject friend = json.getJSONObject(TAG_MESSAGE);
                User ret = new User(friend.getString("Username"));
                ret.setName(friend.getString("Name"));
                ret.setBiography(friend.getString("Biography"));
                ret.setEmail(friend.getString("Email"));
                ret.setLocation(friend.getString("Location"));
                ret.setPhoneNumber(friend.getString("Phonenum"));

                //Log.d("Database Query Successful!", friend);
                return ret;
            } catch (JSONException e) {
                Log.d("FUCK THIS SHIT", e.getMessage());
                e.printStackTrace();
            }
        }
        Log.d("FUCK THIS SHIT", "FUCK THIS SHIT");
        return new User("Fuck","Fuck","Fuck","Fuck","Fuck","Fuck");
    }

    public static String deleteFriend(String myUser, String myFriend) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("UserID", myUser));
        params.add(new BasicNameValuePair("FriendID", myFriend));
        JSONObject json = queryDatabase(DELETE_FRIEND_URL, params);

        try {
            if (json != null) {
                return json.getString(TAG_MESSAGE);
            } else return "Database error";
        } catch (JSONException e) {
            e.printStackTrace();
            return "Database error";
        }
    }

    public static String updateProfile(String username, String name, String biography,
        String email, String location, String phonenum) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("Username", username));
        params.add(new BasicNameValuePair("Name", name));
        params.add(new BasicNameValuePair("Biography", biography));
        params.add(new BasicNameValuePair("Email", email));
        params.add(new BasicNameValuePair("Location", location));
        params.add(new BasicNameValuePair("Phonenum", phonenum));
        JSONObject json = queryDatabase(UPDATE_PROFILE_URL, params);

        try {
            if (json != null) {
                return json.getString(TAG_MESSAGE);
            } else return "Database error";
        } catch (JSONException e) {
            e.printStackTrace();
            return "Database error";
        }
    }

    /**
     * Returns the user's wishlist
     * @param user The user in question
     * @return a copy of the Wishlist object belonging to the user
     * @throws DatabaseErrorException
     */
    public static Wishlist getWishlist(String user) throws DatabaseErrorException {
        user = user.toLowerCase();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", user));
        Wishlist ret = new Wishlist();
        try {
            JSONObject json = queryDatabase(FETCH_WISHLIST_URL, params);
            Log.d("ASDFASDFSA", user);
            if (json == null) {
                throw new DatabaseErrorException("Database Error: no json object returned");
            }
            // check your log for json response
            JSONArray jArray = json.getJSONArray(TAG_MESSAGE);
            if (jArray == null) {
                throw new DatabaseErrorException("Database Error: No Wishlist Found");
            }

            if (json.getInt(TAG_SUCCESS) == 1) {

                JSONObject temp;
                for(int i = 0; i < jArray.length(); i++) {
                    temp = jArray.getJSONObject(i);
                    Item item = new Item(temp.getString("Item"), temp.getInt("Price"));
                    ret.addToWishlist(item);

                }
            } else if (json.getInt(TAG_SUCCESS) == 2) {
                return ret;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Something went horribly wrong!");
        }
        return ret;
    }

    /**
     * Adds an item to the wishlist
     * @param item name of the item
     * @param threshold maximum price the user is willing to pay for the item
     * @return String success or failure message
     */
    public static String addToWishlist(String item, int threshold) {
        List<NameValuePair> params = new ArrayList<>();
        String myUser = CurrentUser.getCurrentUser().getUsername().toLowerCase();
        params.add(new BasicNameValuePair("Username", myUser));
        params.add(new BasicNameValuePair("Item", item));
        params.add(new BasicNameValuePair("Price", Integer.toString(threshold)));
        JSONObject json = queryDatabase(UPDATE_WISHLIST_URL, params);
        try {
            if (json != null && json.getInt(TAG_SUCCESS) == 1) {
                return item + " successfully added to wishlist";
            } else {
                return "Database error, item may not have been added";
            }
        } catch (JSONException e) {
            return "Database error";
        }
    }

    /**
     * Validates the login attempt with the help of the query database.
     * @param URL URL ending of the desired php page to connect to
     * @param params List f Name Value pairs to pass to the database
     * @return json Returns a Json object from which to pull the pertinent information
     */
    private static JSONObject queryDatabase(String URL, List<NameValuePair> params) {
        try {

            JSONParser jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(REMOTE_IP + URL, "POST", params);

            if (json == null) {
                return null;
            }

            Log.d("json Tag Message", json.getString(TAG_MESSAGE));

            if (json.getInt(TAG_SUCCESS) == 1) {
                Log.d("Database Query Successful!", json.toString());
                return json;
            } else {
                Log.d("Database Query Failure!", json.toString());
                return json;

            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method sends a username and an ID to the database. The database should add
     * the ID to the newAlerts[] integer array that belongs to arg user.
     * @param user Friend recieving the alert
     * @param reportID ID of the itemReport in the database
     * @return "success" if successfully added to databes, "failure" otherwise
     *
    public static String sendAlert(String user, int reportID) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("Username", user.toLowerCase()));
        params.add(new BasicNameValuePair("ID", ""+reportID));
        JSONObject json = queryDatabase(SEND_ALERT_URL, params);
        if (json == null) {
            Log.d("sendAlert Error", "error connecting to database");
            return "failure";
        } else {
            Log.d("sendAlert Success", "Alert sent to " + user);
            return "success";
        }
    }
    */

    /**
     * This method retrieves an int[] from the database. The int[] is an array of
     * Report IDs that each represent a unique ItemReport.
     * @return int[] of report IDs that are relevant to the current user's wishlist
     * @throws DatabaseErrorException
     */
    public static int[] getAlerts() throws DatabaseErrorException {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("Username", CurrentUser.getCurrentUser().getUsername().toLowerCase()));
        int[] ret = new int[1];
        try {
            JSONObject json = queryDatabase(GET_ALERTS_URL, params);
            if (json == null) {
                throw new DatabaseErrorException("Database Error: no json object returned");
            }

            JSONArray jArray = json.getJSONArray(TAG_MESSAGE);
            if (jArray == null) {
                throw new DatabaseErrorException("Database Error: No Friends Found");
            }

            if (json.getInt(TAG_SUCCESS) == 1) {
                ret = new int[jArray.length()];
                for(int i = 0; i < jArray.length(); i++) {
                    ret[i] = jArray.getInt(i);
                }
            } else {
                ret[0] = 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Unexpected JSONException");
        }
        return ret;
}

    /**
     * Passes the database the fields of an ItemReport object
     * Database should store these fields with a unique integer ID
     * Database should then return said ID
     * @param productName product's name
     * @param location location of the item
     * @param price price of the item
     * @param quantity quantity of the item
     * @return integer ID key for the newly stored database ItemReport
     */
    public static String createItemReport(String productName, String location,
                                           double price, int quantity) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("UserID", CurrentUser.getCurrentUser().getUsername().toLowerCase()));
        params.add(new BasicNameValuePair("Name", productName));
        params.add(new BasicNameValuePair("Location", location));
        params.add(new BasicNameValuePair("Price", ""+price));
        params.add(new BasicNameValuePair("Quantity", ""+quantity));
        try {
            JSONObject json = queryDatabase(CREATE_ITEM_REPORT, params);
            if (json != null && json.getInt(TAG_SUCCESS) == 1) {
                return productName + " successfully reported as a sale";
            } else {
                return "Database error, item may not have been added";
            }
        } catch (JSONException e) {
            return "Database error";
        }

    }

    /**
     * Passes the database a report ID and retrieves the information associated with the ID
     * @param reportID the unique integer ID associated with a report object
     * @return ItemReport a copy of the itemreport stored on the database with reportID
     *//*
    public static ItemReport getItemReport(int reportID) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("ID", ""+reportID));
        JSONObject json = queryDatabase(GET_ITEM_REPORT_URL, params);
        try {
            ItemReport ret;
            if (json != null) {
                ret = new ItemReport(json.getString("name"), json.getString("location"),
                        json.getDouble("price"), json.getInt("quantity"));
            } else {
                ret = null;
            }
            return ret;
        } catch (JSONException e) {
            Log.d("itemReport Error", "error connecting to database");
        }

        return null;
    }
    */

    /**
     * Fetches the entire new item report list for the current user
     * @return ItemReport[] all relevant ItemReports
     * @throws DatabaseErrorException if the database has trouble
     */
    public static ItemReport[] retrieveItemReports() throws DatabaseErrorException {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("Username", CurrentUser.getCurrentUser().getUsername().toLowerCase()));
        try {
            JSONObject json = queryDatabase(RETRIEVE_ITEM_REPORTS_URL, params);
            ItemReport[] ret;

            JSONArray jArray = json.getJSONArray(TAG_MESSAGE);
            if (jArray == null) {
                throw new DatabaseErrorException("Database Error: no json object returned");
            }

            if (json.getInt(TAG_SUCCESS) == 1) {
                ret = new ItemReport[jArray.length()];
                for(int i = 0; i < jArray.length(); i++) {
                    JSONObject jtemp = jArray.getJSONObject(i);
                    ret[i] = new ItemReport(jtemp.getString("Username"),
                            jtemp.getString("Name"),
                            jtemp.getString("Location"),
                            jtemp.getDouble("Price"),
                            jtemp.getInt("Quantity"));
                }
            } else {
                return null;
            }
            return ret;
        } catch (JSONException e) {
            Log.d("itemReport Error", "error connecting to database");
            throw new DatabaseErrorException("Database Error: unexpected JSONException");
        }
    }
}
