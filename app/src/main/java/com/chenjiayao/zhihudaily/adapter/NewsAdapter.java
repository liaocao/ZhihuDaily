package com.chenjiayao.zhihudaily.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenjiayao.zhihudaily.R;
import com.chenjiayao.zhihudaily.model.LatestNews;
import com.chenjiayao.zhihudaily.model.StoriesEntity;
import com.chenjiayao.zhihudaily.ui.autoScrollViewPager;
import com.chenjiayao.zhihudaily.uitls.PreUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 2015/11/15.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, autoScrollViewPager.OnItemClickListener {

    private Context context;
    private LayoutInflater inflater;
    private List<StoriesEntity> stories;
    private List<LatestNews.TopStoriesEntity> topStoriesEntities;

    FragmentManager manager;

    ImageLoader imageLoader;
    DisplayImageOptions options;

    String lastDate;

    public onViewPagerItemClickListener viewPagerItemClickListener;
    public onRecyclerViewItemListener recyclerViewItemListener;

    PreUtils utils;


    public void addList(List<StoriesEntity> s) {
        int size = stories.size();
        stories.addAll(size, s);
        notifyItemRangeInserted(size, s.size());
    }

    public interface onViewPagerItemClickListener {
        void onPageItemClick(View view, LatestNews.TopStoriesEntity entity);
    }

    public interface onRecyclerViewItemListener {
        void onClick(View view, StoriesEntity storiesEntity, int pos);
    }


    @Override
    public void onItemClick(View v, LatestNews.TopStoriesEntity entity) {
        if (viewPagerItemClickListener != null) {
            viewPagerItemClickListener.onPageItemClick(v, entity);
        }
    }

    @Override
    public void onClick(View v) {
        if (recyclerViewItemListener != null) {
            int pos = (int) v.getTag();
            if (0 != pos) {
                recyclerViewItemListener.onClick(v, stories.get(pos), pos);
            }
        }
    }

    public void setListener(onRecyclerViewItemListener listener) {
        this.recyclerViewItemListener = listener;
    }


    public void setListener(onViewPagerItemClickListener listener) {
        this.viewPagerItemClickListener = listener;
    }


    private static final int ITEM_TYPE_HEADER = 0;
    private static final int ITEM_TYPE_ITEM = 1;


    public NewsAdapter(Context context, FragmentManager manager) {
        this.context = context;
        this.stories = new ArrayList<>();
        this.topStoriesEntities = new ArrayList<>();
        this.manager = manager;
        utils = PreUtils.getInstance(context);
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
    public void setStoriesList(List<StoriesEntity> s) {
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
            view = inflater.inflate(R.layout.item_top_news, parent, false);
            return new HeadViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.item_news, parent, false);
            return new BodyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            HeadViewHolder viewHolder = (HeadViewHolder) holder;
            viewHolder.pager.setTopStoriesEntities(topStoriesEntities);
            viewHolder.pager.setListener(this);
        } else {
            BodyViewHolder viewHolder = (BodyViewHolder) holder;

            if (utils.isClickItem(String.valueOf(stories.get(position).getId()))) {
                viewHolder.title.setTextColor(context.getResources().getColor(R.color.clicked_tv_textcolor));
            } else {
                viewHolder.title.setTextColor(context.getResources().getColor(R.color.textColor));
            }

            viewHolder.title.setText(stories.get(position).getTitle());
            imageLoader.displayImage(stories.get(position).getImages().get(0), viewHolder.picture, options);
            viewHolder.itemView.setTag(position);

            if (position == 1) {
                viewHolder.tvTime.setVisibility(View.VISIBLE);
                viewHolder.tvTime.setText("今日热闻");
                lastDate = stories.get(position).getDate();
            } else {
                if (lastDate.equals(stories.get(position).getDate())) {

                    viewHolder.tvTime.setVisibility(View.GONE);
                } else {
                    viewHolder.tvTime.setVisibility(View.VISIBLE);
                    viewHolder.tvTime.setText(stories.get(position).getDate());
                    lastDate = stories.get(position).getDate();
                }
            }
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

    public static class BodyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_title)
        TextView title;
        @Bind(R.id.iv_picture)
        ImageView picture;
        @Bind(R.id.item_time)
        TextView tvTime;


        public BodyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public static void set(int pos) {
        }
    }


    public class HeadViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.auto_scroll_page)
        autoScrollViewPager pager;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
