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

    private static final String LOCAL_IP = "http://10.0.2.2:80";
    private static final String REMOTE_IP = "http://73.207.216.173:80";

    private static final String LOGIN_URL = "/yesmen/login.php";
    private static final String REGISTER_URL = "/yesmen/register.php";
    private static final String ADD_FRIEND_URL = "/yesmen/add_friend.php";
    private static final String VIEW_FRIEND_URL = "/yesmen/view_friends.php";

    //JSON element ids from response of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_USER = "get_user";

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

    private static JSONObject queryDatabase(String URL, List<NameValuePair> params) {
        try {

            JSONParser jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(REMOTE_IP + URL, "POST", params);

            if (json == null) {
                json = jsonParser.makeHttpRequest(LOCAL_IP + URL, "POST", params);
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
}
