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
import com.babycycle.babyfeeding.model.FeedDay;
import com.babycycle.babyfeeding.model.Reminder;
import roboguice.RoboGuice;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 6/2/13
 * Time: 8:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class StatsListAdapter extends ArrayAdapter<FeedDay> {

    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd");

    public void setFeedDays(List<FeedDay> feedDays) {
        this.feedDays = feedDays;
    }

    Context context;

    List<FeedDay> feedDays;

    int layoutId;

    public StatsListAdapter(Context context, int layoutId, List<FeedDay> objects) {
        super(context, layoutId, objects);
        feedDays = objects;
        this.context = context;
        this.layoutId = layoutId;
    }

    private void injectMembers() {
        RoboGuice.getInjector(context).injectMembers(this);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);

        ViewHolder holder;
        final FeedDay feedDay = this.feedDays.get(position);
        if (convertView == null || ((ViewHolder) convertView.getTag()).feedDay == null) {
            convertView = inflater.inflate(this.layoutId, parent, false);
            holder = new ViewHolder();
            holder.itemContainer = (LinearLayout) convertView.findViewById(R.id.item_container);
            holder.feedDay = (TextView) convertView.findViewById(R.id.feed_day);
            holder.amountChemical = (TextView) convertView.findViewById(R.id.amount_chemical);
            holder.amountNatural = (TextView) convertView.findViewById(R.id.amount_natural);
            holder.amountTotal = (TextView) convertView.findViewById(R.id.amount_total);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        fillViewsWIthData(holder, feedDay, position);

        return convertView;
    }

    private void fillViewsWIthData(ViewHolder holder, FeedDay feedDay, int position) {
        holder.feedDay.setText(dateFormatter.format(feedDay.getDay()));
        holder.amountNatural.setText(feedDay.getNatural() +" ml");
        holder.amountChemical.setText(feedDay.getChemical()+" ml");
        holder.amountTotal.setText((feedDay.getNatural() + feedDay.getChemical())+" ml");

        ColorDrawable backgroundDrawable;
//        if(position%2 == 0)
            backgroundDrawable = new ColorDrawable(context.getResources().getColor(R.color.actionbar_button_text_disabled));
//        else
//            backgroundDrawable = new ColorDrawable(context.getResources().getColor(R.color.standard_background));
        backgroundDrawable.setAlpha(190);
        holder.itemContainer.setBackgroundDrawable(backgroundDrawable);
    }

    @Override
    public int getCount() {
        return feedDays.size();
    }

    class ViewHolder {
        LinearLayout itemContainer;
        TextView feedDay;
        TextView amountNatural;
        TextView amountChemical;
        TextView amountTotal;
    }
}
