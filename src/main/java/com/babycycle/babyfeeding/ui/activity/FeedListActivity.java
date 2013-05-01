package com.babycycle.babyfeeding.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import com.babycycle.babyfeeding.ui.adapter.FeedEventListAdapter;
import com.babycycle.babyfeeding.R;
import com.babycycle.babyfeeding.controllers.RemindersController;
import com.babycycle.babyfeeding.model.FeedEvent;
import com.babycycle.babyfeeding.model.PersistenceFacade;
import com.google.inject.Inject;
import roboguice.activity.RoboActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FeedListActivity extends Activity {

    Button startFeed;
    Button continueFeeding;
    Button finalizeFeeding;
    CheckBox leftBreast;
    CheckBox rightBreast;
    public static FeedEvent currentFeedEvent;

    @Inject
    PersistenceFacade persistenceFacade;
    ListView listView;
    FeedEventListAdapter feedEventListAdapter;

    @Inject
    RemindersController remindersController;

    private final String CONTINUE_FEEDING = "Continue Feeding";
    private final String START_FEEDING = "Start Feeding";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.feed_list);
        persistenceFacade = new PersistenceFacade();
        remindersController = new RemindersController();
        remindersController.setActivity(this);
        remindersController.setPersistenceFacade(persistenceFacade);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        remindersController.showReminders();

    }

    private void initViews() {
        startFeed = (Button) findViewById(R.id.start_feed_button);
        startFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initFeedEvent();
                Intent intent = new Intent(FeedListActivity.this, FeedRunningActivity.class);
                startActivityForResult(intent, 1);

            }
        });

        continueFeeding = (Button) findViewById(R.id.continue_feed_button);
        continueFeeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FeedListActivity.this, FeedRunningActivity.class);
                startActivityForResult(intent, 1);

            }
        });

        finalizeFeeding = (Button) findViewById(R.id.finalize_feed_button);
        finalizeFeeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalizeFeeding();

            }
        });
        leftBreast = (CheckBox) findViewById(R.id.left_breast);
        rightBreast = (CheckBox) findViewById(R.id.right_breast);
        listView = (ListView) findViewById(R.id.list_view);
        feedEventListAdapter = new FeedEventListAdapter(this, R.layout.feed_list_item, persistenceFacade.getFeedEventList(this));
        listView.setAdapter(feedEventListAdapter);
    }

    private void finalizeFeeding() {
        currentFeedEvent.setLeftBreast(leftBreast.isChecked());
        currentFeedEvent.setRightBreast(rightBreast.isChecked());
        persistenceFacade.saveFeedEvent(currentFeedEvent, this);

        continueFeeding.setVisibility(View.GONE);
        finalizeFeeding.setVisibility(View.GONE);
        leftBreast.setChecked(false);
        rightBreast.setChecked(false);
        feedEventListAdapter.setFeedEvents(persistenceFacade.getFeedEventList(FeedListActivity.this));
        feedEventListAdapter.notifyDataSetChanged();
    }

    private void initFeedEvent() {
        Calendar calendarActual = Calendar.getInstance(Locale.US);
        currentFeedEvent = new FeedEvent();
        currentFeedEvent.setStartTime(calendarActual.getTime());

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 1) {

            if(resultCode == RESULT_OK){
                setFinishTime();
                continueFeeding.setVisibility(View.VISIBLE);
                finalizeFeeding.setVisibility(View.VISIBLE);
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    private void setFinishTime() {
        Calendar calendarActual = Calendar.getInstance(Locale.US);
        currentFeedEvent.setFinishTime(calendarActual.getTime());
    }

    @Override
    public void onBackPressed() {
    }

}
