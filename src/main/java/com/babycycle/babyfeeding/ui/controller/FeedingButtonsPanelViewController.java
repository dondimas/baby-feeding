package com.babycycle.babyfeeding.ui.controller;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.babycycle.babyfeeding.R;
import com.babycycle.babyfeeding.model.FeedEvent;
import com.babycycle.babyfeeding.ui.UIConstants;
import com.babycycle.babyfeeding.ui.activity.helpers.TabsCommunicator;
import com.google.inject.Inject;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 5/3/13
 * Time: 1:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class FeedingButtonsPanelViewController {

    private TabsCommunicator tabsCommunicator;

    private Activity activity;

    private View startFeeding;
    private Button continueFeeding;
    private Button finalizeFeeding;

    private FeedingRunner feedingRunner;

    private Date lastFeedStartTime;

    private static SimpleDateFormat hoursMinutesSecondsFormatter = new SimpleDateFormat(UIConstants.HOURS_MINUTES_SECONDS_LASTING_FORMAT);
    private TextView lastFeedingTime;
    private LinearLayout continueFinalizeButtonLayout;

    public FeedingButtonsPanelViewController setFeedingRunner(FeedingRunner feedingRunner) {
        this.feedingRunner = feedingRunner;
        return this;
    }



    public FeedingButtonsPanelViewController setStartFeeding(View startFeed) {
        this.startFeeding = startFeed;
        startFeeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedingRunner.runFeeding();
            }
        });
        return this;
    }

    public FeedingButtonsPanelViewController setContinueFeeding(Button continueFeeding) {
        this.continueFeeding = continueFeeding;
        this.continueFeeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedingRunner.continueFeeding();

            }
        });
        return this;
    }

    public FeedingButtonsPanelViewController setTabsCommunicator(TabsCommunicator tabsCommunicator) {
        this.tabsCommunicator = tabsCommunicator;
        return this;
    }

    public FeedingButtonsPanelViewController setFinalizeFeeding(Button finalizeFeeding) {
        this.finalizeFeeding = finalizeFeeding;
        this.finalizeFeeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalizeFeeding();
            }
        });
        return this;
    }


    public void showFinalizationButtons() {
        continueFinalizeButtonLayout.setVisibility(View.VISIBLE);
        startFeeding.setVisibility(View.GONE);
    }

    public void setLastFeedStartTime(Date lastFeedStartTime) {
        this.lastFeedStartTime = lastFeedStartTime;
        startTiming();
    }

    public FeedingButtonsPanelViewController setLastFeedingTime(TextView lastFeedingTime) {
        this.lastFeedingTime = lastFeedingTime;
        return this;
    }

    public TextView getLastFeedingTime() {
        return lastFeedingTime;
    }

    public FeedingButtonsPanelViewController setContinueFinalizeButtonLayout(LinearLayout continueFinalizeButtonLayout) {
        this.continueFinalizeButtonLayout = continueFinalizeButtonLayout;
        return this;
    }

    public LinearLayout getContinueFinalizeButtonLayout() {
        return continueFinalizeButtonLayout;
    }

    public interface FeedingRunner {
        void runFeeding();
        void continueFeeding();
        void finalizeFeeding(boolean leftBreastChecked, boolean rightBreastChecked);
        Activity getRunnerActivity();
    }

    public void finalizeFeeding() {
        boolean leftBreastIsChecked = false;
        boolean rightBreastIsChecked = false;
        if(tabsCommunicator.getSelectedSource() == TabsCommunicator.FeedSource.LEFT_BREAST) {
            leftBreastIsChecked = true;
        }
        if(tabsCommunicator.getSelectedSource() == TabsCommunicator.FeedSource.RIGHT_BREAST) {
            rightBreastIsChecked = true;
        }

        feedingRunner.finalizeFeeding(leftBreastIsChecked, rightBreastIsChecked);
        continueFinalizeButtonLayout.setVisibility(View.GONE);
        startFeeding.setVisibility(View.VISIBLE);
    }

    private void startTiming() {

        Timer passedFromFeedingTimeTimer = new Timer();
        FeedLastingTimerTask myTask = new FeedLastingTimerTask();
        passedFromFeedingTimeTimer.schedule(myTask, 0, 1000);
    }

    private class FeedLastingTimerTask extends TimerTask {
        @Override
        public void run() {
            updateView();
        }
    }

    private void updateView() {
        feedingRunner.getRunnerActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lastFeedingTime.setText(getStartFeedingButtonText());
            }
        });
    }

    public String getStartFeedingButtonText() {
        long timeDelta = Calendar.getInstance().getTimeInMillis() - lastFeedStartTime.getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeDelta);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);

        return  hoursMinutesSecondsFormatter.format(calendar.getTime());
    }
}
