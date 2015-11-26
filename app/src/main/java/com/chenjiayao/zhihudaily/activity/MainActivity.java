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
import android.widget.TextView;

import com.chenjiayao.zhihudaily.R;
import com.chenjiayao.zhihudaily.adapter.BaseAdapter;
import com.chenjiayao.zhihudaily.adapter.MenuAdapter;
import com.chenjiayao.zhihudaily.adapter.NewsAdapter;
import com.chenjiayao.zhihudaily.adapter.ThemeNewsAdapter;
import com.chenjiayao.zhihudaily.model.LatestNews;
import com.chenjiayao.zhihudaily.model.StoriesEntity;
import com.chenjiayao.zhihudaily.model.Theme;
import com.chenjiayao.zhihudaily.model.ThemeStories;
import com.chenjiayao.zhihudaily.mvp.presenter.MainPresenter;
import com.chenjiayao.zhihudaily.mvp.view.MainView;
import com.chenjiayao.zhihudaily.uitls.PreUtils;
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
    RecyclerView newsRecyclerView;

    @Bind(R.id.menu_recycler_view)
    RecyclerView menuRecyclerView;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    MainPresenter mMainPresenter;
    private ActionBarDrawerToggle toggle;

    NewsAdapter newsAdapter;

    MenuAdapter menuAdapter;

    @Bind(R.id.main_page)
    TextView mainPage;

    List<Theme> menuItems;
    private ThemeNewsAdapter themeNewsAdapter;


    private MenuItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMainPresenter = new MainPresenter(this, this);
        mMainPresenter.loadFirst();

        menuItems = new ArrayList<>();

        ToolbarUtils.initToolbar(this, mToolbar, "首页");
        initRecyclerView();

        initDrawerLayout();
        initSwipeRefresh();

        mainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                mMainPresenter.toHomePage();
                mToolbar.setTitle("首页");
            }
        });

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
            mMainPresenter.loadFirst();

        }

    }

    @Override
    public void setRefreshing(boolean isRefresh) {
        refreshLayout.setRefreshing(isRefresh);
    }


    public void initRecyclerView() {
        initNewsRecyclerView();
        initMenuRecyclerView();
    }


    public void initMenuRecyclerView() {
        //menuRecyclerView
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        menuRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    public void initNewsRecyclerView() {
        newsAdapter = new NewsAdapter(MainActivity.this, getSupportFragmentManager());
        themeNewsAdapter = new ThemeNewsAdapter(MainActivity.this);

        final LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        newsRecyclerView.setLayoutManager(manager);
        newsRecyclerView.setItemAnimator(new DefaultItemAnimator());


        themeNewsAdapter.setListener(new BaseAdapter.onRecyclerViewItemListener() {
            @Override
            public void onClick(View view, StoriesEntity storiesEntity, int pos) {
                Intent intent = new Intent(MainActivity.this, LatestContentActivity.class);
                intent.putExtra("entity", storiesEntity);
                startActivity(intent);
                //我懒得处理了.
            }
        });

        //top新闻的点击事件
        newsAdapter.setListener(new NewsAdapter.onViewPagerItemClickListener() {
            @Override
            public void onPageItemClick(View view, LatestNews.TopStoriesEntity entity) {
                StoriesEntity storiesEntity = new StoriesEntity();
                storiesEntity.setId(entity.getId());
                storiesEntity.setTitle(entity.getTitle());
                List<String> images = new ArrayList<String>();
                images.add(entity.getImage());
                storiesEntity.setImages(images);

                PreUtils utils = PreUtils.getInstance(MainActivity.this);
                utils.saveClickItem(String.valueOf(entity.getId()), true);

                Intent intent = new Intent(MainActivity.this, LatestContentActivity.class);
                intent.putExtra("entity", storiesEntity);
                startActivity(intent);
            }
        });

        //列表新闻的点击事件
        newsAdapter.setListener(new NewsAdapter.onRecyclerViewItemListener() {
            @Override
            public void onClick(View view, StoriesEntity entity, int pos) {
                //每个Item的点击事件

                PreUtils utils = PreUtils.getInstance(MainActivity.this);
                utils.saveClickItem(String.valueOf(entity.getId()), true);

                StoriesEntity storiesEntity = new StoriesEntity();
                storiesEntity.setImages(entity.getImages());
                storiesEntity.setId(entity.getId());
                storiesEntity.setTitle(entity.getTitle());
                storiesEntity.setType(entity.getType());

                Intent intent = new Intent(MainActivity.this, LatestContentActivity.class);
                intent.putExtra("entity", storiesEntity);
                startActivity(intent);
                newsAdapter.notifyDataSetChanged();
            }
        });


        //上拉加载更多
        newsRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mMainPresenter.loadMore(manager);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });

        mMainPresenter.loadTheme();

    }


    /**
     * 首次填充adapter
     *
     * @param latestNews
     */
    @Override
    public void setNewsAdapterList(LatestNews latestNews) {

        newsRecyclerView.setAdapter(newsAdapter);
        newsAdapter.setStoriesList(latestNews.getStories());
        newsAdapter.setTopStoriesList(latestNews.getTop_stories());
    }

    /**
     * 第一次
     *
     * @param themeStories
     */
    @Override
    public void setThemeNewsAdapterList(ThemeStories themeStories) {
        themeNewsAdapter.setListStories(themeStories);
        newsRecyclerView.setAdapter(themeNewsAdapter);
    }


    /**
     * 加载过往内容
     *
     * @param stories
     */
    @Override
    public void addToNewsAdapter(List<StoriesEntity> stories) {
        newsAdapter.addList(stories);
    }


    @Override
    public void setMenuAdapter(final List<Theme> menuItems) {
        menuAdapter = new MenuAdapter(MainActivity.this, menuItems);
        menuAdapter.setListener(new MenuAdapter.onClickListener() {
            @Override
            public void onClick(View v, int pos) {
                mDrawerLayout.closeDrawers();
                mToolbar.setTitle(menuItems.get(pos).getName());
                mMainPresenter.onClick(menuItems.get(pos).getId());
            }
        });
        menuRecyclerView.setAdapter(menuAdapter);
    }


    @Override
    public void finish() {
        super.finish();
    }
}
