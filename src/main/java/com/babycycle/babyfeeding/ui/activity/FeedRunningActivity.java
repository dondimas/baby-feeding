package com.babycycle.babyfeeding.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.babycycle.babyfeeding.R;
import com.babycycle.babyfeeding.ui.UIConstants;
import com.babycycle.babyfeeding.ui.activity.helpers.TabsCommunicator;
import com.google.inject.Inject;
import roboguice.activity.RoboActivity;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: babycycle
 * Date: 4/24/13
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedRunningActivity extends RoboActivity {

    TextView runningFeedTime;
    TextView startFeedTime;
    Button finishFeed;
    Button cancelFeeding;
    Date startTime;
    private static SimpleDateFormat hoursMinutesFormatter = new SimpleDateFormat(UIConstants.HOURS_MINUTES_FORMAT);
    private static SimpleDateFormat minutesSecondsFormatter = new SimpleDateFormat(UIConstants.MINUTES_SECONDS_LASTING_FORMAT);

    @Inject
    TabsCommunicator tabsCommunicator;

    public void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.pull_in_from_bottom, R.anim.hold);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_running);
        startTime = tabsCommunicator.getCurrentFeedEvent().getStartTime();
        startTiming();
        initViews();
    }

    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.hold, R.anim.push_out_to_bottom);
        super.onPause();
    }

    private void initViews() {
        runningFeedTime = (TextView) findViewById(R.id.current_feed_time);
        startFeedTime = (TextView) findViewById(R.id.start_feed_time);

        startFeedTime.setText("Started at : " + hoursMinutesFormatter.format(startTime));
        finishFeed = (Button) findViewById(R.id.finish_current_feeding);
        finishFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = getIntent();
                setResult(RESULT_OK, i);
                finish();

            }
        });

        cancelFeeding = (Button) findViewById(R.id.cancel_current_feeding);
        cancelFeeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCancelConfirmDialogue();


            }
        });
    }

    private void openCancelConfirmDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton(R.string.feeding_cancel_confirm_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent i = getIntent();
                setResult(RESULT_CANCELED, i);
                finish();
            }
        });
        builder.setNegativeButton(R.string.feeding_cancel_not_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.setTitle(R.string.feeding_cancel_confirm_cancel_feeding_message);
        builder.create().show();
    }


    private void startTiming() {

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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                long timeDelta = Calendar.getInstance(Locale.US).getTimeInMillis() - startTime.getTime();
                runningFeedTime.setText(minutesSecondsFormatter.format(timeDelta));
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}