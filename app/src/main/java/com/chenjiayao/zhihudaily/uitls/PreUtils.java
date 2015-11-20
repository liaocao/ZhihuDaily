package com.chenjiayao.zhihudaily.uitls;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
        if (context != null) {
            mSharedPreferences = context.getSharedPreferences("pre.xml", Context.MODE_PRIVATE);
            editor = mSharedPreferences.edit();
        }
    }

    public void saveClickItem(String key, boolean value) {
        Log.i("TAG", key + "=========================");
        editor.putBoolean(key, value);
    }

    public boolean isClickItem(String key) {
        Log.i("TAG", key + "++++++++++++++++++++++++++");
        return mSharedPreferences.getBoolean(key, false);
    }
}
