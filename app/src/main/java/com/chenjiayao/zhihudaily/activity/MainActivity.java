package com.chenjiayao.zhihudaily.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chenjiayao.zhihudaily.R;
import com.chenjiayao.zhihudaily.adapter.MenuAdapter;
import com.chenjiayao.zhihudaily.adapter.NewsAdapter;
import com.chenjiayao.zhihudaily.model.LatestNews;
import com.chenjiayao.zhihudaily.model.StoriesEntity;
import com.chenjiayao.zhihudaily.mvp.presenter.MainPresenter;
import com.chenjiayao.zhihudaily.mvp.view.MainView;
import com.chenjiayao.zhihudaily.uitls.ToolbarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements MainView, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.drawer_lalyout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.menu_recycler_view)
    RecyclerView menuRecyclerView;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    MainPresenter mMainPresenter;
    private ActionBarDrawerToggle toggle;

    NewsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMainPresenter = new MainPresenter(this, this);
        mMainPresenter.load();

        ToolbarUtils.initToolbar(this, mToolbar, "首页");
        initRecyclerView();

        initDrawerLayout();
        initSwipeRefresh();

    }


    private void initSwipeRefresh() {

        refreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_dark);
        refreshLayout.setOnRefreshListener(this);
    }


    private void initDrawerLayout() {

        toggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(toggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }


    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(true);
            mMainPresenter.load();
        }
    }


    @Override
    public void isRefreshing(boolean isRefresh) {
        refreshLayout.setRefreshing(isRefresh);
    }

    public void initRecyclerView() {

        adapter = new NewsAdapter(MainActivity.this, getSupportFragmentManager());
        mRecyclerView.setAdapter(adapter);
        final LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //top新闻的点击事件
        adapter.setListener(new NewsAdapter.onViewPagerItemClickListener() {
            @Override
            public void onPageItemClick(View view, LatestNews.TopStoriesEntity entity) {
                StoriesEntity storiesEntity = new StoriesEntity();
                storiesEntity.setId(entity.getId());
                storiesEntity.setTitle(entity.getTitle());
                List<String> images = new ArrayList<String>();
                images.add(entity.getImage());
                storiesEntity.setImages(images);

                Intent intent = new Intent(MainActivity.this, LatestContentActivity.class);
                intent.putExtra("entity", storiesEntity);
                startActivity(intent);
            }
        });

        //列表新闻的点击事件
        adapter.setListener(new NewsAdapter.onRecyclerViewItemListener() {
            @Override
            public void onClick(View view, StoriesEntity entity) {
                //每个Item的点击事件
                StoriesEntity storiesEntity = new StoriesEntity();
                storiesEntity.setImages(entity.getImages());
                storiesEntity.setId(entity.getId());
//                storiesEntity.setGa_prefix(entity.getGa_prefix());
                storiesEntity.setTitle(entity.getTitle());
                storiesEntity.setType(entity.getType());

                Intent intent = new Intent(MainActivity.this, LatestContentActivity.class);
                intent.putExtra("entity", storiesEntity);
                startActivity(intent);
            }
        });

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = manager.getChildCount();
                int totalItemCount = manager.getItemCount();
                int first = manager.findFirstVisibleItemPosition();

                if (first + visibleItemCount >= totalItemCount) {
                    //可以加载更多
                    mMainPresenter.loadMore();
                }
            }


        });


        //menuRecyclerView
        MenuAdapter menuAdapter = new MenuAdapter(MainActivity.this);
        menuRecyclerView.setAdapter(menuAdapter);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        menuRecyclerView.setItemAnimator(new DefaultItemAnimator());

        menuAdapter.setListener(new MenuAdapter.onClickListener() {
            @Override
            public void onClick(View v, int pos) {
                Toast.makeText(MainActivity.this, "pos = " + pos, Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawers();
            }
        });
    }

    @Override
    public void setList(LatestNews latestNews) {
        String date = latestNews.getDate();
        for (StoriesEntity entity :
                latestNews.getStories()) {
            entity.setDate(date);
        }
        adapter.setStoriesList(latestNews.getStories());
        adapter.setTopStoriesList(latestNews.getTop_stories());
    }

    @Override
    public void addToAdapter(List<StoriesEntity> stories) {
        adapter.addList(stories);
    }
}
