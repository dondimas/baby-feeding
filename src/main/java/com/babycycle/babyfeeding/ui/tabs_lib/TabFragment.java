package com.babycycle.babyfeeding.ui.tabs_lib;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import roboguice.fragment.RoboFragment;

/**
 * Created with IntelliJ IDEA.
 * User: dmitri
 * Date: 10/10/12
 * Time: 10:45 AM
 */
public abstract class TabFragment extends RoboFragment{

    protected Activity ctx;

    public abstract void setLayoutResourceId();

    protected int layoutResourceId;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        }
        setLayoutResourceId();
        return inflater.inflate(layoutResourceId, container, false);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getActivity();
    }
}
