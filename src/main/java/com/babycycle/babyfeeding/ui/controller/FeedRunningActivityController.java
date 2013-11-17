package com.babycycle.babyfeeding.ui.controller;

import android.view.View;
import android.widget.ImageView;
import com.babycycle.babyfeeding.R;
import com.babycycle.babyfeeding.ui.UIConstants;
import com.babycycle.babyfeeding.ui.activity.FeedRunningActivity;
import com.babycycle.babyfeeding.ui.activity.helpers.TabsCommunicator;
import com.google.inject.Inject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 02/11/13
 * Time: 00:42
 * To change this template use File | Settings | File Templates.
 */
public class FeedRunningActivityController implements View.OnClickListener {

    @Inject
    TabsCommunicator tabsCommunicator;

    private FeedRunningActivity feedRunningActivity;

    private static SimpleDateFormat minutesSecondsFormatter = new SimpleDateFormat(UIConstants.MINUTES_SECONDS_LASTING_FORMAT);


    public void setFeedRunningActivity(FeedRunningActivity feedRunningActivity) {
        this.feedRunningActivity = feedRunningActivity;
    }

    @Override
    public void onClick(View v) {
        setSelectionImageView(feedRunningActivity.getLeftBreastImageView(), R.drawable.lewa_piers_1, false);
        setSelectionImageView(feedRunningActivity.getRightBreastImageView(), R.drawable.prawa_piers_1, false);
        setSelectionImageView(feedRunningActivity.getBottleImageView(), R.drawable.butelka, false);
        switch (v.getId()) {
            case R.id.left_breast:
                setSelectionImageView(feedRunningActivity.getLeftBreastImageView(), R.drawable.lewa_piers_selected_1, true);
                tabsCommunicator.setSelectedSource(TabsCommunicator.FeedSource.LEFT_BREAST);
                break;
            case R.id.right_breast:
                setSelectionImageView(feedRunningActivity.getRightBreastImageView(), R.drawable.prawa_piers_selected_1, true);
                tabsCommunicator.setSelectedSource(TabsCommunicator.FeedSource.RIGHT_BREAST);
                break;
            case R.id.bottle_source:
                setSelectionImageView(feedRunningActivity.getBottleImageView(), R.drawable.butelka_selected, true);
                tabsCommunicator.setSelectedSource(TabsCommunicator.FeedSource.BOTTLE);
                break;
        }
    }

    private void setSelectionImageView(ImageView checkBoxImageView, int drawableResourceId, boolean isSelected) {
        checkBoxImageView.setImageDrawable(feedRunningActivity.getResources().getDrawable(drawableResourceId));
        checkBoxImageView.setSelected(isSelected);
    }

    //we need to run timer to show current time passes since feeding start for case of pausing/resuming
    public void startTiming() {

        Timer timer = new Timer();
        FeedLastingTimerTask myTask = new FeedLastingTimerTask();
        timer.schedule(myTask,0,1000);
    }

    private class FeedLastingTimerTask extends TimerTask {
        @Override
        public void run() {
            updateView();
        }
    }

    private void updateView() {
        if(feedRunningActivity != null)
        feedRunningActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                long timeDelta = Calendar.getInstance(Locale.US).getTimeInMillis() - feedRunningActivity.getStartTime().getTime();
                feedRunningActivity.getRunningFeedTime().setText(minutesSecondsFormatter.format(timeDelta));
            }
        });
    }

}
