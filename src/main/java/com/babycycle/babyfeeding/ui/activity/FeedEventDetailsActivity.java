package com.babycycle.babyfeeding.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import com.actionbarsherlock.app.ActionBar;
import com.babycycle.babyfeeding.R;
import com.babycycle.babyfeeding.model.FeedEvent;
import com.babycycle.babyfeeding.model.PersistenceFacade;
import com.babycycle.babyfeeding.ui.activity.helpers.TabsCommunicator;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
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
public class FeedEventDetailsActivity extends BackButtonActionBarRoboSherlockActivity {

    EditText milkAmount;
    TimePicker startEventTime;
    TimePicker endEventTime;
    Button submitFeedEvent;
    Button cancelFeedEventDetails;
    View screenContainer;

    @Inject
    TabsCommunicator tabsCommunicator;

    @Inject
    PersistenceFacade persistenceFacade;

    private FeedEvent feedEvent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
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
        milkAmount.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                {
                    InputMethodManager manager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    manager.hideSoftInputFromWindow(milkAmount.getWindowToken(), 0);
                }
                return false;
            }
        });
        submitFeedEvent = (Button) findViewById(R.id.submit_feed_event_button);
        cancelFeedEventDetails = (Button) findViewById(R.id.cancel_feed_event_details_button);
        screenContainer = findViewById(R.id.container_event_details);

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

        screenContainer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeClaviIfOpened();
            }
        });
        initTimePickers();
        fillWithDataForEdition();
    }

    private void initTimePickers() {
        startEventTime = (TimePicker) findViewById(R.id.start_event_time);
        startEventTime.setIs24HourView(true);
        endEventTime = (TimePicker) findViewById(R.id.end_event_time);
        endEventTime.setIs24HourView(true);

    }

    private void validateTimes() {
        if(endEventTimeBeforeStartEventTime()) {
            int hour = startEventTime.getCurrentHour();
            int minute = startEventTime.getCurrentMinute();
            endEventTime.setCurrentHour(hour);
            endEventTime.setCurrentMinute(minute);
        }
    }

    private boolean endEventTimeBeforeStartEventTime() {
        return ( endEventTime.getCurrentMinute() < startEventTime.getCurrentMinute()
                && endEventTime.getCurrentHour() == startEventTime.getCurrentHour() )
                || endEventTime.getCurrentHour() < startEventTime.getCurrentHour();
    }

    private void saveFeedEvent() {
        validateTimes();
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

    public void closeClaviIfOpened() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}