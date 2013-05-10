package com.babycycle.babyfeeding.ui.navigation;


import com.babycycle.babyfeeding.ui.tabs_lib.TabFragmentsEnum;

/**
 * Created with IntelliJ IDEA.
 * User: dmitri
 * Date: 10/14/12
 * Time: 3:16 PM
 */
public interface IViewWithChildrenNavigator {
    boolean isInRootView();
    void switch2UpperView();
    void onSwitch2RootView();
    void switch2RootView();
    void openSuperView();
    TabFragmentsEnum getRootTabEnum();
}
