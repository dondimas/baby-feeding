package com.babycycle.babyfeeding.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import com.babycycle.babyfeeding.R;
import com.babycycle.babyfeeding.model.PersistenceFacade;
import com.babycycle.babyfeeding.model.Reminder;
import com.babycycle.babyfeeding.ui.activity.helpers.TabsCommunicator;
import com.google.inject.Inject;
import roboguice.activity.RoboActivity;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: babycycle
 * Date: 4/24/13
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReminderDetailsActivity extends RoboActivity {
    public static final int ADD_REMINDER = 0;
    public static final int EDIT_REMINDER = 1;
    public static final int CLONE_REMINDER = 2;

    EditText reminderMessage;
    TimePicker reminderTime;
    Button submitReminder;
    Button cancelReminderDetails;

    @Inject
    TabsCommunicator tabsCommunicator;

    @Inject
    PersistenceFacade persistenceFacade;

    private int formOpenedState;
    private Reminder currentReminder;
    private int MINIMUM_REMINDER_TEXT_LENGTH = 3;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_details_form);
        initViews();


    }

    private void fillWithDataForEdition() {
        if(tabsCommunicator.getReminderForDetails() != null) {
            currentReminder = tabsCommunicator.getReminderForDetails();
            tabsCommunicator.setReminderForDetails(null);
            reminderMessage.setText(currentReminder.getRemindMessage());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentReminder.getTimeOfDay());
            reminderTime.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            reminderTime.setCurrentMinute(calendar.get(Calendar.MINUTE));
//            submitFeedEvent.setText("Edit reminder");
        }
    }

    private void initViews() {
        reminderMessage = (EditText) findViewById(R.id.reminder_message);
        reminderTime = (TimePicker) findViewById(R.id.reminder_time);
        reminderTime.setIs24HourView(true);
        submitReminder = (Button) findViewById(R.id.submit_reminder_button);
        cancelReminderDetails = (Button) findViewById(R.id.cancel_reminder_details_button);

        submitReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCurrentReminder();
            }
        });

        cancelReminderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToReminderList();
            }
        });

        fillWithDataForEdition();
    }

    private void saveCurrentReminder() {
        if(!validateData()) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, reminderTime.getCurrentHour());
        calendar.set(Calendar.MINUTE, reminderTime.getCurrentMinute());
        if(currentReminder == null) {    //TODO CHANGE TO ENUM
            currentReminder = new Reminder(reminderMessage.getText().toString(), calendar.getTime());
        } else {
            currentReminder.setRemindMessage(reminderMessage.getText().toString());
            currentReminder.setTimeOfDay(calendar.getTime());
        }

        persistenceFacade.saveReminder(currentReminder, this);
        returnToReminderList();
    }

    private void returnToReminderList() {
        finish();
    }

    private boolean validateData() {
        return reminderMessage.getText().toString().length() > MINIMUM_REMINDER_TEXT_LENGTH;
    }


//    @Override
//    public void onBackPressed() {
//    }
}