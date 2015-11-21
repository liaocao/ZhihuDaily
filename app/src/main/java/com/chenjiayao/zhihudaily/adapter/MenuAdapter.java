package com.chenjiayao.zhihudaily.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenjiayao.zhihudaily.R;
import com.chenjiayao.zhihudaily.model.Theme;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 2015/11/17.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> implements View.OnClickListener {


    LayoutInflater inflater;
    Context context;

    List<Theme> items;


    public interface onClickListener {
        void onClick(View v, int pos);
    }

    public onClickListener listener;

    public void setListener(onClickListener listener) {
        this.listener = listener;
    }

    public MenuAdapter(Context context, List<Theme> items) {
        this.items = items;
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
        holder.menu.setText(items.get(position).getName());
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        return items.size();
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
            int pos = (int) v.getTag();
            listener.onClick(v, pos);
        }
    }
}
