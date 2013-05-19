package com.babycycle.babyfeeding.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import com.babycycle.babyfeeding.R;
import com.babycycle.babyfeeding.model.FeedEvent;
import com.babycycle.babyfeeding.model.PersistenceFacade;
import com.babycycle.babyfeeding.ui.activity.helpers.TabsCommunicator;
import com.google.inject.Inject;
import roboguice.activity.RoboActivity;

import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: babycycle
 * Date: 4/24/13
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedEventDetailsActivity extends RoboActivity {

    EditText milkAmount;
    TimePicker startEventTime;
    TimePicker endEventTime;
    Button submitFeedEvent;
    Button cancelFeedEventDetails;

    @Inject
    TabsCommunicator tabsCommunicator;

    @Inject
    PersistenceFacade persistenceFacade;

    private FeedEvent feedEvent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_event_details_form);
        initViews();
    }

    private void fillWithDataForEdition() {
        if(tabsCommunicator.getFeedEventForDetails() != null) {
            feedEvent = tabsCommunicator.getFeedEventForDetails();
            tabsCommunicator.setReminderForDetails(null);

            setPickerWithTime(feedEvent.getStartTime(), startEventTime);
            setPickerWithTime(feedEvent.getFinishTime(), endEventTime);

            milkAmount.setText(feedEvent.getMilkAmount()+"");
            submitFeedEvent.setText(R.string.update_feed_event_button_text);
        } else {
            setPickerWithTime(new Date(), startEventTime);
            setPickerWithTime(new Date(), endEventTime);
        }
    }

    private Calendar setPickerWithTime(Date date, TimePicker time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        time.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        time.setCurrentMinute(calendar.get(Calendar.MINUTE));
        return calendar;
    }

    private void initViews() {
        milkAmount = (EditText) findViewById(R.id.milk_amount);
        startEventTime = (TimePicker) findViewById(R.id.start_event_time);
        startEventTime.setIs24HourView(true);
        endEventTime = (TimePicker) findViewById(R.id.end_event_time);
        endEventTime.setIs24HourView(true);
        submitFeedEvent = (Button) findViewById(R.id.submit_feed_event_button);
        cancelFeedEventDetails = (Button) findViewById(R.id.cancel_feed_event_details_button);

        submitFeedEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFeedEvent();
            }
        });

        cancelFeedEventDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToFeedEventList();
            }
        });

        fillWithDataForEdition();
    }

    private void saveFeedEvent() {
//        if(!validateData()) {
//            return;
//        }
        if(feedEvent == null) {    //TODO CHANGE TO ENUM
            feedEvent = new FeedEvent();
        }

        feedEvent.setStartTime(getDateFromPicker(startEventTime));
        feedEvent.setFinishTime(getDateFromPicker(endEventTime));
        try {
            feedEvent.setMilkAmount(Integer.parseInt(milkAmount.getText().toString()));
        }catch (Exception e){}


        persistenceFacade.saveFeedEvent(feedEvent, this);
        returnToFeedEventList();
    }

    private Date getDateFromPicker(TimePicker picker) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, picker.getCurrentHour());
        calendar.set(Calendar.MINUTE, picker.getCurrentMinute());
        return calendar.getTime();
    }

    private void returnToFeedEventList() {
        finish();
    }

}