package com.babycycle.babyfeeding.ui.controller;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.babycycle.babyfeeding.R;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 5/2/13
 * Time: 12:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClockAppController {

    private Context context;

    public void openClockAppIfPossible() {
        PackageManager packageManager = context.getPackageManager();
        Intent alarmClockIntent = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER);

        // Verify clock implementation
        String clockImpls[][] = {
                {"HTC Alarm Clock", "com.htc.android.worldclock", "com.htc.android.worldclock.WorldClockTabControl" },
                {"Standar Alarm Clock", "com.android.deskclock", "com.android.deskclock.AlarmClock"},
                {"Froyo Nexus Alarm Clock", "com.google.android.deskclock", "com.android.deskclock.DeskClock"},
                {"Moto Blur Alarm Clock", "com.motorola.blur.alarmclock",  "com.motorola.blur.alarmclock.AlarmClock"},
                {"Samsung Galaxy Clock", "com.sec.android.app.clockpackage","com.sec.android.app.clockpackage.ClockPackage"}
        };

        boolean foundClockImpl = false;

        for(int i=0; i<clockImpls.length; i++) {
            String vendor = clockImpls[i][0];
            String packageName = clockImpls[i][1];
            String className = clockImpls[i][2];
            try {
                ComponentName cn = new ComponentName(packageName, className);
                ActivityInfo aInfo = packageManager.getActivityInfo(cn, PackageManager.GET_META_DATA);
                alarmClockIntent.setComponent(cn);
                Log.e("ClockAppController", "Found " + vendor + " --> " + packageName + "/" + className);
                foundClockImpl = true;
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("ClockAppController", vendor + " does not exists");
            }
        }

        if (foundClockImpl) {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, alarmClockIntent, 0);
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {

            }
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void openClockAppIfNeed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setPositiveButton("Yes I DO!!!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                openClockAppIfPossible();
            }
        });
        builder.setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.setTitle(" Do you want to set Alarm Clock?");
        builder.create().show();
    }
}
