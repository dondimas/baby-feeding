package com.babycycle.babyfeeding.ui.tab_fragments;

import android.content.Context;
import android.os.AsyncTask;
import com.babycycle.babyfeeding.model.FeedDay;
import com.babycycle.babyfeeding.model.FeedEvent;
import com.babycycle.babyfeeding.model.PersistenceFacade;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 6/2/13
 * Time: 9:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class StatsPresenter {

    @Inject
    PersistenceFacade persistenceFacade;

    Context activity;

    public void refreshListData() {
        //To change body of created methods use File | Settings | File Templates.
    }

    class LoadFeedEventsAsyncTask extends AsyncTask<Void,Void,Void> {
        List<FeedDay> feedDays;


        @Override
        protected Void doInBackground(Void... params) {
            feedDays = pullEventsAndGroupFeedDays();
//            feedDays = persistenceFacade.getFeedEventList(activity);
//            feedDays = persistenceFacade.getFeedEventList(activity);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(feedDays != null) {
//                feedEventListAdapter.setFeedEvents(feedDays);
//                feedEventListAdapter.notifyDataSetChanged();
//                feedingButtonsPanelViewController.setLastFeedStartTime(persistenceFacade.getLastFeedStartTime());
            }
        }
    }

    List<FeedDay> pullEventsAndGroupFeedDays() {
        List<FeedEvent> feedEvents = persistenceFacade.getFeedEventList(activity);
        FeedEvent firstEvent = feedEvents.get(0);
        List<FeedDay> feedDays = new ArrayList<FeedDay>();
        FeedDay currentDay = new FeedDay();
        currentDay.setDay(firstEvent.getStartTime());

        for(FeedEvent event:feedEvents) {
            if(!sameDate(currentDay.getDay(), event.getStartTime())) {
                feedDays.add(currentDay);
                currentDay = new FeedDay();
                currentDay.setDay(event.getStartTime());
            }
            currentDay = addAmount(currentDay, event);
        }
        return feedDays;
    }

    private FeedDay addAmount(FeedDay currentDay, FeedEvent event) {
        if(event.getMilkAmount()%5==0) {
            currentDay.setNatural(currentDay.getNatural() + event.getMilkAmount());
        } else {
            currentDay.setChemical(currentDay.getChemical() + event.getMilkAmount() -1);
        }
        return currentDay;
    }

    private boolean sameDate(Date day, Date startTime) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(day);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(startTime);

        return calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH) && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);

    }
}
