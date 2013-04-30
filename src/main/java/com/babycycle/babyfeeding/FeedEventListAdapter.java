package com.babycycle.babyfeeding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.babycycle.babyfeeding.model.FeedEvent;

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

    public void setFeedEvents(List<FeedEvent> feedEvents) {
        this.feedEvents = feedEvents;
        for (int i = 0; i < feedEvents.size(); i++) {
            if(i == 0){
                feedEvents.get(0).odd = true;
            } else {
                if(feedEvents.get(i).getStartTime().getTime() - feedEvents.get(i - 1).getFinishTime().getTime() > 600000) {
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
            holder.leftBreast = (TextView) convertView.findViewById(R.id.left_breast);
            holder.rightBreast = (TextView) convertView.findViewById(R.id.right_breast);
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
        holder.leftBreast.setText(feedEvent.isLeftBreast() ? "Left":"-----");
        holder.rightBreast.setText(feedEvent.isRightBreast() ? "Right":"-----");
        if(!feedEvent.odd)
            holder.itemContainer.setBackgroundResource(R.color.item_light_gray);
        else
            holder.itemContainer.setBackgroundResource(R.color.standard_background);
        Date today = new Date();
        if(feedEvent.getFinishTime().getDay() != today.getDay()) {
            holder.fullDate.setText(fullDateFormatter.format(feedEvent.getFinishTime()));
        } else {
            holder.fullDate.setText("");
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
        TextView leftBreast;
        TextView rightBreast;
        public TextView fullDate;
    }
}
