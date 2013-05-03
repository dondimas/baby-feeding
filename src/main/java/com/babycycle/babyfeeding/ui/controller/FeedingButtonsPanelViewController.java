package com.babycycle.babyfeeding.ui.controller;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import com.babycycle.babyfeeding.R;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 5/3/13
 * Time: 1:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class FeedingButtonsPanelViewController {

    private Button startFeeding;
    private Button continueFeeding;
    private Button finalizeFeeding;
    private CheckBox leftBreast;
    private CheckBox rightBreast;

    public FeedingButtonsPanelViewController setFeedingRunner(FeedingRunner feedingRunner) {
        this.feedingRunner = feedingRunner;
        return this;
    }

    private FeedingRunner feedingRunner;

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

    public void showFinalizationButtons() {
        continueFeeding.setVisibility(View.VISIBLE);
        finalizeFeeding.setVisibility(View.VISIBLE);
    }

    public interface FeedingRunner {
        void runFeeding();
        void continueFeeding();
        void finalizeFeeding(boolean leftBreastChecked, boolean rightBreastChecked);
    }

    private void unsetOppositeCheckbox(View v) {
        CheckBox oppositeCheckbox = leftBreast;
        if(v.getId() == R.id.left_breast) {
            oppositeCheckbox = rightBreast;
        }
        if(((CheckBox)v).isChecked()) {
            oppositeCheckbox.setChecked(false);
        }
    }
    public void finalizeFeeding() {
        feedingRunner.finalizeFeeding(leftBreast.isChecked(), rightBreast.isChecked());
        continueFeeding.setVisibility(View.GONE);
        finalizeFeeding.setVisibility(View.GONE);
        leftBreast.setChecked(false);
        rightBreast.setChecked(false);


    }
}
