package com.chenjiayao.zhihudaily.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.chenjiayao.zhihudaily.constant;
import com.chenjiayao.zhihudaily.mvp.view.LatestContentView;
import com.chenjiayao.zhihudaily.uitls.HttpUtils;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by chen on 2015/11/20.
 */
public class LatestContentPresenter {

    LatestContentView mLatestContentView;
    Context mContext;

    public LatestContentPresenter(LatestContentView mLatestContentView, Context context) {
        this.mLatestContentView = mLatestContentView;
        this.mContext = context;
    }


    public void getJson(final int id) {
        if (HttpUtils.isNetworkConnected(mContext)) {
            HttpUtils.get("news/" + id, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    responseString = responseString.replaceAll("'", "''");
                    mLatestContentView.showContent(responseString);
                }
            });
        }
    }
}
