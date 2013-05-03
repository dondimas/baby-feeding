package com.babycycle.babyfeeding.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import com.babycycle.babyfeeding.ui.adapter.FeedEventListAdapter;
import com.babycycle.babyfeeding.R;
import com.babycycle.babyfeeding.ui.controller.ClockAppController;
import com.babycycle.babyfeeding.ui.controller.FeedingButtonsPanelViewController;
import com.babycycle.babyfeeding.ui.controller.RemindersController;
import com.babycycle.babyfeeding.model.FeedEvent;
import com.babycycle.babyfeeding.model.PersistenceFacade;
import com.google.inject.Inject;

import java.util.Calendar;
import java.util.Locale;

public class FeedListActivity extends Activity implements FeedingButtonsPanelViewController.FeedingRunner{

    private Button startFeeding;
    private Button continueFeeding;
    private Button finalizeFeeding;
    private CheckBox leftBreast;
    private CheckBox rightBreast;
    public static FeedEvent currentFeedEvent;

    @Inject
    PersistenceFacade persistenceFacade;
    ListView listView;
    FeedEventListAdapter feedEventListAdapter;

    @Inject
    RemindersController remindersController;

    ClockAppController clockAppController;

    FeedingButtonsPanelViewController feedingButtonsPanelViewController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.feed_list);
        persistenceFacade = new PersistenceFacade();
        initViews();
        initControllers();
    }

    private void initControllers() {
        remindersController = new RemindersController();
        remindersController.setActivity(this);
        remindersController.setPersistenceFacade(persistenceFacade);
        clockAppController = new ClockAppController();
        clockAppController.setContext(this);
        feedingButtonsPanelViewController = new FeedingButtonsPanelViewController()
                .setFeedingRunner(this)
                .setStartFeeding(startFeeding)
                .setContinueFeeding(continueFeeding)
                .setFinalizeFeeding(finalizeFeeding)
                .setLeftBreast(leftBreast)
                .setRightBreast(rightBreast);
        feedingButtonsPanelViewController.setLastFeedStartTime(persistenceFacade.getLastFeedStartTime());
    }

    @Override
    protected void onResume() {
        super.onResume();
        remindersController.showReminders();

    }

    private void initViews() {
        startFeeding = (Button) findViewById(R.id.start_feed_button);
        continueFeeding = (Button) findViewById(R.id.continue_feed_button);
        finalizeFeeding = (Button) findViewById(R.id.finalize_feed_button);
        leftBreast = (CheckBox) findViewById(R.id.left_breast);
        rightBreast = (CheckBox) findViewById(R.id.right_breast);
        initListView();
    }

    public void continueFeeding() {
        Intent intent = new Intent(this, FeedRunningActivity.class);
        startActivityForResult(intent, 1);
    }



    private void initListView() {
        listView = (ListView) findViewById(R.id.list_view);
        feedEventListAdapter = new FeedEventListAdapter(this, R.layout.feed_list_item, persistenceFacade.getFeedEventList(this));
        listView.setAdapter(feedEventListAdapter);
    }

    @Override
    public void runFeeding() {
        initFeedEvent();
        continueFeeding();
    }

    public void finalizeFeeding(boolean leftBreastChecked, boolean rightBreastChecked) {
        persistFeedingData(leftBreastChecked, rightBreastChecked);
        refreshListData();
        clockAppController.openClockAppIfNeed();
    }

    private void refreshListData() {
        feedEventListAdapter.setFeedEvents(persistenceFacade.getFeedEventList(FeedListActivity.this));
        feedEventListAdapter.notifyDataSetChanged();
        feedingButtonsPanelViewController.setLastFeedStartTime(persistenceFacade.getLastFeedStartTime());
    }

    private void persistFeedingData(boolean leftBreastChecked, boolean rightBreastChecked) {
        currentFeedEvent.setLeftBreast(leftBreastChecked);
        currentFeedEvent.setRightBreast(rightBreastChecked);
        persistenceFacade.saveFeedEvent(currentFeedEvent, this);
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
                feedingButtonsPanelViewController.showFinalizationButtons();
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
