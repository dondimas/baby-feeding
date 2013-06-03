package com.babycycle.babyfeeding.test_utils;

import android.app.Application;
import com.babycycle.babyfeeding.model.FeedEvent;
import com.babycycle.babyfeeding.model.PersistenceFacade;
import com.google.inject.Inject;

import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 6/3/13
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class DBDataCreationHelper {

    PersistenceFacade persistenceFacade;

    Application application;

    public static void createEventsList(PersistenceFacade persistenceFacade, Application application, long maxGapMillis) {
        long minute = 1000*60;
        Calendar calendar = Calendar.getInstance();
        long startTimeMillis0 =  calendar.getTimeInMillis();
        long startTimeMillis =  calendar.getTimeInMillis();
        startTimeMillis -= minute*60*30;
        int amount = 30;
        for(int i = 0; startTimeMillis < startTimeMillis0; i++) {
            calendar.set(Calendar.HOUR_OF_DAY, i +2);
            calendar.setTimeInMillis(startTimeMillis);
            Date startTime = calendar.getTime();
            startTimeMillis += 15*minute;
            calendar.setTimeInMillis(startTimeMillis);
            Date finishTime = calendar.getTime();

            FeedEvent newEvent = new FeedEvent();
            newEvent.setStartTime(startTime);
            newEvent.setFinishTime(finishTime);
            newEvent.setMilkAmount(amount);
            persistenceFacade.saveFeedEvent(newEvent, application);
            if(i%3 == 0) {

                startTimeMillis += maxGapMillis*2;
                amount = 30;
            }
            else {
                startTimeMillis += maxGapMillis/2;
                amount = 31;
            }
        }
    }
}
