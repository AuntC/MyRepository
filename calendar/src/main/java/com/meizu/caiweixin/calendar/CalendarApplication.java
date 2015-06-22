package com.meizu.caiweixin.calendar;

import android.app.Application;

/**
 * Created by caiweixin on 5/26/15.
 */
public class CalendarApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalendarManager.initContext(this);
    }
}
