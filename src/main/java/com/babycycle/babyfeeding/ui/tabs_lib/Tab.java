package com.babycycle.babyfeeding.ui.tabs_lib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.babycycle.babyfeeding.R;

/**
 * Created with IntelliJ IDEA.
 * User: Tomasz Morcinek
 * Date: 30.11.12
 * Time: 16:52
 * To change this template use File | Settings | File Templates.
 */
public class Tab extends LinearLayout{

    public Tab(Context context, int imageRes, int textRes) {
        super(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.tab, this);
        ((ImageView) view.findViewById(R.id.tab_image)).setImageResource(imageRes);
        ((TextView) view.findViewById(R.id.tab_text)).setText(textRes);
    }
}
