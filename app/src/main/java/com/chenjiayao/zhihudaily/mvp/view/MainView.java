package com.chenjiayao.zhihudaily.mvp.view;

import com.chenjiayao.zhihudaily.model.LatestNews;
import com.chenjiayao.zhihudaily.model.StoriesEntity;
import com.chenjiayao.zhihudaily.model.Theme;
import com.chenjiayao.zhihudaily.model.ThemeStories;

import java.util.List;

/**
 * Created by chen on 2015/11/15.
 */
public interface MainView {

    void setRefreshing(boolean isRefresh);


    void setNewsAdapterList(LatestNews latestNews);

    void addToNewsAdapter(List<StoriesEntity> stories);

    void setThemeNewsAdapterList(ThemeStories themeStories);


    void setMenuAdapter(List<Theme> menuItems);
}
