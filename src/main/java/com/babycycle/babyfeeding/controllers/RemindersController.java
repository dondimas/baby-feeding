package com.babycycle.babyfeeding.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import com.babycycle.babyfeeding.model.PersistenceFacade;
import com.babycycle.babyfeeding.model.Reminder;
import com.babycycle.babyfeeding.ui.activity.FeedListActivity;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 4/28/13
 * Time: 6:08 PM
 * To change this template use File | Settings | File Templates.
 */
//@Singleton
public class RemindersController {

    public void setPersistenceFacade(PersistenceFacade persistenceFacade) {
        this.persistenceFacade = persistenceFacade;
    }

    //    @Inject
    PersistenceFacade persistenceFacade;

    public void setActivity(FeedListActivity activity) {
        this.activity = activity;
    }

    @Inject
    FeedListActivity activity;

    int shownReminders = 0;
    private List<Reminder> reminders;

    public void showReminders() {
        shownReminders = 0;
        reminders = persistenceFacade.getUpdatedForTodayReminders(activity);
        showNextReminder();

    }

    private void showNextReminder() {
        Reminder chosenReminder = null;
        for(;shownReminders < reminders.size(); shownReminders ++) {
            if(!reminders.get(shownReminders).isWasConfirmed()) {
                chosenReminder = reminders.get(shownReminders);
                break;
            }

        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                setCurrentReminderConfirmed();
                showNextReminder();
            }
        });
        builder.setNegativeButton("Remind me later", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                showNextReminder();
            }
        });
        builder.setTitle(chosenReminder.getRemindMessage());
        builder.create().show();
    }

    private void setCurrentReminderConfirmed() {
        Reminder chosenReminder = reminders.get(shownReminders);
        chosenReminder.setWasConfirmed(true);
        persistenceFacade.saveReminder(chosenReminder, activity);
    }
}
