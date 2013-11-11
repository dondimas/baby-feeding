package com.babycycle.babyfeeding.ui.activity.helpers;

import com.babycycle.babyfeeding.model.FeedEvent;
import com.babycycle.babyfeeding.model.Reminder;
import com.google.inject.Singleton;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 5/4/13
 * Time: 7:32 PM
 * To change this template use File | Settings | File Templates.
 */

@Singleton
public class TabsCommunicator {
    public enum FeedSource {
        LEFT_BREAST, RIGHT_BREAST, BOTTLE
    }
    private FeedEvent currentFeedEvent;

    private Reminder reminderForDetails;

    private FeedEvent feedEventForDetails;

    private FeedSource selectedSource;

    public FeedEvent getCurrentFeedEvent() {
        return currentFeedEvent;
    }

    public void setCurrentFeedEvent(FeedEvent currentFeedEvent) {
        this.currentFeedEvent = currentFeedEvent;
    }

    public Reminder getReminderForDetails() {
        return reminderForDetails;
    }

    public void setReminderForDetails(Reminder reminderForDetails) {
        this.reminderForDetails = reminderForDetails;
    }

    public void setFeedEventForDetails(FeedEvent feedEventForDetails) {
        this.feedEventForDetails = feedEventForDetails;
    }

    public FeedEvent getFeedEventForDetails() {
        return feedEventForDetails;
    }

    public FeedSource getSelectedSource() {
        return selectedSource;
    }

    public void setSelectedSource(FeedSource selectedSource) {
        this.selectedSource = selectedSource;
    }
}
