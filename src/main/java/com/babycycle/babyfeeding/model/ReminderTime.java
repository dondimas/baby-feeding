package com.babycycle.babyfeeding.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: babycycle
 * Date: 4/28/13
 * Time: 7:03 PM
 * To change this template use File | Settings | File Templates.
 */
@DatabaseTable(tableName = "reminder_time")
public class ReminderTime {
    public ReminderTime() {
    }

    @DatabaseField(generatedId = true)
    private int id;

//    @DatabaseField(canBeNull = false, foreign = true)
//    Reminder reminder;

    public ReminderTime(Date timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    @DatabaseField
    private Date timeOfDay;
}
