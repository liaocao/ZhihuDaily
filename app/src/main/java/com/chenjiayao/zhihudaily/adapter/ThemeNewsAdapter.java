package com.chenjiayao.zhihudaily.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenjiayao.zhihudaily.R;
import com.chenjiayao.zhihudaily.model.ThemeStories;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 2015/11/21.
 */
public class ThemeNewsAdapter extends BaseAdapter implements View.OnClickListener {


    public ThemeNewsAdapter(Context context) {
        super(context);
        stories = new ThemeStories();

    }

    //初次加载调用
    public void setListStories(ThemeStories listStories) {
        stories = listStories;
        notifyDataSetChanged();
    }

    //加载更多调用
    public void addListSroies(ThemeStories listStories) {
        int size = listStories.getStories().size();
        stories.getStories().addAll(listStories.getStories());
        notifyItemRangeInserted(listStories.getStories().size(), size);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return new HeadViewHolder(inflater.inflate(R.layout.item_theme_describle, parent, false));
        } else {
            return new ThemeNewsViewHolder(inflater.inflate(R.layout.item_theme_news, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (position == 0) {

            HeadViewHolder viewHolder = (HeadViewHolder) holder;
            viewHolder.tvDesc.setText(stories.getDescription());
            imageLoader.displayImage(stories.getBackground(), viewHolder.ivDesc, options);
        } else {
            ThemeNewsViewHolder viewHolder = (ThemeNewsViewHolder) holder;

            if (null == stories.getStories().get(position).getImages()) {
                viewHolder.ivPicture.setVisibility(View.GONE);
            } else {
                if (stories.getStories().get(position).getImages().size() == 0) {
                    viewHolder.ivPicture.setVisibility(View.GONE);
                } else {
                    imageLoader.displayImage(stories.getStories().get(position).getImages().get(0),
                            viewHolder.ivPicture, options);
                    viewHolder.ivPicture.setVisibility(View.VISIBLE);
                }
            }
            viewHolder.itemView.setTag(position);
            viewHolder.tvTitle.setText(stories.getStories().get(position).getTitle());
            viewHolder.itemView.setOnClickListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return stories.getStories().size();
    }

    public void setListener(onRecyclerViewItemListener listener) {
        this.recyclerViewItemListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (recyclerViewItemListener != null) {
            int pos = (int) v.getTag();
            recyclerViewItemListener.onClick(v, stories.getStories().get(pos), pos);
        }
    }


    public class ThemeNewsViewHolder extends BaseViewHolder {

        @Bind(R.id.tv_theme_title)
        TextView tvTitle;
        @Bind(R.id.iv_theme_picture)
        ImageView ivPicture;


        public ThemeNewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


    public class HeadViewHolder extends BaseViewHolder {

        @Bind(R.id.iv_theme_desc)
        ImageView ivDesc;
        @Bind(R.id.tv_theme_desc)
        TextView tvDesc;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
