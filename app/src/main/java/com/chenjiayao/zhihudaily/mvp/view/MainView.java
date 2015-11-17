package com.chenjiayao.zhihudaily.mvp.view;

import com.chenjiayao.zhihudaily.adapter.NewsAdapter;

/**
 * Created by chen on 2015/11/15.
 */
public interface MainView {

    void isRefreshing(boolean isRefresh);

    void initRecyclerView(NewsAdapter adapter);
}
