package com.babycycle.babyfeeding.model;

import android.content.Context;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: babycycle
 * Date: 4/25/13
 * Time: 1:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Singleton
public class PersistenceFacade {

    List<FeedEvent> feedEventList = new ArrayList<FeedEvent>();
    private List<Reminder> reminders;

    public void saveFeedEvent(FeedEvent feedEvent, Context context) {
        DatabaseHelper.getHelper(context).saveFeedEvent(feedEvent);
    }


    public void saveReminder(Reminder reminder, Context context) {
        DatabaseHelper.getHelper(context).saveReminder(reminder);
    }

    public List<FeedEvent> getFeedEventList(Context context) {
        feedEventList = DatabaseHelper.getHelper(context).getFeedEvents(context);
        return feedEventList;
    }

    public List<Reminder> getReminders(Context context) {
        reminders = DatabaseHelper.getHelper(context).getReminders(context);
        return reminders;
    }
}
