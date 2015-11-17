package com.chenjiayao.zhihudaily.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chenjiayao.zhihudaily.model.LatestNews;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by chen on 2015/11/17.
 */
public class TopStoriesAdapter extends FragmentPagerAdapter {


    private final DisplayImageOptions options;
    List<LatestNews.TopStoriesEntity> topStoriesEntities;

    ImageLoader imageLoader;


    public TopStoriesAdapter(FragmentManager fm, List<LatestNews.TopStoriesEntity> storiesEntityList,
                             Context context) {
        super(fm);
        this.topStoriesEntities = storiesEntityList;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
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
