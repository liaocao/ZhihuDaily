package com.chenjiayao.zhihudaily.uitls;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;

import com.chenjiayao.zhihudaily.activity.BaseActivity;

/**
 * Created by chen on 2015/11/15.
 */
public class ToolbarUtils {

    public static void initToolbar(BaseActivity activity, Toolbar toolbar, String title) {

        if (null == activity || null == toolbar) {
            return;
        }

        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setHomeButtonEnabled(true);

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
