package com.dima.babyfeeding.controllers;

import com.dima.babyfeeding.model.Reminder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 4/28/13
 * Time: 6:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class RemindersController {

    private List<Reminder> reminders;

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }
}
