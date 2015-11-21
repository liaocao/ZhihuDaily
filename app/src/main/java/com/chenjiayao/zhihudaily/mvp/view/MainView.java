package com.chenjiayao.zhihudaily.mvp.view;

import com.chenjiayao.zhihudaily.adapter.ThemeNewsAdapter;
import com.chenjiayao.zhihudaily.model.LatestNews;
import com.chenjiayao.zhihudaily.model.StoriesEntity;

import java.util.List;

/**
 * Created by chen on 2015/11/15.
 */
public interface MainView {

    void isRefreshing(boolean isRefresh);


    void setList(LatestNews latestNews);

    void addToAdapter(List<StoriesEntity> stories);

    void testAdapter(ThemeNewsAdapter adapter);
}
