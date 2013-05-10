package com.babycycle.babyfeeding.ui.tabs_lib;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
* Created with IntelliJ IDEA.
* User: dmitri
* Date: 10/12/12
* Time: 2:02 PM
*/
public class FragmentTab {

    public String getTag() {
        return tag;
    }

    private String tag;

    public Class getClss() {
        return clss;
    }

    private Class<? extends TabFragment> clss;

    public Bundle getArgs() {
        return args;
    }

    private Bundle args;

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    private Fragment fragment;

    public FragmentTab(String tag, Class<? extends TabFragment> clazz, Bundle args) {
        this.tag = tag;
        this.clss = clazz;
        this.args = args;
    }

}
