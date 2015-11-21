package com.chenjiayao.zhihudaily.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenjiayao.zhihudaily.R;
import com.chenjiayao.zhihudaily.model.LatestNews;
import com.chenjiayao.zhihudaily.model.StoriesEntity;
import com.chenjiayao.zhihudaily.ui.autoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 2015/11/15.
 */
public class NewsAdapter extends BaseAdapter implements View.OnClickListener, autoScrollViewPager.OnItemClickListener {

    private List<LatestNews.TopStoriesEntity> topStoriesEntities;
    List<StoriesEntity> stories;


    FragmentManager manager;

    String lastDate;

    public onViewPagerItemClickListener viewPagerItemClickListener;


    public void addList(List<StoriesEntity> s) {
        int size = stories.size();
        stories.addAll(size, s);
        notifyItemRangeInserted(size, s.size());
    }

    public interface onViewPagerItemClickListener {
        void onPageItemClick(View view, LatestNews.TopStoriesEntity entity);
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


    public NewsAdapter(Context context, FragmentManager manager) {
        super(context);
        this.topStoriesEntities = new ArrayList<>();
        this.stories = new ArrayList<>();

        this.manager = manager;
    }

    public void setStoriesList(List<StoriesEntity> list) {
        stories.clear();
        stories.addAll(list);
        notifyDataSetChanged();
    }


    public void setTopStoriesList(List<LatestNews.TopStoriesEntity> s) {
        topStoriesEntities.clear();
        topStoriesEntities.addAll(s);
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (position == 0) {
            HeadViewHolder viewHolder = (HeadViewHolder) holder;
            viewHolder.pager.setTopStoriesEntities(topStoriesEntities);
            viewHolder.pager.setListener(this);
        } else {
            BodyViewHolder viewHolder = (BodyViewHolder) holder;

            //已读处理
            if (utils.isClickItem(String.valueOf(stories.get(position).getId()))) {
                viewHolder.title.setTextColor(context.getResources().getColor(R.color.clicked_tv_textcolor));
            } else {
                viewHolder.title.setTextColor(context.getResources().getColor(R.color.textColor));
            }

            //内容
            viewHolder.title.setText(stories.get(position).getTitle());

            //图片显示,有可能包含没有图片的情况
            if (stories.get(position).getImages().get(0) == null) {
                viewHolder.picture.setVisibility(View.GONE);
            } else {
                imageLoader.displayImage(stories.get(position).getImages().get(0), viewHolder.picture, options);
                viewHolder.picture.setVisibility(View.VISIBLE);
            }
            viewHolder.itemView.setTag(position);

            //时间显示
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
            //点击监听函数
            viewHolder.itemView.setOnClickListener(this);
        }
    }


    @Override
    public int getItemCount() {
        return stories.size();
    }


    public class BodyViewHolder extends BaseViewHolder {
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

    }


    public class HeadViewHolder extends BaseViewHolder {

        @Bind(R.id.auto_scroll_page)
        autoScrollViewPager pager;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
