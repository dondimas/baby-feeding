package com.babycycle.babyfeeding.ui.tabs_lib;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TabHost;
import roboguice.activity.RoboFragmentActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dmitri
 * Date: 10/10/12
 * Time: 3:04 PM
 */
public abstract class AbstractTabFragmentActivity extends RoboFragmentActivity implements TabHost.OnTabChangeListener {

    protected String ACTIVE_TAB_BUNDLE_PARAM = "ACTIVE_TAB_BUNDLE_PARAM";

    protected FragmentTab mLastFragmentTab = null;

    protected final Map<String, FragmentTab> tabsMap = new HashMap<String, FragmentTab>();

    protected TabHost mTabHost;
    protected int tabContentViewId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutContentView();
        setTabContentViewId();
        initialiseTabHostTabHelper(savedInstanceState);
        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString(ACTIVE_TAB_BUNDLE_PARAM));
        }
    }

    protected abstract void setTabContentViewId();

    protected abstract void setLayoutContentView();

    protected void initialiseTabHostTabHelper(Bundle args) {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);

        mTabHost.setup();
        mTabHost.clearAllTabs();


        initHelpers();

        addTabHostTabs(args);

        initTabClickListeners();

        openFirstTabToBeShown();

        mTabHost.setOnTabChangedListener(this);
    }


    protected abstract void initHelpers();

    protected abstract void initTabClickListeners();

    protected abstract void openFirstTabToBeShown();

    protected abstract void addTabHostTabs(Bundle args);

    protected void setTab2FocusByTabHost(TabFragmentsEnum tabEnum) {
        mTabHost.setCurrentTabByTag(tabEnum.getTabTag());
        mTabHost.getCurrentTabView().requestFocus();
    }

    public void onTabChanged(String tag) {
        FragmentTab newFragmentTab = this.tabsMap.get(tag);
        if (mLastFragmentTab != newFragmentTab) {
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            if (mLastFragmentTab != null) {
                if (mLastFragmentTab.getFragment() != null) {
                    ft.detach(mLastFragmentTab.getFragment());
                }
            }
            if (newFragmentTab != null) {
                if (newFragmentTab.getFragment() == null) {
                    newFragmentTab.setFragment(Fragment.instantiate(this,
                            newFragmentTab.getClss().getName(), newFragmentTab.getArgs()));
                    ft.add(tabContentViewId, newFragmentTab.getFragment(), newFragmentTab.getTag());
                } else {
                    ft.attach(newFragmentTab.getFragment());
                }
            }

            mLastFragmentTab = newFragmentTab;
            ft.commit();
            this.getSupportFragmentManager().executePendingTransactions();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(ACTIVE_TAB_BUNDLE_PARAM, mTabHost.getCurrentTabTag()); //save the tab selected
        super.onSaveInstanceState(outState);
    }

    public void changeContentTab(TabFragmentsEnum tab, TabFragmentsEnum superTab, boolean addToBackStack, Bundle fragmentStatus) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        try {
            Fragment newFragment = null;
            if( fragmentStatus != null) {
                newFragment = tab.getFragmentType().newInstance();
                newFragment.setArguments(fragmentStatus);
            } else {
                newFragment = this.getSupportFragmentManager().findFragmentByTag(tab.getTabTag());
                if (newFragment == null) {
                    newFragment = tab.getFragmentType().newInstance();
                }
            }

            ft.replace(tabContentViewId, newFragment, tab.getTabTag());
            if (addToBackStack) {
                ft.addToBackStack(tab.getTabTag());
            }
            addNewFragment2TabsMapAndHistory(tab, superTab, (TabFragment) newFragment);
        } catch (Exception e) {
            new RuntimeException(e);
        }
        ft.commit();

    }

    public void changeContentTab(TabFragmentsEnum tab, TabFragmentsEnum superTab) {
        changeContentTab(tab,superTab,false,null);
    }

    protected abstract void addNewFragment2TabsMapAndHistory(TabFragmentsEnum tab, TabFragmentsEnum superTab, TabFragment newFragment);


    public static class TabFactory implements TabHost.TabContentFactory {

        private final Context mContext;

        public TabFactory(Context context) {
            mContext = context;
        }

        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }

    }

}
