package com.babycycle.babyfeeding.ui.activity;

import android.os.Bundle;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.babycycle.babyfeeding.R;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 01/12/13
 * Time: 01:03
 * To change this template use File | Settings | File Templates.
 */
public abstract class BackButtonActionBarRoboSherlockActivity extends RoboSherlockActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(this.getResources().getString(getActionBarTitleStringResource()));

    }

    protected abstract int getActionBarTitleStringResource();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.menu_update,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                runDoneAction();
                break;
            case android.R.id.home:
                runHomeAction();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }

    protected void runHomeAction() {
        finish();
    }

    protected abstract void runDoneAction();
}
