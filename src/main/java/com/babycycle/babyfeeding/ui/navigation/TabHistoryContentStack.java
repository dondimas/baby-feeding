package com.babycycle.babyfeeding.ui.navigation;

import com.babycycle.babyfeeding.ui.tabs_lib.TabFragmentsEnum;
import com.google.inject.Singleton;

/**
 * Created with IntelliJ IDEA.
 * User: dmitri
 * Date: 10/15/12
 * Time: 12:17 PM
 */

@Singleton
public class TabHistoryContentStack implements ITabHistoryContentStack {

    private TabFragmentsEnum currentFragment;
    private TabFragmentsEnum superViewFragment;

    public TabFragmentsEnum getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(TabFragmentsEnum currentFragment) {
        this.currentFragment = currentFragment;
    }

    public TabFragmentsEnum getSuperViewFragment() {
        return superViewFragment;
    }

    public void setSuperViewFragment(TabFragmentsEnum superViewFragment) {
        this.superViewFragment = superViewFragment;
    }

    public void setViewFragments(TabFragmentsEnum currentFragment, TabFragmentsEnum superViewFragment) {
        this.currentFragment = currentFragment;
        this.superViewFragment = superViewFragment;
    }
}
