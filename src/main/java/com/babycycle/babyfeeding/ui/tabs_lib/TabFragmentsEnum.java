package com.babycycle.babyfeeding.ui.tabs_lib;

/**
 * Created with IntelliJ IDEA.
 * User: dmitri
 * Date: 10/16/12
 * Time: 11:09 PM
 */
public interface TabFragmentsEnum {
    Class<? extends TabFragment> getFragmentType();
    String getTabTag();
}
