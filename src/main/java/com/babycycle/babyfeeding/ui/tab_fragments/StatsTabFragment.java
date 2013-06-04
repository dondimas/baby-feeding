package com.babycycle.babyfeeding.ui.tab_fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.babycycle.babyfeeding.R;
import com.babycycle.babyfeeding.model.FeedDay;
import com.babycycle.babyfeeding.model.PersistenceFacade;
import com.babycycle.babyfeeding.ui.adapter.StatsListAdapter;
import com.babycycle.babyfeeding.ui.tabs_lib.TabFragment;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

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
    StatsListAdapter feedDayListAdapter;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        statsPresenter.setView(this);
        initListView(rootView);
        return rootView;
    }

    private void initListView(View rootView) {
        listView = (ListView) rootView.findViewById(R.id.stats_list_view);
        feedDayListAdapter = new StatsListAdapter(activity, R.layout.feed_day_list_item, new ArrayList<FeedDay>(0));
        listView.setAdapter(feedDayListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        statsPresenter.refreshListData();
    }

    public void showUpdatedDaysList(List<FeedDay> feedDays) {
        feedDayListAdapter.setFeedDays(feedDays);
        feedDayListAdapter.notifyDataSetChanged();
    }
}
