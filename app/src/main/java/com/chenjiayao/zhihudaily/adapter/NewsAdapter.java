package com.chenjiayao.zhihudaily.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenjiayao.zhihudaily.R;
import com.chenjiayao.zhihudaily.model.LatestNews;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * Created by chen on 2015/11/15.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context context;
    private LayoutInflater inflater;
    private List<LatestNews.StoriesEntity> stories;
    private List<LatestNews.TopStoriesEntity> topStoriesEntities;

    ImageLoader imageLoader;
    DisplayImageOptions options;

    @Override
    public void onClick(View v) {
        if (listener != null) {
            int pos = (int) v.getTag();
            listener.onClick(v, pos);
        }
    }

    public interface onClickListener {
        void onClick(View view, int pos);
    }


    public onClickListener listener;


    private static final int ITEM_TYPE_HEADER = 0;
    private static final int ITEM_TYPE_ITEM = 1;


    public void setListener(onClickListener listener) {
        this.listener = listener;
    }

    public NewsAdapter(Context context) {
        this.context = context;
        this.stories = new ArrayList<>();
        this.topStoriesEntities = new ArrayList<>();

        inflater = LayoutInflater.from(context);

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    /**
     * 在这个地方可能 会降低性能
     *
     * @param s
     */
    public void setStoriesList(List<LatestNews.StoriesEntity> s) {
        stories.clear();
        this.stories.addAll(s);
        notifyDataSetChanged();
    }

    public void setTopStoriesList(List<LatestNews.TopStoriesEntity> s) {
        topStoriesEntities.clear();
        topStoriesEntities.addAll(s);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_TYPE_HEADER) {
            view = inflater.inflate(R.layout.item_news_view_pager, parent, false);
            return new HeadViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.item_news, parent, false);
            return new BodyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {

        } else {
            BodyViewHolder viewHolder = (BodyViewHolder) holder;
            viewHolder.title.setText(stories.get(position).getTitle());
            imageLoader.displayImage(stories.get(position).getImages().get(0), viewHolder.picture, options);

            viewHolder.itemView.setTag(position);
            viewHolder.itemView.setOnClickListener(this);
        }
    }


    @Override
    public int getItemCount() {
        return stories.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ITEM_TYPE_HEADER : ITEM_TYPE_ITEM;
    }

    public class BodyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_title)
        TextView title;
        @Bind(R.id.iv_picture)
        ImageView picture;

        public BodyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HeadViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.view_pager)
        AutoScrollViewPager viewPager;


        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            new TopStoriesAdapter(,topStoriesEntities);
        }
    }
}
