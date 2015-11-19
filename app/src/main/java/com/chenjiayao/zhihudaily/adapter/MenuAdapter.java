package com.chenjiayao.zhihudaily.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenjiayao.zhihudaily.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 2015/11/17.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> implements View.OnClickListener {

    String[] items = new String[]{"首页", "用户推荐日报", "互联网安全", "开始游戏", "设计日报"
            , "音乐日报", "动漫日报"};

    LayoutInflater inflater;
    Context context;

    View lastClickItem = null;

    public interface onClickListener {
        void onClick(View v, int pos);
    }

    public onClickListener listener;

    public void setListener(onClickListener listener) {
        this.listener = listener;
    }

    public MenuAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public MenuAdapter.MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuAdapter.MenuViewHolder holder, int position) {
        holder.menu.setText(items[position]);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);

        if (0 == position) {
            lastClickItem = holder.itemView;
            holder.itemView.setBackgroundResource(R.color.clickColor);
        }
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.menu_item)
        TextView menu;

        public MenuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void onClick(View v) {
        if (null != listener) {
            if (lastClickItem != v) {
                int pos = (int) v.getTag();
                lastClickItem.setBackgroundColor(Color.WHITE);
                v.setBackgroundResource(R.color.clickColor);
                lastClickItem = v;
                listener.onClick(v, pos);
            }
        }
    }
}
