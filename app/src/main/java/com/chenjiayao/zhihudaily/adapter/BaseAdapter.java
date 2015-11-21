package com.chenjiayao.zhihudaily.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chenjiayao.zhihudaily.model.StoriesEntity;
import com.chenjiayao.zhihudaily.model.ThemeStories;
import com.chenjiayao.zhihudaily.uitls.PreUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by chen on 2015/11/21.
 */
public abstract class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {


    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_ITEM = 1;


    Context context;
    LayoutInflater inflater;
    ThemeStories stories;
    ImageLoader imageLoader;
    DisplayImageOptions options;


    public onRecyclerViewItemListener recyclerViewItemListener;

    public interface onRecyclerViewItemListener {
        void onClick(View view, StoriesEntity storiesEntity, int pos);
    }


    PreUtils utils;


    public BaseAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        imageLoader = ImageLoader.getInstance();

        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        utils = PreUtils.getInstance(context);


    }


    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ITEM_TYPE_HEADER : ITEM_TYPE_ITEM;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }
}
