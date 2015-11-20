package com.chenjiayao.zhihudaily.mvp.presenter;

import android.content.Context;

import com.chenjiayao.zhihudaily.constant;
import com.chenjiayao.zhihudaily.model.LatestNews;
import com.chenjiayao.zhihudaily.mvp.view.MainView;
import com.chenjiayao.zhihudaily.uitls.HttpUtils;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by chen on 2015/11/15.
 */
public class MainPresenter {

    MainView mainView;

    Context context;

    /**
     * 解析json之后获取到的今日新闻的内容
     */
    LatestNews latestNews;

    /**
     * 今日时间
     */
    String date;



    boolean isLoading = false;


    public MainPresenter(MainView view, Context context) {
        this.mainView = view;
        this.context = context;
    }


    /**
     * 最开始从服务器上面加载最新的内容.没有加载成功的话就加载在数据库中的缓存
     */
    public void load() {
        isLoading = true;
        //异步加载文字
        if (HttpUtils.isNetworkConnected(context)) {
            HttpUtils.get(constant.NEWS, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    //如果从网络获取失败,从数据库中获取缓存
                    getFromCache();
                    mainView.isRefreshing(false);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    parseResponseString(responseString);
                    mainView.isRefreshing(false);
                    isLoading = false;
                }
            });
        } else {
            //没有网络直接获取缓存
            mainView.isRefreshing(false);
            isLoading = false;
        }
    }

    /**
     * 从本地缓存里面获取数据
     */
    private void getFromCache() {
        isLoading = false;
    }

    /**
     * 解析网络返回的json数据
     *
     * @param responseString
     */
    private void parseResponseString(String responseString) {
        Gson gson = new Gson();
        latestNews = gson.fromJson(responseString, LatestNews.class);
        date = latestNews.getDate();
        mainView.setList(latestNews);
    }
}
