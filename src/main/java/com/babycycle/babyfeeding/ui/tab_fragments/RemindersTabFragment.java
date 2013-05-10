package com.babycycle.babyfeeding.ui.tab_fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import com.babycycle.babyfeeding.R;
import com.babycycle.babyfeeding.model.PersistenceFacade;
import com.babycycle.babyfeeding.model.Reminder;
import com.babycycle.babyfeeding.ui.activity.ReminderDetailsActivity;
import com.babycycle.babyfeeding.ui.adapter.ReminderListAdapter;
import com.babycycle.babyfeeding.ui.tabs_lib.TabFragment;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 5/4/13
 * Time: 5:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class RemindersTabFragment extends TabFragment {

    ListView listView;

    ReminderListAdapter reminderListAdapter;

    @Inject
    PersistenceFacade persistenceFacade;

    Activity activity;
    private Button addReminderButton;

    @Override
    public void setLayoutResourceId() {
        layoutResourceId = R.layout.reminder_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        initListView(rootView);
        initViews(rootView);
        activity = getActivity();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshListData();
    }

    private void initViews(View rootView) {
        addReminderButton = (Button) rootView.findViewById(R.id.add_reminder_button);
        addReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ReminderDetailsActivity.class);
                activity.startActivityForResult(intent, 123);
            }
        });
    }

    private void initListView(View rootView) {
        listView = (ListView) rootView.findViewById(R.id.list_view);
        reminderListAdapter = new ReminderListAdapter(getActivity(), R.layout.reminder_list_item, new ArrayList<Reminder>(0));
        listView.setAdapter(reminderListAdapter);

    }

    private void refreshListData() {
        LoadRemindersAsyncTask loadRemindersAsyncTask = new LoadRemindersAsyncTask();
        loadRemindersAsyncTask.execute();
    }

    class LoadRemindersAsyncTask extends AsyncTask<Void,Void,Void> {
        List<Reminder> feedEvents;
        @Override
        protected Void doInBackground(Void... params) {
            feedEvents = persistenceFacade.getReminders(activity);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(feedEvents != null) {
                reminderListAdapter.setReminderList(feedEvents);
                reminderListAdapter.notifyDataSetChanged();
            }
        }
    }
}
