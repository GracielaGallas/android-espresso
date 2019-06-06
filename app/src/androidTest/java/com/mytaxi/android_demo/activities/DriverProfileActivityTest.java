package com.mytaxi.android_demo.activities;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mytaxi.android_demo.TestApplication;
import com.mytaxi.android_demo.TestUtils;
import com.mytaxi.android_demo.activities.robot.DriverProfileActivityTestRobot;
import com.mytaxi.android_demo.dependencies.component.TestAppComponent;
import com.mytaxi.android_demo.models.User;
import com.mytaxi.android_demo.utils.network.HttpClient;
import com.mytaxi.android_demo.utils.storage.SharedPrefStorage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.mytaxi.android_demo.dependencies.common.AppTestContents.CORRECT_DRIVER;
import static com.mytaxi.android_demo.dependencies.common.AppTestContents.CORRECT_PASS;
import static com.mytaxi.android_demo.dependencies.common.AppTestContents.CORRECT_USER;
import static com.mytaxi.android_demo.dependencies.common.AppTestContents.DRIVER_CARD;
import static com.mytaxi.android_demo.dependencies.common.AppTestContents.FIELD_SEARCH;
import static com.mytaxi.android_demo.dependencies.common.AppTestContents.PHONE_BUTTON;
import static com.mytaxi.android_demo.dependencies.common.AppTestContents.SALT;
import static com.mytaxi.android_demo.dependencies.common.AppTestContents.TO_SEARCH;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class DriverProfileActivityTest {

    @Inject
    HttpClient httpClient;

    @Inject
    SharedPrefStorage sharedPrefStorage;

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule
            = new IntentsTestRule<>(MainActivity.class, true, false);

    private final User user = new User(
            CORRECT_USER, SALT, TestUtils.calculateSHA256(CORRECT_PASS, SALT));

    @Test
    public void shouldLaunchPhoneDialer_whenPhoneIconIsClicked() {
        showDriversProfile();
        new DriverProfileActivityTestRobot()
                .clickOnView(DRIVER_CARD)
                .pressContinue(PHONE_BUTTON);
    }

    private void showDriversProfile() {
        final ArgumentCaptor<HttpClient.DriverCallback> driverCallbackArgumentCaptor =
                ArgumentCaptor.forClass(HttpClient.DriverCallback.class);

        intentsTestRule.launchActivity(null);
        verify(httpClient).fetchDrivers(driverCallbackArgumentCaptor.capture());
        final HttpClient.DriverCallback callback = driverCallbackArgumentCaptor.getValue();
        callback.setDrivers(TestUtils.getDversList());
        callback.run();
        new DriverProfileActivityTestRobot()
                .checkSearchFieldIsDisplayed(FIELD_SEARCH)
                .enterTextIntoView(FIELD_SEARCH, TO_SEARCH);
        onView(withText(CORRECT_DRIVER))
                .inRoot(
                        withDecorView(not(is(intentsTestRule
                                .getActivity()
                                .getWindow()
                                .getDecorView()))))
                .perform(click());

    }

    @Before
    public void setup() {
        final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        final TestApplication app =
                (TestApplication) instrumentation.getTargetContext().getApplicationContext();
        final TestAppComponent appComponent = (TestAppComponent) app.getAppComponent();
        appComponent.inject(this);

        when(sharedPrefStorage.loadUser()).thenReturn(user);
    }
}