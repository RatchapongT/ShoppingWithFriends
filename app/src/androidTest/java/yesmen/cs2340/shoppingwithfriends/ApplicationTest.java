package yesmen.cs2340.shoppingwithfriends;

import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestCase;
import android.test.TouchUtils;
import android.widget.Button;

/**
 * ApplicationTest checks the delete Friend feature.
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2<DetailedFriend> {

    public DetailedFriend activity;
    public Button deleteFriend;
    @SuppressWarnings("deprecation")
    public ApplicationTest() {
        super("yesmen.cs2340.shoppingwithfriends", DetailedFriend.class);
    }

    /**
     * Get Activity method
     * @throws Exception
     */
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    /**
     * Test case of deleting a friend
     * @throws Throwable
     */
    public void testDeleteFriend() throws Throwable {
        DatabaseInterfacer.login("sunny", "sunny");
        DatabaseInterfacer.addFriend("param", "sunny");

        String[] names = DatabaseInterfacer.viewFriends("sunny");
        boolean found = false;
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals("param")) {
                found = true;
            }
        }
        assertTrue(found);
        DatabaseInterfacer.retrieveProfile("param");
        //DatabaseInterfacer.deleteFriend("sunny", "param");
        deleteFriend = (Button) activity.findViewById(yesmen.cs2340.shoppingwithfriends.R.id.delete_friends);
        TouchUtils.clickView(this, deleteFriend);
        Thread.sleep(500);
        names = DatabaseInterfacer.viewFriends("sunny");
        found = false;
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals("param")) {
                found = true;
            }
        }
        assertFalse(found);
    }

//    /**
//     * Test if the user has any friends.
//     * @throws Throwable
//     */
//    public void testfriendFound() throws Throwable {
//        DatabaseInterfacer.login("sunny", "sunny");
//
//        String [] names = DatabaseInterfacer.viewFriends("sunny");
//        if (names.length == 0) {
//            assertEquals("No Friends Found!", names[0]);
//        }
//
//        DatabaseInterfacer.addFriend("param", "sunny");
//        if (names.length != 0) {
//            assertEquals("Success", names[0]);
//        }
//        DatabaseInterfacer.deleteFriend("sunny","param");
//    }

}