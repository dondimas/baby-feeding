package com.babycycle.babyfeeding.test_utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.babycycle.babyfeeding.ui.tab_fragments.FeedEventsTabFragment;
import roboguice.activity.RoboFragmentActivity;

/**
 * Created with IntelliJ IDEA.
 * User: dmitri
 * Date: 11/27/12
 * Time: 11:39 AM
 */
public class DebugActivityForFragment extends RoboFragmentActivity {
    private static final int CONTENT_VIEW_ID = 10101010;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frame = new FrameLayout(this);
        frame.setId(CONTENT_VIEW_ID);
        setContentView(frame, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


            Fragment newFragment = new FeedEventsTabFragment();
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.add(CONTENT_VIEW_ID, newFragment).commit();

    }

}
