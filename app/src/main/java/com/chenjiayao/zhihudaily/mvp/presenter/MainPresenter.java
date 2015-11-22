package com.chenjiayao.zhihudaily.mvp.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.chenjiayao.zhihudaily.constant;
import com.chenjiayao.zhihudaily.model.LatestNews;
import com.chenjiayao.zhihudaily.model.StoriesEntity;
import com.chenjiayao.zhihudaily.model.Theme;
import com.chenjiayao.zhihudaily.model.ThemeStories;
import com.chenjiayao.zhihudaily.model.beforeContent;
import com.chenjiayao.zhihudaily.mvp.view.MainView;
import com.chenjiayao.zhihudaily.uitls.HttpUtils;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 前一天的日期,这个前一天指的是已经加载出来内容的日期的前一天
     */
    String lastDate;


    boolean isLoading = false;
    boolean isLoadingMore = false;


    public MainPresenter(MainView view, Context context) {
        this.mainView = view;
        this.context = context;
    }


    /**
     * 首次加载 / 下拉刷新
     */
    public void loadFirst() {
        isLoading = true;
        //异步加载文字
        if (HttpUtils.isNetworkConnected(context)) {
            HttpUtils.get(constant.NEWS, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    parseTodayResponseString(responseString);
                    isLoading = false;
                }
            });
        }
    }


    /**
     * 解析今日日报内容
     *
     * @param responseString
     */
    private void parseTodayResponseString(String responseString) {
        Gson gson = new Gson();
        latestNews = gson.fromJson(responseString, LatestNews.class);
        date = latestNews.getDate();
        lastDate = date;
        for (StoriesEntity entity :
                latestNews.getStories()) {
            entity.setDate(convertDate(date));
        }
        isLoading = false;
        mainView.isRefreshing(false);

        mainView.setNewsAdapterList(latestNews);
    }

/////////////////////////////////////////////////////////////////////////////////

    /**
     * 加载往期日报内容
     *
     * @param manager
     */
    public void loadMore(LinearLayoutManager manager, int flag) {

        int visibleItemCount = manager.getChildCount();
        int totalItemCount = manager.getItemCount();
        int first = manager.findFirstVisibleItemPosition();

        if (first + visibleItemCount >= totalItemCount && !isLoadingMore) {

            //可以加载更多
            isLoadingMore = true;
            if (0 == flag) {
                if (HttpUtils.isNetworkConnected(context)) {
                    HttpUtils.get(constant.BEFORE_URL + date, new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            parseBeforeResponseString(responseString);
                        }
                    });

                }
            }
        }
    }

    /**
     * 解析往期的日报内容
     *
     * @param responseString
     */
    private void parseBeforeResponseString(String responseString) {
        Gson gson = new Gson();
        beforeContent content = gson.fromJson(responseString, com.chenjiayao.zhihudaily.model.beforeContent.class);
        date = content.getDate();
        for (StoriesEntity entity : content.getStories()) {
            entity.setDate(convertDate(date));
        }
        isLoadingMore = false;
        latestNews.getStories().addAll(content.getStories());
        mainView.addToNewsAdapter(content.getStories());
    }


    /**
     * 格式转换
     * 20151121 ---> 2015年11月21日
     *
     * @param date
     * @return
     */
    private String convertDate(String date) {
        String res = date.substring(0, 4);
        res += "年";
        res += date.substring(4, 6);
        res += "月";
        res += date.substring(6, 8);
        res += "日";
        return res;
    }

    /////////////////////////////////////////////////////////////////////////


    /**
     * 点击抽屉菜单item的响应
     *
     * @param id
     */
    public void onClick(String id) {
        if (HttpUtils.isNetworkConnected(context)) {
            HttpUtils.get("theme/" + id, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {

                    parseThemeResponse(responseString);
                }
            });
        }
    }


    /**
     * 解析主题日报格式
     *
     * @param responseString
     */
    private void parseThemeResponse(String responseString) {
        Gson gson = new Gson();
        ThemeStories themeStories = gson.fromJson(responseString, ThemeStories.class);
        mainView.setThemeNewsAdapterList(themeStories);
    }

    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////

    /**
     * 加载主题日报菜单
     */
    public void loadTheme() {
        if (HttpUtils.isNetworkConnected(context)) {
            HttpUtils.get(constant.THEME, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    parseJson(response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }
    }


    /**
     * 解析主题日报的json格式
     *
     * @param json
     */
    private void parseJson(JSONObject json) {
        try {
            JSONArray itemArray = json.getJSONArray("others");
            List<Theme> menuItems = new ArrayList<>();
            for (int i = 0; i < itemArray.length(); i++) {
                Theme theme = new Theme();
                JSONObject item = itemArray.getJSONObject(i);
                theme.setId(item.getString("id"));
                theme.setName(item.getString("name"));
                menuItems.add(theme);
                mainView.setMenuAdapter(menuItems);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void toHomePage() {
        mainView.setNewsAdapterList(latestNews);
    }
}





