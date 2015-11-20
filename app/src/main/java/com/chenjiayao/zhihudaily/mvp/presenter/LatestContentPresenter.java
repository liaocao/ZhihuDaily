package com.chenjiayao.zhihudaily.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.chenjiayao.zhihudaily.model.Content;
import com.chenjiayao.zhihudaily.mvp.view.LatestContentView;
import com.chenjiayao.zhihudaily.uitls.HttpUtils;
import com.google.gson.Gson;
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

    public Content getContent(int id) {
        final Content[] content = {null};
        if (HttpUtils.isNetworkConnected(mContext)) {
            HttpUtils.get("news/" + id, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    content[0] = parseJson(responseString);
                }
            });
        }
        if (content[0] == null) {
            Log.i("TAG", "=========================");
        }
        return content[0];
    }


    /**
     * 解析从服务器传回来的网页
     *
     * @param responseString
     */
    private Content parseJson(String responseString) {
        Gson gson = new Gson();
        Content content = gson.fromJson(responseString, Content.class);
        return content;

    }
}
