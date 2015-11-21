package com.chenjiayao.zhihudaily.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenjiayao.zhihudaily.R;
import com.chenjiayao.zhihudaily.model.StoriesEntity;
import com.chenjiayao.zhihudaily.model.ThemeStories;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 2015/11/21.
 */
public class ThemeNewsAdapter extends BaseAdapter {


    public ThemeNewsAdapter(Context context) {
        super(context);
        stories = new ThemeStories();
        stories.setStories(new ArrayList<StoriesEntity>());

    }


    public void setListStories(ThemeStories listStories) {
        stories = listStories;
        notifyDataSetChanged();
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
            viewHolder.tvTitle.setText(stories.getStories().get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return stories.getStories().size();
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
