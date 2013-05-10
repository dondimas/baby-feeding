package com.babycycle.babyfeeding.ui.tabs_lib;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TabHost;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dmitri
 * Date: 10/17/12
 * Time: 10:55 PM
 */
public class AbstractTabActivityTabHelper {

    protected Map<String,FragmentTab> tabsMap;
    protected TabHost tabHost;
    protected FragmentActivity activity;

    public AbstractTabActivityTabHelper(Map<String, FragmentTab> tabsMap, TabHost tabHost, FragmentActivity activity) {
        this.tabsMap = tabsMap;
        this.tabHost = tabHost;
        this.activity = activity;
    }

    protected void addTopBarTab(TabFragmentsEnum applicationTab, View indicator, Bundle args) {
        FragmentTab fragmentTab = new FragmentTab(applicationTab.getTabTag(), applicationTab.getFragmentType(), args);
        addTabHostTab(activity, tabHost, tabHost.newTabSpec(applicationTab.getTabTag()).setIndicator(indicator), (fragmentTab));
        tabsMap.put(fragmentTab.getTag(), fragmentTab);
    }


    public void setTab2Focus(String tabTagName) {
        tabHost.setCurrentTabByTag(tabTagName);
        tabHost.getCurrentTabView().requestFocus();
    }
    public void setTab2Focus(TabFragmentsEnum tabEnum) {
        String tabTagName = tabEnum.getTabTag();
        setTab2Focus(tabTagName);
    }


    public void addTabHostTab(FragmentActivity activity, TabHost tabHost, TabHost.TabSpec tabSpec, FragmentTab fragmentTab) {
        // Attach a Tab view factory to the spec
        tabSpec.setContent(new AbstractTabFragmentActivity.TabFactory(activity));
        String tag = tabSpec.getTag();

        // Check to see if we already have a fragment for this tab, probably
        // from a previously saved state.  If so, deactivate it, because our
        // initial state is that a tab isn't shown.
        fragmentTab.setFragment(activity.getSupportFragmentManager().findFragmentByTag(tag));
        if (fragmentTab.getFragment() != null && !fragmentTab.getFragment().isDetached()) {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.detach(fragmentTab.getFragment());
            ft.commit();
            activity.getSupportFragmentManager().executePendingTransactions();
        }

        tabHost.addTab(tabSpec);
    }
}
