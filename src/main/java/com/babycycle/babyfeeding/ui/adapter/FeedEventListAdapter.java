package com.babycycle.babyfeeding.ui.adapter;

import android.content.Context;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private static final long maxGapOneFeedingMillis = 600000;
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat fullDateFormatter = new SimpleDateFormat("MM-dd");

    public void setFeedEvents(List<FeedEvent> feedEvents) {
        this.feedEvents = feedEvents;
        for (int i = 0; i < feedEvents.size(); i++) {
            if(i == 0){
                feedEvents.get(0).odd = true;
            } else {
                if(feedEvents.get(i).getStartTime().getTime() - feedEvents.get(i - 1).getFinishTime().getTime() > maxGapOneFeedingMillis) {
                    feedEvents.get(i).odd = !feedEvents.get(i - 1).odd;
                } else {
                    feedEvents.get(i).odd = feedEvents.get(i - 1).odd;
                }
            }

        }
    }

    List<FeedEvent> feedEvents;

    public FeedEventListAdapter(Context context, int layoutId, List<FeedEvent> feedEvents) {
        super(context, layoutId, feedEvents);
        this.context = context;
        setFeedEvents(feedEvents);
        this.layoutId = layoutId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);

        ViewHolder holder;
        final FeedEvent feedEvent = this.feedEvents.get(feedEvents.size() - position -1);
        if (convertView == null || ((ViewHolder) convertView.getTag()).startTime == null) {
            convertView = inflater.inflate(this.layoutId, parent, false);
            holder = new ViewHolder();
            holder.itemContainer = (LinearLayout) convertView.findViewById(R.id.item_container);
            holder.startTime = (TextView) convertView.findViewById(R.id.start_time);
            holder.finishTime = (TextView) convertView.findViewById(R.id.finish_time);
            holder.fullDate = (TextView) convertView.findViewById(R.id.full_date);
            holder.feedingLastedTime = (TextView) convertView.findViewById(R.id.feeding_lasted_tima);
            holder.breast = (TextView) convertView.findViewById(R.id.breast);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        fillViewsWIthData(holder, feedEvent);


        return convertView;
    }

    private void fillViewsWIthData(ViewHolder holder, FeedEvent feedEvent) {
        holder.startTime.setText(dateFormatter.format(feedEvent.getStartTime()));
        holder.finishTime.setText(dateFormatter.format(feedEvent.getFinishTime()));
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
            backgroundDrawable = new ColorDrawable(context.getResources().getColor(R.color.actionbar_button_text_disabled));
        else
            backgroundDrawable = new ColorDrawable(context.getResources().getColor(R.color.standard_background));
        backgroundDrawable.setAlpha(190);
        holder.itemContainer.setBackgroundDrawable(backgroundDrawable);
    }

    private void setFeedingDate(ViewHolder holder, FeedEvent feedEvent) {
        Calendar calendar = Calendar.getInstance();
        int todayDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(feedEvent.getFinishTime());
        if(calendar.get(Calendar.DAY_OF_MONTH) != todayDay) {
            holder.fullDate.setText(fullDateFormatter.format(feedEvent.getFinishTime()));
        } else {
            holder.fullDate.setText(R.string.today);
        }
    }

    @Override
    public int getCount() {
        return feedEvents.size();
    }

    class ViewHolder {
        LinearLayout itemContainer;
        TextView startTime;
        TextView finishTime;
        TextView feedingLastedTime;
        TextView breast;
        public TextView fullDate;
    }
}
