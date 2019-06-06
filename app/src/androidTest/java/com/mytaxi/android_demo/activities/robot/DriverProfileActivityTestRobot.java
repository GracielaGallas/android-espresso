package com.mytaxi.android_demo.activities.robot;

public class DriverProfileActivityTestRobot extends ScreenRobot<DriverProfileActivityTestRobot> {




    public DriverProfileActivityTestRobot clickAtButtonPhone(int viewId){
        return clickOnView(viewId);
    }

    public DriverProfileActivityTestRobot checkSearchFieldIsDisplayed(int text) {
        return checkIsDisplayed(text);
    }

    public DriverProfileActivityTestRobot writeTextToResearch(int text2, String text3) {
        return enterTextIntoView(text2, text3)
                .closeKeyboard();
    }
    public DriverProfileActivityTestRobot pressContinue(int phone_button) {
        clickOnView(phone_button);
        return this;
    }
}
