package com.babycycle.babyfeeding.ui.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import com.babycycle.babyfeeding.R;
import com.babycycle.babyfeeding.model.PersistenceFacade;
import com.babycycle.babyfeeding.model.Reminder;
import com.babycycle.babyfeeding.ui.activity.FeedListActivity;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 4/28/13
 * Time: 6:08 PM
 * To change this template use File | Settings | File Templates.
 */
//@Singleton
public class RemindersController {

    private final long timeDelayForNextRemindersShowingInMillis = 1000*60*60;

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

    private long lastRemindersShownTime = 0;

    public void showReminders() {
        long currentTime = Calendar.getInstance(Locale.US).getTimeInMillis();
        if(tooEarlyToShowReminder()) return;
        lastRemindersShownTime = currentTime;

        shownReminders = 0;
        reminders = persistenceFacade.getUpdatedForTodayReminders(activity);
        showNextReminder();

    }

    private boolean tooEarlyToShowReminder() {
        long currentTime = Calendar.getInstance(Locale.US).getTimeInMillis();
        if((currentTime -lastRemindersShownTime) < timeDelayForNextRemindersShowingInMillis) {
            return true;
        }
        lastRemindersShownTime = currentTime;
        return false;
    }

    private void showNextReminder() {
        Reminder chosenReminder = chooseNextReminderToShow();
        if(chosenReminder == null) {
            return;
        }
        showReminderOnScreen(chosenReminder);
    }

    private Reminder chooseNextReminderToShow() {
        Reminder chosenReminder = null;
        for(;shownReminders < reminders.size(); shownReminders ++) {
            if(reminderShouldBeShown(reminders.get(shownReminders))) {
                chosenReminder = reminders.get(shownReminders);
                shownReminders++;
                break;
            }

        }
        return chosenReminder;
    }

    private void showReminderOnScreen(Reminder chosenReminder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setPositiveButton(R.string.reminder_confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                setCurrentReminderConfirmed();
                showNextReminder();
            }
        });
        builder.setNegativeButton(R.string.reminder_remind_later, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                showNextReminder();
            }
        });
        builder.setTitle(chosenReminder.getRemindMessage());
        builder.create().show();
    }

    private boolean reminderShouldBeShown(Reminder reminder) {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        calendar.setTime(reminder.getTimeOfDay());
        return !reminder.isWasConfirmed() && calendar.get(Calendar.HOUR_OF_DAY) <= currentHour;
    }

    private void setCurrentReminderConfirmed() {
        Reminder chosenReminder = reminders.get(shownReminders-1);
        chosenReminder.setWasConfirmed(true);
        persistenceFacade.saveReminder(chosenReminder, activity);
    }
}