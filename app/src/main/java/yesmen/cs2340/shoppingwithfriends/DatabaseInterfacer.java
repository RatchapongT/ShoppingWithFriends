package yesmen.cs2340.shoppingwithfriends;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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


    private static final String REMOTE_IP = "http://wtfizlinux.com/";

    private static final String LOGIN_URL = "/yesmen/login.php";
    private static final String REGISTER_URL = "/yesmen/register.php";
    private static final String ADD_FRIEND_URL = "/yesmen/add_friend.php";
    private static final String VIEW_FRIEND_URL = "/yesmen/view_friends.php";

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
     * Validates the login attempt with the help of the query database.
     * @param URL URL ending of the desired php page to connect to
     * @param params List f Name Value pairs to pass to the database
     * @return json Returns a Json object from which to pull the pertinent information
     */
    private static JSONObject queryDatabase(String URL, List<NameValuePair> params) {
        try {

            JSONParser jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(REMOTE_IP + URL, "POST", params);

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
}
