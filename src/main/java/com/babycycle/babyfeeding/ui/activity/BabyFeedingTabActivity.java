package com.babycycle.babyfeeding.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.babycycle.babyfeeding.R;
import com.babycycle.babyfeeding.ui.activity.helpers.BabyFeedingActivityResultListener;
import com.babycycle.babyfeeding.ui.activity.helpers.BabyFeedingTabActivityTabHelper;
import com.babycycle.babyfeeding.ui.controller.RemindersController;
import com.babycycle.babyfeeding.ui.navigation.ITabHistoryContentStack;
import com.babycycle.babyfeeding.ui.navigation.TabNavigator;
import com.babycycle.babyfeeding.ui.tabs_lib.AbstractTabFragmentActivity;
import com.babycycle.babyfeeding.ui.tabs_lib.FragmentTab;
import com.babycycle.babyfeeding.ui.tabs_lib.TabFragment;
import com.babycycle.babyfeeding.ui.tabs_lib.TabFragmentsEnum;
import com.google.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 5/4/13
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class BabyFeedingTabActivity extends AbstractTabFragmentActivity{

    BabyFeedingTabActivityTabHelper activityTabHelper;

    @Inject
    ITabHistoryContentStack tabHistoryContentStack;

    @Inject
    RemindersController remindersController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        remindersController.setActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        remindersController.showReminders();
    }

    @Override
    public void onTabChanged(String tag) {
        super.onTabChanged(tag);
        remindersController.showReminders();
    }

    public void setActivityResultListener(BabyFeedingActivityResultListener activityResultListener) {
        this.activityResultListener = activityResultListener;
    }

    BabyFeedingActivityResultListener activityResultListener;

    @Override
    protected void setTabContentViewId() {
        tabContentViewId = android.R.id.tabcontent;
    }

    @Override
    protected void setLayoutContentView() {
        setContentView(R.layout.babyfeeding_tab_activity);
    }

    @Override
    protected void initHelpers() {
        activityTabHelper = new BabyFeedingTabActivityTabHelper(tabsMap, mTabHost, this);
    }

    @Override
    protected void initTabClickListeners() {
        activityTabHelper.initTabClickListeners();
    }

    @Override
    protected void openFirstTabToBeShown() {
        changeContentTabOnTabChanged(TabNavigator.APPLICATION_TABS.FEED_EVENTS);
    }

    @Override
    protected void addTabHostTabs(Bundle args) {
        activityTabHelper.addFeedEventsTab();
        activityTabHelper.addRemindersTab();
        activityTabHelper.addStatsTab();
    }

    @Override
    protected void addNewFragment2TabsMapAndHistory(TabFragmentsEnum tab, TabFragmentsEnum superTab, TabFragment newFragment) {
        tabHistoryContentStack.setCurrentFragment(tab);
        FragmentTab fragmentTab = new FragmentTab(tab.getTabTag(), tab.getFragmentType(), null);
        fragmentTab.setFragment(newFragment);
        tabsMap.put(tab.getTabTag(), fragmentTab);
        if (superTab == null) {
            tabHistoryContentStack.setSuperViewFragment(null);
        } else {
            tabHistoryContentStack.setSuperViewFragment(superTab);
        }
    }

    public void changeContentTab(TabFragmentsEnum tab) {
        changeContentTab(tab, null, true, null);
    }

    protected void changeContentTabOnTabChanged(TabNavigator.APPLICATION_TABS applicationTab) {
        changeContentTab(applicationTab);
        onTabChanged(applicationTab.getTabTag());
    }

    public void clearBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(activityResultListener != null) {
            activityResultListener.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
    }
}
