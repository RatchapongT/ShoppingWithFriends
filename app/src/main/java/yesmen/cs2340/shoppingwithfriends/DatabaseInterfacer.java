package yesmen.cs2340.shoppingwithfriends;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reese on 2/20/2015.
 */
public class DatabaseInterfacer {

    private static final String IP_PUNCH = "http://10.0.2.2:80";
    private static final String IP = "http://73.207.216.173:80";

    private static final String LOGIN_URL = "/yesmen/login.php";
    private static final String REGISTER_URL = "/yesmen/register.php";
    private static final String FRIEND_URL = "/yesmen/add_friend.php";

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
            } else return "Database error, no response from server";
        }   catch (JSONException e) {
            e.printStackTrace();
            return "Database error";
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
            } else return "Database error";
        }   catch (JSONException e) {
            return "Database error";
        }
    }

    /**
     *
     * @param friendUser User friend to add
     * @param myUser User self
     * @return String result of addFriend operation
     */
    public static String addFriend(String friendUser, String myUser) {
        int addFriendSuccess = 0;
        friendUser = friendUser.toLowerCase();
        myUser = myUser.toLowerCase();

        // Building Parameters
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("FriendID", friendUser));
        params.add(new BasicNameValuePair("UserID", myUser));

        JSONObject json = queryDatabase(FRIEND_URL, params);

        try {
            if (json != null) {
                return json.getString(TAG_MESSAGE);
            } else return "Database error";
        } catch (JSONException e) {
            e.printStackTrace();
            return "Database error";
        }
    }



    private static JSONObject queryDatabase(String URL, List<NameValuePair> params) {
        try {
            Log.d("Request!", "Starting the validation process");
            // getting product details by making HTTP request
            JSONParser jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(IP+URL, "POST", params);

            if (json == null) {
                json = jsonParser.makeHttpRequest(IP_PUNCH+URL, "POST", params);
            }

            // check your log for json response
            Log.d("Login attempt", json.toString());

            // json success tag
            int success = json.getInt(TAG_SUCCESS);

            Log.d("json Tag Message", json.getString(TAG_MESSAGE));
            if (success == 1) {
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
