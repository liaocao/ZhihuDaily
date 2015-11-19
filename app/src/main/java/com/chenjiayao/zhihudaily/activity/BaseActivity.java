package com.chenjiayao.zhihudaily.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;

/**
 * Created by chen on 2015/11/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());
        ButterKnife.bind(this);
        initStatusBar();
    }

    private void initStatusBar() {
//        if (Build.VERSION_CODES.KITKAT == Build.VERSION.SDK_INT) {
//            SystemBarTintManager manager = new SystemBarTintManager(this);
//            manager.setStatusBarTintEnabled(true);
//            manager.setNavigationBarTintEnabled(true);
//            manager.setStatusBarTintResource(R.color.colorPrimaryDark);
//        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    protected abstract int getContentLayout();
}
