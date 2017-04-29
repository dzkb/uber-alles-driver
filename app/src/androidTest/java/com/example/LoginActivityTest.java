package com.example;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.tomek.uberallesdriver.CustomerActivity;
import com.example.tomek.uberallesdriver.LoginActivity;
import com.example.tomek.uberallesdriver.R;
import com.robotium.solo.Solo;


public class LoginActivityTest extends
        ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;
    //@Rule public final ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testCorrectLogin() throws Exception {
        solo.assertCurrentActivity("wrong activity", LoginActivity.class);
        EditText number = (EditText) solo.getView(R.id.edit_text_phone_number);
        EditText password = (EditText) solo.getView(R.id.edit_text_password);

        solo.enterText(number, "700800300");
        solo.enterText(password, "dupa.8");

        solo.clickOnButton(solo.getString(R.string.login));

        solo.assertCurrentActivity("wrong activity", CustomerActivity.class);
    }

    public void testIncorrectLogin() throws Exception {
        solo.assertCurrentActivity("wrong activity", LoginActivity.class);
        EditText number = (EditText) solo.getView(R.id.edit_text_phone_number);
        EditText password = (EditText) solo.getView(R.id.edit_text_password);

        solo.enterText(number, "700800300");
        solo.enterText(password, "dupa");

        solo.clickOnButton(solo.getString(R.string.login));
        solo.waitForText(solo.getString(R.string.wrong_password));
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}