package com.babycycle.babyfeeding.ui.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import com.babycycle.babyfeeding.model.FeedDay;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 6/2/13
 * Time: 8:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class StatsListAdapter extends ArrayAdapter<FeedDay> {
    public StatsListAdapter(Context context, int textViewResourceId, List<FeedDay> objects) {
        super(context, textViewResourceId, objects);
    }
}
