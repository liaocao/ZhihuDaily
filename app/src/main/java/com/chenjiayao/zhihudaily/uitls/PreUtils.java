package com.chenjiayao.zhihudaily.uitls;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chen on 2015/11/20.
 */
public class PreUtils {

    static PreUtils utils;

    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor editor;


    public static PreUtils getInstance(Context context) {
        if (null == utils) {
            synchronized (PreUtils.class) {
                utils = new PreUtils(context);

            }
        }
        return utils;
    }


    private PreUtils(Context context) {
        mSharedPreferences = context.getSharedPreferences("pre.xml", Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public void saveClickItem(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean isClickItem(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }
}
