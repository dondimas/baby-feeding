package com.babycycle.babyfeeding.ui.activity.helpers;

import android.content.Intent;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 5/4/13
 * Time: 6:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BabyFeedingActivityResultListener {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
