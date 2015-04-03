package yesmen.cs2340.shoppingwithfriends;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

import junit.framework.TestCase;


public class LoginPageTest extends ActivityInstrumentationTestCase2<LoginPage>{

    private LoginPage myActivity;
    private Button loginButton;
    private Button registerButton;
    private EditText username;
    private EditText password;


//    private final String expectedUser = "luka";
//    private final String expectedPass = "hello";

    public LoginPageTest() {
        super("yesmen.cs2340.shoppingwithfriends", LoginPage.class);
    }


    @Override
    public void setUp() throws Exception{
        super.setUp();

        setActivityInitialTouchMode(true);

        myActivity = getActivity();

        loginButton = (Button) myActivity.findViewById(R.id.login_execute_button);
        registerButton = (Button) myActivity.findViewById(R.id.login_register_button);

        username = (EditText) myActivity.findViewById(R.id.login_username_input);
        password = (EditText) myActivity.findViewById(R.id.login_password_input);


    }

    public void testLoginButton_layout() {
        final View decorView = myActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, loginButton);

        final ViewGroup.LayoutParams layoutParams = loginButton.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void testUsernameLayout_layout() {
        final View decorView = myActivity.getWindow().getDecorView();
        ViewAsserts.assertOnScreen(decorView, username);
        assertTrue(View.VISIBLE == username.getVisibility());
    }

    public void testUsernameText() {

        myActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                username.setText("luka");
            }
        });
        getInstrumentation().waitForIdleSync();
        assertEquals("luka", username.getText().toString());

    }

//    public void testPasswordText() {
//
//        myActivity.runOnUiThread(new Runnable() {
//             @Override
//             public void run() {
//                 password.setText("hello");
//             }
//             });
//        getInstrumentation().waitForIdleSync();
//        assertEquals("hello", password.getText().toString());
//    }


    public void testLoginSuccess_LoginAndPassMatch() throws Throwable {
        myActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                username.setText("luka");
                password.setText("hello");
                loginButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        Thread.sleep(2000);
        assertEquals("Login successful!", myActivity.response);
    }




}