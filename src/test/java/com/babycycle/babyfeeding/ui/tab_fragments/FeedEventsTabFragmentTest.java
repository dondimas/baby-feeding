package com.babycycle.babyfeeding.ui.tab_fragments;

import android.app.Activity;
import com.babycycle.babyfeeding.test_utils.BabyFeedingTestRunner;
import com.babycycle.babyfeeding.test_utils.DebugActivityForFragment;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 5/11/13
 * Time: 1:03 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(BabyFeedingTestRunner.class)
public class FeedEventsTabFragmentTest {

    @Test
    public void testOnCreate() throws Exception {

        DebugActivityForFragment activity = new DebugActivityForFragment();
        activity.onCreate(null);
    }
}
