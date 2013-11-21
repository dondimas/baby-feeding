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
import com.babycycle.babyfeeding.ui.UIConstants;
import com.babycycle.babyfeeding.ui.activity.FeedEventDetailsActivity;
import com.babycycle.babyfeeding.ui.activity.helpers.TabsCommunicator;
import com.google.inject.Inject;
import roboguice.RoboGuice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: babycycle
 * Date: 4/25/13
 * Time: 1:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedEventListAdapter extends ArrayAdapter<FeedEvent> {

    Context context;
    private int layoutId;

    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat fullDateFormatter = new SimpleDateFormat("MM-dd");

    @Inject
    TabsCommunicator tabsCommunicator;

    public void setFeedEvents(List<FeedEvent> feedEvents) {
        this.feedEvents = feedEvents;

    }

    List<FeedEvent> feedEvents;

    public FeedEventListAdapter(Context context, int layoutId, List<FeedEvent> feedEvents) {
        super(context, layoutId, feedEvents);
        this.context = context;
        setFeedEvents(feedEvents);
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
//        final FeedEvent feedEvent = this.feedEvents.get(feedEvents.size() - position -1);
        final FeedEvent feedEvent = this.feedEvents.get(position);
        if (convertView == null || ((ViewHolder) convertView.getTag()).startTime == null) {
            convertView = inflater.inflate(this.layoutId, parent, false);
            holder = new ViewHolder();
            holder.itemContainer = (LinearLayout) convertView.findViewById(R.id.item_container);
            holder.startTime = (TextView) convertView.findViewById(R.id.start_time);
            holder.feedingLastedTime = (TextView) convertView.findViewById(R.id.feeding_lasted_time);
            holder.breast = (TextView) convertView.findViewById(R.id.breast);
            holder.amount = (TextView) convertView.findViewById(R.id.amount);
            holder.itemClickListener = new ItemClickListener();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        fillViewsWIthData(holder, feedEvent);

        holder.itemClickListener.setPosition(position);
        convertView.setOnClickListener(holder.itemClickListener);
        return convertView;
    }

    private void fillViewsWIthData(ViewHolder holder, FeedEvent feedEvent) {
        holder.startTime.setText(dateFormatter.format(feedEvent.getStartTime()));
        setFeedingLastedTime(holder, feedEvent);

        String breast = feedEvent.isLeftBreast() ? "Left": (feedEvent.isRightBreast() ? "Right": "Bottle");
        holder.breast.setText(breast);

        setFeedingDate(holder, feedEvent);

        setRowBackground(holder, feedEvent);

    }

    private void setFeedingLastedTime(ViewHolder holder, FeedEvent feedEvent) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(UIConstants.MINUTES_SECONDS_LASTING_FORMAT);
        holder.feedingLastedTime.setText(dateFormatter.format(new Date(feedEvent.getFinishTime().getTime() - feedEvent.getStartTime().getTime())));
    }

    private void setRowBackground(ViewHolder holder, FeedEvent feedEvent) {
        ColorDrawable backgroundDrawable;
        if(!feedEvent.odd)
            backgroundDrawable = new ColorDrawable(context.getResources().getColor(R.color.list_row_dark));
        else
            backgroundDrawable = new ColorDrawable(context.getResources().getColor(R.color.list_row_light));
        backgroundDrawable.setAlpha(190);
        holder.itemContainer.setBackgroundDrawable(backgroundDrawable);
    }

    private void setFeedingDate(ViewHolder holder, FeedEvent feedEvent) {
        if(feedEvent.getMilkAmount() > 0) {
            holder.amount.setText(feedEvent.getMilkAmount() + " ml");

        }else {
            holder.amount.setText("--");
        }

    }

    @Override
    public int getCount() {
        return feedEvents.size();
    }

    class ViewHolder {
        LinearLayout itemContainer;
        TextView startTime;
//        TextView finishTime;
        TextView feedingLastedTime;
        TextView breast;
//        public TextView fullDate;
        public ItemClickListener itemClickListener;
        public TextView amount;
    }

    class ItemClickListener implements View.OnClickListener {

        private int position;
        @Override
        public void onClick(View v) {
            openFeedEventDetails(position);
        }

        void setPosition(int position) {
            this.position = position;
        }
    }

    private void openFeedEventDetails(int position) {
//        FeedEvent itemEvent = feedEvents.get(getCount() - position -1);
        FeedEvent itemEvent = feedEvents.get(position);
        tabsCommunicator.setFeedEventForDetails(itemEvent);
        Intent intent = new Intent(context, FeedEventDetailsActivity.class);
        ((Activity)context).startActivityForResult(intent, 123);
    }
}
