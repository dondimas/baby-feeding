package com.babycycle.babyfeeding.ui.activity.helpers;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TabHost;
import com.babycycle.babyfeeding.R;
import com.babycycle.babyfeeding.ui.activity.BabyFeedingTabActivity;
import com.babycycle.babyfeeding.ui.navigation.TabNavigator;
import com.babycycle.babyfeeding.ui.tabs_lib.AbstractTabActivityTabHelper;
import com.babycycle.babyfeeding.ui.tabs_lib.FragmentTab;
import com.babycycle.babyfeeding.ui.tabs_lib.Tab;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 5/4/13
 * Time: 5:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class BabyFeedingTabActivityTabHelper extends AbstractTabActivityTabHelper {

    int currentTabIndexBeingAdd = 0;
    int FEED_EVENTS_TAB_INDEX;
    int REMINDERS_TAB_INDEX;
    BabyFeedingTabActivity babyFeedingTabActivity;

    public BabyFeedingTabActivityTabHelper(Map<String, FragmentTab> tabsMap, TabHost tabHost, BabyFeedingTabActivity activity) {
        super(tabsMap, tabHost, activity);
        babyFeedingTabActivity = activity;
    }

    public void addFeedEventsTab() {
        FEED_EVENTS_TAB_INDEX = currentTabIndexBeingAdd++;
        Tab tab = new Tab(babyFeedingTabActivity, R.drawable.tab_feed_event_states, R.string.tab_feed_events_tabname);
        addTopBarTab(TabNavigator.APPLICATION_TABS.FEED_EVENTS, tab, null);
    }

    public void addRemindersTab() {
        REMINDERS_TAB_INDEX = currentTabIndexBeingAdd++;
        Tab tab = new Tab(babyFeedingTabActivity, R.drawable.tab_reminders_states, R.string.tab_reminders_tabname);
        addTopBarTab(TabNavigator.APPLICATION_TABS.REMINDERS, tab, null);
    }

    public void fixUI() {
        tabHost.getTabWidget().getChildAt(REMINDERS_TAB_INDEX).performClick();
        tabHost.getTabWidget().getChildAt(FEED_EVENTS_TAB_INDEX).performClick();
        tabHost.getTabWidget().getChildAt(REMINDERS_TAB_INDEX).performClick();
        tabHost.getTabWidget().getChildAt(FEED_EVENTS_TAB_INDEX).performClick();

    }
    public void initTabClickListeners() {

        tabHost.getTabWidget().getChildAt(FEED_EVENTS_TAB_INDEX).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (tabHost.getCurrentTabTag().equals(TabNavigator.APPLICATION_TABS.FEED_EVENTS.getTabTag())) {
                    return;
                }
                babyFeedingTabActivity.clearBackStack();
                babyFeedingTabActivity.changeContentTab(TabNavigator.APPLICATION_TABS.FEED_EVENTS);
                setTab2Focus(TabNavigator.APPLICATION_TABS.FEED_EVENTS);
            }
        });

        tabHost.getTabWidget().getChildAt(REMINDERS_TAB_INDEX).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (tabHost.getCurrentTabTag().equals(TabNavigator.APPLICATION_TABS.REMINDERS.getTabTag())) {
                    return;
                }
                babyFeedingTabActivity.clearBackStack();
                babyFeedingTabActivity.changeContentTab(TabNavigator.APPLICATION_TABS.REMINDERS);
                setTab2Focus(TabNavigator.APPLICATION_TABS.REMINDERS);
            }
        });

    }
}
