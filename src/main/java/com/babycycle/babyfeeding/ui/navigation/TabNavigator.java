package com.babycycle.babyfeeding.ui.navigation;

import com.babycycle.babyfeeding.ui.tab_fragments.FeedEventsTabFragment;
import com.babycycle.babyfeeding.ui.tab_fragments.RemindersTabFragment;
import com.babycycle.babyfeeding.ui.tabs_lib.TabFragment;
import com.babycycle.babyfeeding.ui.tabs_lib.TabFragmentsEnum;

/**
 * Created with IntelliJ IDEA.
 * User: dmitri
 * Date: 11/15/12
 * Time: 5:15 PM
 */
public class TabNavigator {

    public static enum APPLICATION_TABS implements TabFragmentsEnum {
        FEED_EVENTS(FeedEventsTabFragment.class)
       , REMINDERS(RemindersTabFragment.class)
       ;

        private Class<? extends TabFragment> fragmentType;

        private final String tabTag;

        private APPLICATION_TABS(Class<? extends TabFragment> fragmentType) {
            this.fragmentType = fragmentType;
            this.tabTag = name();
        }

        private APPLICATION_TABS(Class<? extends TabFragment> fragmentType, String tabTag) {
            this.fragmentType = fragmentType;
            this.tabTag = tabTag;
        }

        public Class<? extends TabFragment> getFragmentType() {
            return fragmentType;
        }

        public String getTabTag() {
            return tabTag;
        }
    }
}
