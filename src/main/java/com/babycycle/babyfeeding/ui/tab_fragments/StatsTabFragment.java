package com.babycycle.babyfeeding.ui.tab_fragments;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.babycycle.babyfeeding.R;
import com.babycycle.babyfeeding.model.PersistenceFacade;
import com.babycycle.babyfeeding.ui.adapter.FeedEventListAdapter;
import com.babycycle.babyfeeding.ui.adapter.StatsListAdapter;
import com.babycycle.babyfeeding.ui.controller.ClockAppController;
import com.babycycle.babyfeeding.ui.controller.FeedingButtonsPanelViewController;
import com.babycycle.babyfeeding.ui.controller.RemindersController;
import com.babycycle.babyfeeding.ui.tabs_lib.TabFragment;
import com.google.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 6/2/13
 * Time: 3:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class StatsTabFragment  extends TabFragment {

    @Inject
    PersistenceFacade persistenceFacade;
    @Inject
    StatsPresenter statsPresenter;

    ListView listView;
    StatsListAdapter feedEventListAdapter;


    Activity activity;


    @Override
    public void setLayoutResourceId() {
        layoutResourceId = R.layout.stats_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        activity = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        statsPresenter.refreshListData();
    }

}
