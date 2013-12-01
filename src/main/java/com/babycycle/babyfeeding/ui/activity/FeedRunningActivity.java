package com.babycycle.babyfeeding.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.actionbarsherlock.view.MenuItem;
import com.babycycle.babyfeeding.R;
import com.babycycle.babyfeeding.ui.UIConstants;
import com.babycycle.babyfeeding.ui.activity.helpers.TabsCommunicator;
import com.babycycle.babyfeeding.ui.controller.FeedRunningActivityController;
import com.google.inject.Inject;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: babycycle
 * Date: 4/24/13
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedRunningActivity extends BackButtonActionBarRoboSherlockActivity implements View.OnClickListener{


    @InjectView(R.id.current_feed_time)
    private TextView runningFeedTime;
    @InjectView(R.id.start_feed_time)
    private TextView startFeedTime;
    @InjectView(R.id.finish_current_feeding)
    private Button finishFeed;
    @InjectView(R.id.cancel_current_feeding)
    private Button cancelFeeding;
    @InjectView(R.id.left_breast)
    private ImageView leftBreastImageView;
    @InjectView(R.id.right_breast)
    private ImageView rightBreastImageView;
    @InjectView(R.id.bottle_source)
    private ImageView bottleImageView;

    private Date startTime;
    private static SimpleDateFormat hoursMinutesFormatter = new SimpleDateFormat(UIConstants.HOURS_MINUTES_FORMAT);

    @Inject
    TabsCommunicator tabsCommunicator;

    @Inject
    FeedRunningActivityController feedRunningActivityController;

    public void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.pull_in_from_bottom, R.anim.hold);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_running);
        startTime = tabsCommunicator.getCurrentFeedEvent().getStartTime();
        feedRunningActivityController.startTiming();
        initViews();
    }

    @Override
    protected void onResume() {
        feedRunningActivityController.setFeedRunningActivity(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        feedRunningActivityController.setFeedRunningActivity(null);
        overridePendingTransition(R.anim.hold, R.anim.push_out_to_bottom);
        super.onPause();
    }

    public ImageView getBottleImageView() {
        return bottleImageView;
    }

    public ImageView getLeftBreastImageView() {
        return leftBreastImageView;
    }

    public ImageView getRightBreastImageView() {
        return rightBreastImageView;
    }

    private void initViews() {
        leftBreastImageView.setOnClickListener(this);
        rightBreastImageView.setOnClickListener(this);
        bottleImageView.setOnClickListener(this);

        startFeedTime.setText(hoursMinutesFormatter.format(startTime));
        finishFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelFeedingGetBackToList(RESULT_OK);

            }
        });

        cancelFeeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCancelConfirmDialogue();
            }
        });
    }

    private void cancelFeedingGetBackToList(int result) {
        Intent i = getIntent();
        setResult(result, i);
        finish();
    }

    private void openCancelConfirmDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton(R.string.feeding_cancel_confirm_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                cancelFeedingGetBackToList(RESULT_CANCELED);
            }
        });
        builder.setNegativeButton(R.string.feeding_cancel_not_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.setTitle(R.string.feeding_cancel_confirm_cancel_feeding_message);
        builder.create().show();
    }

    @Override
    public void onClick(View v) {
        feedRunningActivityController.onClick(v);
    }

    @Override
    public void onBackPressed() {
    }

    public TextView getRunningFeedTime() {
        return runningFeedTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                openCancelConfirmDialogue();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }
}

