package com.dima.babyfeeding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 4/24/13
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedRunningActivity extends Activity {

    TextView runningFeedTime;
    Button finishFeed;
    Button cancelFeeding;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_running);

        startTiming();
        initViews();
    }

    private void initViews() {
        runningFeedTime = (TextView) findViewById(R.id.current_feed_time);
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

    int minutes;
    int seconds;
    private void startTiming() {

        Timer timer = new Timer();
        MyTimerTask myTask = new MyTimerTask();
        timer.schedule(myTask,0,1000);
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            if(seconds == 50) {
                minutes++;
                seconds = 0;
            } else  {
                seconds++;
            }
            updateView();
        }
    }

    private void updateView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                runningFeedTime.setText(minutes +":"+seconds);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}