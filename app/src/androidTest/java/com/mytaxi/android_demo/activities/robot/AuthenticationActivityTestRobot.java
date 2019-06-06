package com.mytaxi.android_demo.activities.robot;


public class AuthenticationActivityTestRobot extends ScreenRobot<AuthenticationActivityTestRobot> {


    public AuthenticationActivityTestRobot checkUserFieldIsDisplayed(int field_user) {
        checkIsDisplayed(field_user);
        return this;
    }

    public AuthenticationActivityTestRobot checkPasswordIsDisplayed(int field_password) {
        checkIsDisplayed(field_password);
        return this;
    }

    public AuthenticationActivityTestRobot checkButtonLoginIsClickable(int button_login) {
        checkIsClickable(button_login);
        return this;
    }

    public AuthenticationActivityTestRobot pressContinue(int button_login) {
        clickOnView(button_login);
        return this;
    }

    public AuthenticationActivityTestRobot writeUser(int field_user, String text) {
        return enterTextIntoView(field_user, text)
                .closeKeyboard();
    }

    public AuthenticationActivityTestRobot writePassword(int field_password, String pass) {
        return enterTextIntoView(field_password, pass)
                .closeKeyboard();
    }

    public AuthenticationActivityTestRobot printLogOfTest(String result_test) {
        return printLog(result_test);
    }

}