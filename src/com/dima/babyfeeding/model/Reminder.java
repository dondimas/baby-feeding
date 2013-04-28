package com.dima.babyfeeding.model;

import com.j256.ormlite.dao.EagerForeignCollection;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: dima
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

    @DatabaseField
    private String remindMessage;

//    @ForeignCollectionField(eager = true)
//    Collection<ReminderTime> reminderTimes;

    @DatabaseField
    private Date timeOfDay;

    public Reminder(String remindMessage, Date timeOfDay) {
        this.remindMessage = remindMessage;
        this.timeOfDay = timeOfDay;
//        this.reminderTimes = new TreeSet<ReminderTime>(reminderTimes);
    }
}
