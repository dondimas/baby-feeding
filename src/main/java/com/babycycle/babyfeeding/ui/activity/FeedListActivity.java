//package com.babycycle.babyfeeding.ui.activity;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.ListView;
//import com.babycycle.babyfeeding.ui.activity.helpers.TabsCommunicator;
//import com.babycycle.babyfeeding.ui.adapter.FeedEventListAdapter;
//import com.babycycle.babyfeeding.R;
//import com.babycycle.babyfeeding.ui.controller.ClockAppController;
//import com.babycycle.babyfeeding.ui.controller.FeedingButtonsPanelViewController;
//import com.babycycle.babyfeeding.ui.controller.RemindersController;
//import com.babycycle.babyfeeding.model.FeedEvent;
//import com.babycycle.babyfeeding.model.PersistenceFacade;
//import com.google.inject.Inject;
//import roboguice.activity.RoboActivity;
//import roboguice.inject.InjectView;
//
//import java.util.Calendar;
//import java.util.Locale;
//
//public class FeedListActivity extends RoboActivity implements FeedingButtonsPanelViewController.FeedingRunner{
//
//    @InjectView(R.id.start_feed_button)
//    private Button startFeeding;
//
//    @InjectView(R.id.continue_feed_button)
//    private Button continueFeeding;
//
//    @InjectView(R.id.finalize_feed_button)
//    private Button finalizeFeeding;
//
////    @InjectView(R.id.left_breast)
////    private CheckBox leftBreast;
////
////    @InjectView(R.id.right_breast)
////    private CheckBox rightBreast;
////
////    @InjectView(R.id.bottle_source)
////    private CheckBox settingsButton;
//
//    @Inject
//    PersistenceFacade persistenceFacade;
//
//    ListView listView;
//    FeedEventListAdapter feedEventListAdapter;
//
//    @Inject
//    RemindersController remindersController;
//
//    @Inject
//    ClockAppController clockAppController;
//
//    FeedingButtonsPanelViewController feedingButtonsPanelViewController;
//
//    Activity activity;
//
//    @Inject
//    TabsCommunicator tabsCommunicator;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(null);
//        activity = this;
//        setContentView(R.layout.feed_list);
//        initListView();
//        initControllers();
//    }
//
//    private void initControllers() {
//        remindersController.setActivity(activity);
//        clockAppController.setContext(activity);
//        feedingButtonsPanelViewController = new FeedingButtonsPanelViewController()
//                .setFeedingRunner(this)
//                .setStartFeeding(startFeeding)
//                .setContinueFeeding(continueFeeding)
//                .setFinalizeFeeding(finalizeFeeding);
////                .setLeftBreast(leftBreast)
////                .setBottleSource(leftBreast)
////                .setRightBreast(rightBreast);
//        feedingButtonsPanelViewController.setLastFeedStartTime(persistenceFacade.getLastFeedStartTime());
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        remindersController.showReminders();
//
//    }
//
//    private void initListView() {
//        listView = (ListView) findViewById(R.id.list_view);
//        feedEventListAdapter = new FeedEventListAdapter(this, R.layout.feed_list_item, persistenceFacade.getFeedEventList(this));
//        listView.setAdapter(feedEventListAdapter);
//    }
//
//    @Override
//    public void runFeeding() {
//        initFeedEvent();
//        continueFeeding();
//    }
//
//    public void continueFeeding() {
//        Intent intent = new Intent(this, FeedRunningActivity.class);
//        startActivityForResult(intent, 1);
//    }
//
//    public void finalizeFeeding(boolean leftBreastChecked, boolean rightBreastChecked) {
//        persistFeedingData(leftBreastChecked, rightBreastChecked);
//        refreshListData();
//        clockAppController.openClockAppIfNeed();
//    }
//
//    @Override
//    public Activity getRunnerActivity() {
//        return activity;
//    }
//
//    private void refreshListData() {
//        feedEventListAdapter.setFeedEvents(persistenceFacade.getFeedEventList(FeedListActivity.this));
//        feedEventListAdapter.notifyDataSetChanged();
//        feedingButtonsPanelViewController.setLastFeedStartTime(persistenceFacade.getLastFeedStartTime());
//    }
//
//    private void persistFeedingData(boolean leftBreastChecked, boolean rightBreastChecked) {
//        tabsCommunicator.getCurrentFeedEvent().setLeftBreast(leftBreastChecked);
//        tabsCommunicator.getCurrentFeedEvent().setRightBreast(rightBreastChecked);
//        persistenceFacade.saveFeedEvent(tabsCommunicator.getCurrentFeedEvent(), this);
//    }
//
//    private void initFeedEvent() {
//        Calendar calendarActual = Calendar.getInstance(Locale.US);
//        FeedEvent currentFeedEvent = new FeedEvent();
//        currentFeedEvent.setStartTime(calendarActual.getTime());
//        tabsCommunicator.setCurrentFeedEvent(currentFeedEvent);
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == 1) {
//            if(resultCode == RESULT_OK){
//                setFinishTime();
//                feedingButtonsPanelViewController.showFinalizationButtons();
//            }
//            if (resultCode == RESULT_CANCELED) {
//
//            }
//        }
//    }
//
//    private void setFinishTime() {
//        Calendar calendarActual = Calendar.getInstance(Locale.US);
//        tabsCommunicator.getCurrentFeedEvent().setFinishTime(calendarActual.getTime());
//    }
//
//    @Override
//    public void onBackPressed() {
//    }
//
//}
