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

import com.chenjiayao.zhihudaily.R;
import com.chenjiayao.zhihudaily.adapter.MenuAdapter;
import com.chenjiayao.zhihudaily.adapter.NewsAdapter;
import com.chenjiayao.zhihudaily.adapter.ThemeNewsAdapter;
import com.chenjiayao.zhihudaily.constant;
import com.chenjiayao.zhihudaily.model.LatestNews;
import com.chenjiayao.zhihudaily.model.StoriesEntity;
import com.chenjiayao.zhihudaily.model.Theme;
import com.chenjiayao.zhihudaily.mvp.presenter.MainPresenter;
import com.chenjiayao.zhihudaily.mvp.view.MainView;
import com.chenjiayao.zhihudaily.uitls.HttpUtils;
import com.chenjiayao.zhihudaily.uitls.PreUtils;
import com.chenjiayao.zhihudaily.uitls.ToolbarUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cz.msebera.android.httpclient.Header;

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

    NewsAdapter newsAdapter;
    MenuAdapter menuAdapter;
    ThemeNewsAdapter themeAdapter;

    List<Theme> menuItems;


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
    public void isRefreshing(boolean isRefresh) {
        refreshLayout.setRefreshing(isRefresh);
    }

    public void initRecyclerView() {

        newsAdapter = new NewsAdapter(MainActivity.this, getSupportFragmentManager());
        mRecyclerView.setAdapter(newsAdapter);
        final LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

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


        if (HttpUtils.isNetworkConnected(MainActivity.this)) {
            HttpUtils.get(constant.THEME, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    parseJson(response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }
        //menuRecyclerView
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        menuRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }


    /**
     * 解析主题日报的json格式
     *
     * @param json
     */
    private void parseJson(JSONObject json) {
        try {
            JSONArray itemArray = json.getJSONArray("others");
            for (int i = 0; i < itemArray.length(); i++) {
                Theme theme = new Theme();
                JSONObject item = itemArray.getJSONObject(i);
                theme.setId(item.getString("id"));
                theme.setName(item.getString("name"));
                menuItems.add(theme);
            }
            menuAdapter = new MenuAdapter(MainActivity.this, menuItems);
            menuAdapter.setListener(new MenuAdapter.onClickListener() {
                @Override
                public void onClick(View v, int pos) {
                    mDrawerLayout.closeDrawers();
                    mMainPresenter.onClick(menuItems.get(pos).getId());
                }
            });
            menuRecyclerView.setAdapter(menuAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setList(LatestNews latestNews) {
        String date = latestNews.getDate();
        for (StoriesEntity entity :
                latestNews.getStories()) {
            entity.setDate(date);
        }
        newsAdapter.setStoriesList(latestNews.getStories());
        newsAdapter.setTopStoriesList(latestNews.getTop_stories());
    }

    @Override
    public void addToAdapter(List<StoriesEntity> stories) {
        newsAdapter.addList(stories);
    }

    @Override
    public void testAdapter(ThemeNewsAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }
}
