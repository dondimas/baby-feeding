package com.babycycle.babyfeeding.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: babycycle
 * Date: 4/28/13
 * Time: 7:01 PM
 * To change this template use File | Settings | File Templates.
 */
@DatabaseTable(tableName = "reminder")
public class Reminder {

    public Reminder() {
    }

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = true, columnName = "remind_message")
    private String remindMessage;

//    @ForeignCollectionField(eager = true)
//    Collection<ReminderTime> reminderTimes;

    public Date getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(Date timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public String getRemindMessage() {
        return remindMessage;
    }

    public void setRemindMessage(String remindMessage) {
        this.remindMessage = remindMessage;
    }

    @DatabaseField(canBeNull = true, columnName = "time_of_day")
    private Date timeOfDay;

    @DatabaseField(columnName = "was_confirmed")
    private boolean wasConfirmed;

    public Reminder(String remindMessage, Date timeOfDay) {
        this.remindMessage = remindMessage;
        this.timeOfDay = timeOfDay;
//        this.reminderTimes = new TreeSet<ReminderTime>(reminderTimes);
    }

    public boolean isWasConfirmed() {
        return wasConfirmed;
    }

    public void setWasConfirmed(boolean wasConfirmed) {
        this.wasConfirmed = wasConfirmed;
    }
}
