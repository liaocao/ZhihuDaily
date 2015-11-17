package com.chenjiayao.zhihudaily.adapter;

import android.content.Context;
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
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    String[] items = new String[]{"首页", "用户推荐日报", "互联网安全", "开始游戏", "设计日报"
            , "音乐日报", "动漫日报"};

    LayoutInflater inflater;
    Context context;

    public MenuAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public MenuAdapter.MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.menu_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuAdapter.MenuViewHolder holder, int position) {
        holder.menu.setText(items[position]);
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
}
