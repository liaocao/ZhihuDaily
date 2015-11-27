package com.chenjiayao.zhihudaily.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.chenjiayao.zhihudaily.R;
import com.chenjiayao.zhihudaily.model.Content;
import com.chenjiayao.zhihudaily.model.StoriesEntity;
import com.chenjiayao.zhihudaily.mvp.presenter.LatestContentPresenter;
import com.chenjiayao.zhihudaily.mvp.view.LatestContentView;
import com.google.gson.Gson;
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

    @Bind(R.id.iv_content_picture)
    ImageView ivPicture;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private StoriesEntity entity;

    LatestContentPresenter mPresenter;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private Content content = new Content();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new LatestContentPresenter(this, this);

        entity = (StoriesEntity) getIntent().getSerializableExtra("entity");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        mCollapsingToolbarLayout.setTitle(entity.getTitle());


        initWebView();

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();


        mPresenter.getJson(entity.getId());
    }

    private void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        mWebView.getSettings().setDatabaseEnabled(true);
        // 开启Application Cache功能
        mWebView.getSettings().setAppCacheEnabled(true);
    }


    @Override
    protected int getContentLayout() {
        return R.layout.activity_content;
    }

    @Override
    public void showContent(String responseString) {
        Gson gson = new Gson();
        content = gson.fromJson(responseString, Content.class);
        imageLoader.displayImage(content.getImage(), ivPicture, options);

        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + content.getBody() + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        mWebView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }
}
