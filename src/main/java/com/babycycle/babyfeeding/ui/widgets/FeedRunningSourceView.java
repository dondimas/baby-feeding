package com.babycycle.babyfeeding.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.babycycle.babyfeeding.R;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 29/11/13
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
public class FeedRunningSourceView extends LinearLayout{
    public FeedRunningSourceView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.feed_running_source_radio_buttons, this);
    }

    public FeedRunningSourceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FeedRunningSourceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
