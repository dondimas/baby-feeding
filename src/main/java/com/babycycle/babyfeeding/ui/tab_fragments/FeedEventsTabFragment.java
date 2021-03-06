package com.babycycle.babyfeeding.ui.tab_fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.babycycle.babyfeeding.R;
import com.babycycle.babyfeeding.model.FeedEvent;
import com.babycycle.babyfeeding.model.PersistenceFacade;
import com.babycycle.babyfeeding.ui.activity.BabyFeedingTabActivity;
import com.babycycle.babyfeeding.ui.activity.FeedRunningActivity;
import com.babycycle.babyfeeding.ui.activity.helpers.BabyFeedingActivityResultListener;
import com.babycycle.babyfeeding.ui.activity.helpers.TabsCommunicator;
import com.babycycle.babyfeeding.ui.adapter.FeedEventListAdapter;
import com.babycycle.babyfeeding.ui.controller.ClockAppController;
import com.babycycle.babyfeeding.ui.controller.FeedingButtonsPanelViewController;
import com.babycycle.babyfeeding.ui.controller.RemindersController;
import com.babycycle.babyfeeding.ui.tabs_lib.TabFragment;
import com.google.inject.Inject;
import roboguice.inject.InjectView;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 5/4/13
 * Time: 5:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedEventsTabFragment extends TabFragment implements FeedingButtonsPanelViewController.FeedingRunner, BabyFeedingActivityResultListener {
    @Override
    public void setLayoutResourceId() {
        layoutResourceId = R.layout.feed_list;
    }

    @InjectView(R.id.continue_feed_button)
    private Button continueFeeding;

    @InjectView(R.id.finalize_feed_button)
    private Button finalizeFeeding;

    @InjectView(R.id.continue_finalize_container)
    private LinearLayout continueFinalizeButtonLayout;

    @InjectView(R.id.start_feeding_container)
    private LinearLayout startFeedingButtonLayout;

    @InjectView(R.id.last_feeding_time)
    private TextView lastFeedingTime;

    @Inject
    PersistenceFacade persistenceFacade;

    ListView listView;
    FeedEventListAdapter feedEventListAdapter;

    @Inject
    RemindersController remindersController;

    @Inject
    ClockAppController clockAppController;

    FeedingButtonsPanelViewController feedingButtonsPanelViewController;

    Activity activity;

    @Inject
    TabsCommunicator tabsCommunicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        activity = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshListData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        initViews(rootView);
        initListView(rootView);
        initControllers();
        if(activity instanceof BabyFeedingTabActivity)
        ((BabyFeedingTabActivity)activity).setActivityResultListener(this);
        checkFeedEventWasFired();
        return rootView;
    }

    private void checkFeedEventWasFired() {
        FeedEvent runningFeedEvent = persistenceFacade.getRunningFeedEvent(activity);
        if(runningFeedEvent != null) {
            initFeedEventWithStartTime(runningFeedEvent.getStartTime());
            continueFeeding();
        }
    }

    private void initViews(View rootView) {
        continueFeeding = (Button) rootView.findViewById(R.id.continue_feed_button);
        finalizeFeeding = (Button) rootView.findViewById(R.id.finalize_feed_button);
        lastFeedingTime = (TextView) rootView.findViewById(R.id.last_feeding_time);
        startFeedingButtonLayout = (LinearLayout) rootView.findViewById(R.id.start_feeding_container);
        startFeedingButtonLayout.setClickable(true);
        continueFinalizeButtonLayout = (LinearLayout) rootView.findViewById(R.id.continue_finalize_container);
    }
    private void initControllers() {
        remindersController.setActivity(activity);
        clockAppController.setContext(activity);
        feedingButtonsPanelViewController = new FeedingButtonsPanelViewController()
                .setFeedingRunner(this)
                .setContinueFinalizeButtonLayout(continueFinalizeButtonLayout)
                .setStartFeeding(startFeedingButtonLayout)
                .setLastFeedingTime(lastFeedingTime)
                .setContinueFeeding(continueFeeding)
                .setTabsCommunicator(tabsCommunicator)
                .setFinalizeFeeding(finalizeFeeding);
//                .setLeftBreast(leftBreast)
//                .setBottleSource(bottleSource)
//                .setRightBreast(rightBreast);
        feedingButtonsPanelViewController.setLastFeedStartTime(persistenceFacade.getLastFeedStartTime());
    }

    public void continueFeeding() {
        Intent intent = new Intent(activity, FeedRunningActivity.class);
        startActivityForResult(intent, 1);
    }

    private void initListView(View rootView) {
        listView = (ListView) rootView.findViewById(R.id.list_view);
        feedEventListAdapter = new FeedEventListAdapter(activity, R.layout.feed_list_item, new ArrayList<FeedEvent>(0));
        listView.setAdapter(feedEventListAdapter);
        refreshListData();

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

    @Override
    public Activity getRunnerActivity() {
        return activity;
    }

    private void refreshListData() {
        LoadFeedEventsAsyncTask loadFeedEventsAsyncTask = new LoadFeedEventsAsyncTask();
        loadFeedEventsAsyncTask.execute();
    }

    private void persistFeedingData(boolean leftBreastChecked, boolean rightBreastChecked) {
        tabsCommunicator.getCurrentFeedEvent().setLeftBreast(leftBreastChecked);
        tabsCommunicator.getCurrentFeedEvent().setRightBreast(rightBreastChecked);
        tabsCommunicator.setSelectedSource(null);
        persistenceFacade.saveFeedEvent(tabsCommunicator.getCurrentFeedEvent(), activity);
        persistenceFacade.deleteStartedFeedEvent(activity);
    }

    private void initFeedEvent() {
        Calendar calendarActual = Calendar.getInstance(Locale.US);
        FeedEvent feedEvent = new FeedEvent();
        feedEvent.setStartTime(new Date());
        persistenceFacade.persistStartedFeedEvent(feedEvent, activity);
        initFeedEventWithStartTime(calendarActual.getTime());
    }

    private void initFeedEventWithStartTime(Date feedStartTime) {
        FeedEvent currentFeedEvent = new FeedEvent();
        currentFeedEvent.setStartTime(feedStartTime);
        tabsCommunicator.setCurrentFeedEvent(currentFeedEvent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == activity.RESULT_OK){
            setFinishTime();
            feedingButtonsPanelViewController.showFinalizationButtons();
        }
        if (resultCode == activity.RESULT_CANCELED) {

        }
    }

    private void setFinishTime() {
        Calendar calendarActual = Calendar.getInstance(Locale.US);
        tabsCommunicator.getCurrentFeedEvent().setFinishTime(calendarActual.getTime());
    }

//    @Override
//    public void onBackPressed() {
//    }

    class LoadFeedEventsAsyncTask extends AsyncTask<Void,Void,Void> {
        List<FeedEvent> feedEvents;
        @Override
        protected Void doInBackground(Void... params) {
            feedEvents = persistenceFacade.getFeedEventList(activity);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(feedEvents != null) {
                feedEventListAdapter.setFeedEvents(feedEvents);
                feedEventListAdapter.notifyDataSetChanged();
                feedingButtonsPanelViewController.setLastFeedStartTime(persistenceFacade.getLastFeedStartTime());
            }
        }
    }
}
