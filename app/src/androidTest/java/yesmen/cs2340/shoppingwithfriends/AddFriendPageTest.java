package yesmen.cs2340.shoppingwithfriends;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.EditText;

public class AddFriendPageTest extends ActivityInstrumentationTestCase2<AddFriendPage> {

    private AddFriendPage activity;
    private EditText username;
    private String myUser = "test";
    private String myUserPass = "test";
    private String myFriend = "test2";
    private String myImaginaryFriend = "test3";

    @SuppressWarnings("deprecation")
    public AddFriendPageTest() {
        super("yesmen.cs2340.shoppingwithfriends", AddFriendPage.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        username = (EditText) activity.findViewById(R.id.add_friend_username_input);
    }

    public void testAddFriend() throws Throwable {
        DatabaseInterfacer.login(myUser, myUserPass);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                username.setText(myFriend);
            }
        });
        getInstrumentation().waitForIdleSync();
        assertEquals(myFriend, username.getText().toString());
        TouchUtils.clickView(this, activity.findViewById(R.id.add_friend_execute_button));
        assertTrue(myFriend + " was successfully added", checkForFriend(myFriend));
        deleteFriend(myFriend);
    }

    public void testAddExistingFriend() throws Throwable {
        DatabaseInterfacer.login(myUser, myUserPass);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                username.setText(myFriend);
            }
        });
        getInstrumentation().waitForIdleSync();
        assertEquals(myFriend, username.getText().toString());
        TouchUtils.clickView(this, activity.findViewById(R.id.add_friend_execute_button));
        assertTrue(myFriend + " was successfully added", checkForFriend(myFriend));
        TouchUtils.clickView(this, activity.findViewById(R.id.add_friend_execute_button));
        int friendEntries = 0;
        for (String friend: DatabaseInterfacer.viewFriends(myUser)) {
            if (friend.equals(myFriend)) {
                friendEntries++;
            }
        }
        assertEquals(friendEntries, 1);
    }

    public void testAddNonexistantFriend() throws Throwable {
        DatabaseInterfacer.login(myUser, myUserPass);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                username.setText(myImaginaryFriend);
            }
        });
        getInstrumentation().waitForIdleSync();
        assertEquals(myImaginaryFriend, username.getText().toString());
        TouchUtils.clickView(this, activity.findViewById(R.id.add_friend_execute_button));
        assertFalse(myFriend + " was successfully added", checkForFriend(myImaginaryFriend));
    }

    private boolean checkForFriend(String name) {
        for (String friend: DatabaseInterfacer.viewFriends(myUser)) {
            if (friend.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private void deleteFriend(String name) {
        DatabaseInterfacer.deleteFriend(myUser, name);
    }
}