package com.babycycle.babyfeeding.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.babycycle.babyfeeding.R;
import com.babycycle.babyfeeding.model.FeedEvent;
import com.babycycle.babyfeeding.model.Reminder;
import com.babycycle.babyfeeding.ui.activity.ReminderDetailsActivity;
import com.babycycle.babyfeeding.ui.activity.helpers.TabsCommunicator;
import com.google.inject.Inject;
import roboguice.RoboGuice;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: babycycle
 * Date: 4/25/13
 * Time: 1:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReminderListAdapter extends ArrayAdapter<Reminder> {

    Context context;
    private int layoutId;

    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");


    @Inject
    TabsCommunicator tabsCommunicator;

    public void setReminderList(List<Reminder> reminderList) {
        this.reminderList = reminderList;
    }

    List<Reminder> reminderList;

    public ReminderListAdapter(Context context, int layoutId, List<Reminder> feedEvents) {
        super(context, layoutId, feedEvents);
        this.context = context;
        this.reminderList = feedEvents;
        this.layoutId = layoutId;
        injectMembers();
    }

    private void injectMembers() {
        RoboGuice.getInjector(context).injectMembers(this);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);

        ViewHolder holder;
        final Reminder feedEvent = this.reminderList.get(position);
        if (convertView == null || ((ViewHolder) convertView.getTag()).reminderTime == null) {
            convertView = inflater.inflate(this.layoutId, parent, false);
            holder = new ViewHolder();
            holder.itemContainer = (LinearLayout) convertView.findViewById(R.id.item_container);
            holder.reminderTime = (TextView) convertView.findViewById(R.id.reminder_time);
            holder.remindMessage = (TextView) convertView.findViewById(R.id.reminder_message);
            ColorDrawable backgroundDrawable = new ColorDrawable(context.getResources().getColor(R.color.standard_background));
            backgroundDrawable.setAlpha(190);
            holder.itemContainer.setBackgroundDrawable(backgroundDrawable);
            holder.itemClickListener = new ItemClickListener();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        fillViewsWIthData(holder, feedEvent);

        holder.itemClickListener.setPosition(position);
        holder.itemContainer.setOnClickListener(holder.itemClickListener);
        return convertView;
    }

    private void fillViewsWIthData(ViewHolder holder, Reminder reminder) {
        holder.reminderTime.setText(dateFormatter.format(reminder.getTimeOfDay()));
        holder.remindMessage.setText(reminder.getRemindMessage());
    }

    @Override
    public int getCount() {
        return reminderList.size();
    }

    class ViewHolder {
        LinearLayout itemContainer;
        TextView reminderTime;
        TextView remindMessage;
        ItemClickListener itemClickListener;
    }

    class ItemClickListener implements View.OnClickListener {

        private int position;
        @Override
        public void onClick(View v) {
            openReminderDetailsWithReminder(position);
        }

        void setPosition(int position) {
            this.position = position;
        }
    }

    private void openReminderDetailsWithReminder(int position) {
        Reminder itemReminder = reminderList.get(position);
        tabsCommunicator.setReminderForDetails(itemReminder);
        Intent intent = new Intent(context, ReminderDetailsActivity.class);
        ((Activity)context).startActivityForResult(intent, 123);
    }
}
