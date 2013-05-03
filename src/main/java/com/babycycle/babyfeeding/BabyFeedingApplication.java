package com.babycycle.babyfeeding;

import android.app.Application;
import android.content.Context;
import com.babycycle.babyfeeding.model.DatabaseHelper;
import com.google.inject.Singleton;
import roboguice.RoboGuice;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 5/1/13
 * Time: 12:03 AM
 * To change this template use File | Settings | File Templates.
 */
@Singleton
public class BabyFeedingApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RoboGuice.getInjector(this).injectMembers(this);
        context = this.getApplicationContext();
//        DatabaseHelper databaseHelper = new DatabaseHelper(this);
    }

    private static Context context;



    public static Context getAppContext() {
        return BabyFeedingApplication.context;
    }
}
