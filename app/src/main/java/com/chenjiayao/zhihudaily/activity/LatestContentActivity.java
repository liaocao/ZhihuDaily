package com.chenjiayao.zhihudaily.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.chenjiayao.zhihudaily.R;
import com.chenjiayao.zhihudaily.model.Content;
import com.chenjiayao.zhihudaily.model.LatestNews;
import com.chenjiayao.zhihudaily.mvp.presenter.LatestContentPresenter;
import com.chenjiayao.zhihudaily.mvp.view.LatestContentView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;

/**
 * Created by chen on 2015/11/19.
 */
public class LatestContentActivity extends BaseActivity implements LatestContentView {

    @Bind(R.id.app_bar)
    AppBarLayout mAppBarLayout;

    @Bind(R.id.web_view)
    WebView mWebView;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Bind(R.id.iv_picture)
    ImageView ivPicture;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    private LatestNews.StoriesEntity entity;

    LatestContentPresenter mPresenter;
    private Content content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAppBarLayout.setVisibility(View.INVISIBLE);

        entity = (LatestNews.StoriesEntity) getIntent().getSerializableExtra("entity");

        mPresenter = new LatestContentPresenter(this, this);

        mCollapsingToolbarLayout.setTitle(entity.getTitle());
        mCollapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
        mCollapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorPrimaryDark));


        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        mWebView.getSettings().setDomStorageEnabled(true);

        mWebView.getSettings().setDatabaseEnabled(true);

        mWebView.getSettings().setAppCacheEnabled(true);

        content = mPresenter.getContent(entity.getId());

        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();
        Log.i("TAG", content.getImage());
        if (content != null) {
            imageLoader.displayImage(content.getImage(), ivPicture, options);
        }

    }


    @Override
    protected int getContentLayout() {
        return R.layout.activity_content;
    }
}
