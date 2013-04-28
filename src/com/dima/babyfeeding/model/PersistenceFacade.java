package com.dima.babyfeeding.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 4/25/13
 * Time: 1:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class PersistenceFacade {

    List<FeedEvent> feedEventList = new ArrayList<FeedEvent>();

    public void saveFeedEvent(FeedEvent feedEvent, Context context) {
        DatabaseHelper helper = DatabaseHelper.getHelper(context);
        helper.saveFeedEvent(feedEvent);
    }

    public List<FeedEvent> getFeedEventList(Context context) {
        DatabaseHelper helper = DatabaseHelper.getHelper(context);
        feedEventList = helper.getFeedEvents(context);
        return feedEventList;
    }
}
