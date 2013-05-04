package com.babycycle.babyfeeding.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import com.babycycle.babyfeeding.R;
import roboguice.activity.RoboActivity;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 5/3/13
 * Time: 10:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReminderListActivity extends RoboActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_list);
    }
}