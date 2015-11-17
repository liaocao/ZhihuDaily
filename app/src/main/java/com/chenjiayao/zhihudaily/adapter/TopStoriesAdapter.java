package com.chenjiayao.zhihudaily.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chenjiayao.zhihudaily.model.LatestNews;

import java.util.List;

/**
 * Created by chen on 2015/11/17.
 */
public class TopStoriesAdapter extends FragmentPagerAdapter {


    List<LatestNews.TopStoriesEntity> topStoriesEntities;

    public TopStoriesAdapter(FragmentManager fm, List<LatestNews.TopStoriesEntity> storiesEntityList) {
        super(fm);
        this.topStoriesEntities = storiesEntityList;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return topStoriesEntities.size();
    }
}
