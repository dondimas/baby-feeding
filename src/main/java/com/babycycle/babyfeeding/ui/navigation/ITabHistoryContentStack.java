package com.babycycle.babyfeeding.ui.navigation;


import com.babycycle.babyfeeding.ui.tabs_lib.TabFragmentsEnum;

/**
 * Created with IntelliJ IDEA.
 * User: dmitri
 * Date: 10/14/12
 * Time: 3:15 PM
 */
public interface ITabHistoryContentStack{


    TabFragmentsEnum getCurrentFragment();

    void setCurrentFragment(TabFragmentsEnum currentFragment);

    TabFragmentsEnum getSuperViewFragment();

    void setSuperViewFragment(TabFragmentsEnum superViewFragment);

    void setViewFragments(TabFragmentsEnum currentFragment, TabFragmentsEnum superViewFragment);
}
