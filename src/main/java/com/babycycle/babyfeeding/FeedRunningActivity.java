package com.babycycle.babyfeeding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: babycycle
 * Date: 4/24/13
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedRunningActivity extends Activity {

    TextView runningFeedTime;
    TextView startFeedTime;
    Button finishFeed;
    Button cancelFeeding;
    Date startTime;
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_running);
        startTime = FeedList.currentFeedEvent.getStartTime();
        startTiming();
        initViews();
    }

    private void initViews() {
        runningFeedTime = (TextView) findViewById(R.id.current_feed_time);
        startFeedTime = (TextView) findViewById(R.id.start_feed_time);

        startFeedTime.setText("Started at : " + dateFormatter.format(startTime));
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
                Intent i = getIntent();
                setResult(RESULT_CANCELED, i);
                finish();

            }
        });
    }


    private void startTiming() {

        Timer timer = new Timer();
        MyTimerTask myTask = new MyTimerTask();
        timer.schedule(myTask,0,1000);
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            updateView();
        }
    }

    private void updateView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                long minutes;
                long seconds;
                long timeDelta = Calendar.getInstance(Locale.US).getTimeInMillis() - startTime.getTime();
                timeDelta /= 1000;
                seconds = timeDelta % 60;
                minutes = (timeDelta -seconds)/60;
                runningFeedTime.setText(minutes +":"+seconds);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}