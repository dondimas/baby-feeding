package com.babycycle.babyfeeding.ui.controller;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import com.babycycle.babyfeeding.R;
import com.babycycle.babyfeeding.model.FeedEvent;
import com.babycycle.babyfeeding.ui.UIConstants;

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

    private Activity activity;

    private Button startFeeding;
    private Button continueFeeding;
    private Button finalizeFeeding;
    private CheckBox leftBreast;
    private CheckBox rightBreast;

    private CheckBox bottleSource;

    private FeedingRunner feedingRunner;

    private Date lastFeedStartTime;

    private static SimpleDateFormat hoursMinutesSecondsFormatter = new SimpleDateFormat(UIConstants.HOURS_MINUTES_SECONDS_LASTING_FORMAT);
//    private static SimpleDateFormat hoursMinutesSecondsFormatter = new SimpleDateFormat("yyyy-MM-DD-kk mm:ss");
    public FeedingButtonsPanelViewController setFeedingRunner(FeedingRunner feedingRunner) {
        this.feedingRunner = feedingRunner;
        return this;
    }



    public FeedingButtonsPanelViewController setStartFeeding(Button startFeed) {
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

    public FeedingButtonsPanelViewController setLeftBreast(CheckBox leftBreast) {
        this.leftBreast = leftBreast;
        this.leftBreast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsetOppositeCheckbox(v);
            }
        });
        return this;
    }

    public FeedingButtonsPanelViewController setRightBreast(CheckBox rightBreast) {
        this.rightBreast = rightBreast;
        this.rightBreast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsetOppositeCheckbox(v);
            }
        });
        return this;
    }

    public FeedingButtonsPanelViewController setBottleSource(CheckBox bottleSource) {
        this.bottleSource = bottleSource;
        this.bottleSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsetOppositeCheckbox(v);
            }
        });
        return this;
    }

    public void showFinalizationButtons() {
        continueFeeding.setVisibility(View.VISIBLE);
        finalizeFeeding.setVisibility(View.VISIBLE);
        startFeeding.setVisibility(View.GONE);
    }

    public void setLastFeedStartTime(Date lastFeedStartTime) {
        this.lastFeedStartTime = lastFeedStartTime;
        startTiming();
    }

    public void updateBreastsWithRunningFeedingEvent(FeedEvent runningFeedEvent) {
        leftBreast.setChecked(runningFeedEvent.isLeftBreast());
        rightBreast.setChecked(runningFeedEvent.isRightBreast());
        bottleSource.setChecked(false);
    }

    public interface FeedingRunner {
        void runFeeding();
        void continueFeeding();
        void finalizeFeeding(boolean leftBreastChecked, boolean rightBreastChecked);
        Activity getRunnerActivity();
    }

    private void unsetOppositeCheckbox(View v) {
        if(!((CheckBox)v).isChecked()) {
            return;
        }
        CheckBox oppositeCheckbox = leftBreast;

        if(v.getId() != R.id.left_breast) {
            leftBreast.setChecked(false);
        }
        if(v.getId() != R.id.right_breast) {
            rightBreast.setChecked(false);
        }
        if(v.getId() != R.id.bottle_source) {
            bottleSource.setChecked(false);
        }
    }
    public void finalizeFeeding() {
        feedingRunner.finalizeFeeding(leftBreast.isChecked(), rightBreast.isChecked());
        continueFeeding.setVisibility(View.GONE);
        finalizeFeeding.setVisibility(View.GONE);
        startFeeding.setVisibility(View.VISIBLE);
        leftBreast.setChecked(false);
        rightBreast.setChecked(false);
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
                startFeeding.setText(getStartFeedingButtonText());
//                Log.e("updateView()", "Current time : "+Calendar.getInstance().getTime());
//                Log.e("updateView()", "Current time : "+hoursMinutesSecondsFormatter.format(Calendar.getInstance().getTime()));
//                Log.e("updateView()", "lastFeedStartTime : "+lastFeedStartTime);
//                Log.e("updateView()", "lastFeedStartTime : "+hoursMinutesSecondsFormatter.format(lastFeedStartTime));
//                Log.e("updateView()", "time delta seconds : "+timeDelta/1000);
//                Log.e("updateView()", "time delta minutes : "+timeDelta/60000);
            }
        });
    }

    public String getStartFeedingButtonText() {
        long timeDelta = Calendar.getInstance().getTimeInMillis() - lastFeedStartTime.getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeDelta);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);

        return "Start Feeding : " + hoursMinutesSecondsFormatter.format(calendar.getTime());
    }
}
