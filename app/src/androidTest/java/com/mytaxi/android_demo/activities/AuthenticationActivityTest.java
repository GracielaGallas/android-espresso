package com.mytaxi.android_demo.activities;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mytaxi.android_demo.TestApplication;
import com.mytaxi.android_demo.activities.robot.AuthenticationActivityTestRobot;
import com.mytaxi.android_demo.dependencies.component.TestAppComponent;
import com.mytaxi.android_demo.utils.PermissionHelper;
import com.mytaxi.android_demo.utils.network.HttpClient;
import com.mytaxi.android_demo.utils.storage.SharedPrefStorage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static com.mytaxi.android_demo.dependencies.common.AppTestContents.BUTTON_LOGIN;
import static com.mytaxi.android_demo.dependencies.common.AppTestContents.CORRECT_PASS;
import static com.mytaxi.android_demo.dependencies.common.AppTestContents.CORRECT_USER;
import static com.mytaxi.android_demo.dependencies.common.AppTestContents.FIELD_PASSWORD;
import static com.mytaxi.android_demo.dependencies.common.AppTestContents.FIELD_USER;
import static com.mytaxi.android_demo.dependencies.common.AppTestContents.WRONG_PASS;
import static com.mytaxi.android_demo.dependencies.common.AppTestContents.WRONG_USER;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class AuthenticationActivityTest {

    @Inject
    HttpClient httpClient;

    @Inject
    SharedPrefStorage sharedPrefStorage;

    @Inject
    PermissionHelper permissionHelper;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class, true, false);


    @Test
    public void shouldSeeAuthenticationActivity_whenUserIsNotAuthenticated() {
        activityTestRule.launchActivity(null);
        new AuthenticationActivityTestRobot()
                .checkUserFieldIsDisplayed(FIELD_USER);
    }

    @Test
    public void shouldRemainOnLoginPage_whenLoginCredentialsAreNotValid() {
        activityTestRule.launchActivity(null);
        new AuthenticationActivityTestRobot()
                .writeUser(FIELD_USER, WRONG_USER)
                .writePassword(FIELD_PASSWORD, WRONG_PASS)
                .pressContinue(BUTTON_LOGIN)
                .checkUserFieldIsDisplayed(FIELD_USER)
                .checkPasswordIsDisplayed(FIELD_PASSWORD)
                .checkButtonLoginIsClickable(BUTTON_LOGIN);
    }

    @Test
    public void shouldOpenMainActivity_whenLoginCredentialsAreValid() {
        activityTestRule.launchActivity(null);
        new AuthenticationActivityTestRobot()
                .writeUser(FIELD_USER, CORRECT_USER)
                .writePassword(FIELD_PASSWORD, CORRECT_PASS)
                .pressContinue(BUTTON_LOGIN);
    }


    @Before
    public void setup() {
        final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        final TestApplication app =
                (TestApplication) instrumentation.getTargetContext().getApplicationContext();
        final TestAppComponent appComponent = (TestAppComponent) app.getAppComponent();
        appComponent.inject(this);

        when(sharedPrefStorage.loadUser()).thenReturn(null);
    }
}