package com.chenjiayao.zhihudaily.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenjiayao.zhihudaily.R;

import butterknife.ButterKnife;

/**
 * Created by chen on 2015/11/17.
 */
public class ScrollFragment extends Fragment {


    public ImageView topPicture;
    public TextView topTitle;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.view_pager_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
