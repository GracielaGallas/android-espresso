package com.mytaxi.android_demo.activities.robot;


public class MainActivityTestRobot extends ScreenRobot<MainActivityTestRobot> {


    public MainActivityTestRobot checkSearchFieldIsDisplayed(int text) {
        return checkIsDisplayed(text);
    }

    public MainActivityTestRobot writeTextToResearch(int text2, String text3) {
        return enterTextIntoView(text2, text3)
                .closeKeyboard();
    }

    public MainActivityTestRobot checkIsDriverCardIsDisplayed(int driver_card){
        return checkIsDisplayed(driver_card);

    }
}
