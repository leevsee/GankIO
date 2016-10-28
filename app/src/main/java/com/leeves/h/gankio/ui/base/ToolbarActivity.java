package com.leeves.h.gankio.ui.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.leeves.h.gankio.R;

/**
 * Function：
 * Created by h on 2016/10/23.
 *
 * @author Leeves
 */

public abstract class ToolbarActivity extends AppCompatActivity {
    private static final String TAG = ToolbarActivity.class.getSimpleName();

    private Toolbar mToolbar;

    abstract protected int provideContentViewId();

    abstract protected String setToolbarTitle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentViewId());

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mToolbar.setTitle(setToolbarTitle());
        setSupportActionBar(mToolbar);

        if (isCanBack()) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        if (isMaterialMenu()) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }

        }
    }

    public boolean isCanBack() {
        return false;
    }

    public boolean isMaterialMenu() {
        return false;
    }

    public void setTitle(String title) {
        mToolbar.setTitle(setToolbarTitle());
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home && isCanBack()) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        return super.onCreatePanelMenu(featureId, menu);
    }

}

