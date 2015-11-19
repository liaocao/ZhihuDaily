package com.chenjiayao.zhihudaily.ui;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenjiayao.zhihudaily.R;
import com.chenjiayao.zhihudaily.model.LatestNews;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 2015/11/19.
 */
public class autoScrollViewPager extends FrameLayout
        implements View.OnClickListener, ViewPager.OnPageChangeListener {


    //    @Bind(R.id.ll_dot)
    LinearLayout inflateDot;

    //    @Bind(R.id.vp)
    ViewPager viewPager;

    //    @Bind(R.id.item_top_title)
    TextView tvTitle;
    //    @Bind(R.id.item_top_picture)
    ImageView ivPicture;


    boolean isAutoPlay = true;

    /**
     * 用来填充一个title和imageview的view
     */
    List<View> views;

    /**
     * 用来存放每一个指示器
     */
    private List<ImageView> dots;

    Context context;

    List<LatestNews.TopStoriesEntity> topStoriesEntities;

    ImageLoader imageLoader;
    DisplayImageOptions options;

    private Handler handler = new Handler();

    /**
     * 延时多久自动切换
     */
    private int delayTime;
    private int currentItme;


    public interface OnItemClickListener {
        public void onClick(View v, LatestNews.TopStoriesEntity entity);
    }


    public OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public autoScrollViewPager(Context context) {
        this(context, null);
    }

    public autoScrollViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public autoScrollViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        topStoriesEntities = new ArrayList<>();
        imageLoader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();
        this.context = context;

        initView();

    }

    private void initView() {
        views = new ArrayList<>();
        dots = new ArrayList<>();

        delayTime = 2000;
    }

    public void setTopStoriesEntities(List<LatestNews.TopStoriesEntity> list) {
        this.topStoriesEntities = list;
        reset();
    }

    private void reset() {
        views.clear();
        initUI();
    }

    private void initUI() {

        /**
         * 填充指示器
         */
        View view = LayoutInflater.from(context).inflate(R.layout.dot_layout, this, true);
//        ButterKnife.bind(view, this);

        viewPager = (ViewPager) view.findViewById(R.id.vp);
        inflateDot = (LinearLayout) view.findViewById(R.id.ll_dot);

        inflateDot.removeAllViews();

        int len = topStoriesEntities.size();

        for (int i = 0; i < len; i++) {
            ImageView imageView = new ImageView(context);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            params.leftMargin = 6;
            params.rightMargin = 6;

            inflateDot.addView(imageView, params);

            dots.add(imageView);
        }


        for (int i = 0; i <= len + 1; i++) {
            View fragmentView = LayoutInflater.from(context).inflate(R.layout.view_pager_item, null);
//            ButterKnife.bind(fragmentView, this);
            tvTitle = (TextView) fragmentView.findViewById(R.id.item_top_title);
            ivPicture = (ImageView) fragmentView.findViewById(R.id.item_top_picture);
            if (len + 1 == i) {
                imageLoader.displayImage(topStoriesEntities.get(0).getImage(), ivPicture, options);
                tvTitle.setText(topStoriesEntities.get(0).getTitle());
            } else if (0 == i) {
                imageLoader.displayImage(topStoriesEntities.get(len - 1).getImage(), ivPicture, options);
                tvTitle.setText(topStoriesEntities.get(len - 1).getTitle());
            } else {
                imageLoader.displayImage(topStoriesEntities.get(i - 1).getImage(), ivPicture, options);
                tvTitle.setText(topStoriesEntities.get(i - 1).getTitle());
            }
            //填充了 len + 1个图片
            views.add(fragmentView);
            fragmentView.setOnClickListener(this);

        }
        currentItme = 1;
        viewPager.setCurrentItem(1);
        viewPager.setFocusable(true);
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(this);
        startPlay();
    }

    /**
     * 每个页面的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (listener != null) {
            LatestNews.TopStoriesEntity entity = topStoriesEntities.get(viewPager.getCurrentItem() - 1);
            listener.onClick(v, entity);
        }
    }

    private void startPlay() {
        handler.postDelayed(task, 3000);
    }

    //轮播核心代码
    private final Runnable task = new Runnable() {
        //开始自动轮播
        @Override
        public void run() {
            if (isAutoPlay) {
                currentItme = currentItme % (topStoriesEntities.size() + 1) + 1;
                if (1 == currentItme) {
                    viewPager.setCurrentItem(currentItme, true);
                    handler.post(task);
                } else {
                    viewPager.setCurrentItem(currentItme);
                    handler.postDelayed(task, 5000);
                }
            } else {
                handler.postDelayed(task, 5000);
            }
        }
    };


    //////////////////////////////////////////////////////////////
    //viewPager的adapter
    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {

            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    ///////////////////////////////////////////////////////////////////
    //继承的监听函数
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dots.size(); i++) {
            if (i == position - 1) {
                dots.get(i).setImageResource(R.drawable.dot_focus);
            } else {
                dots.get(i).setImageResource(R.drawable.dot_blur);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                isAutoPlay = false;
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                isAutoPlay = true;
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                if (0 == viewPager.getCurrentItem()) {
                    viewPager.setCurrentItem(topStoriesEntities.size(), false);
                } else if (viewPager.getCurrentItem() == topStoriesEntities.size() + 1) {
                    viewPager.setCurrentItem(1, false);
                }
                currentItme = viewPager.getCurrentItem();
                isAutoPlay = true;
                break;
        }
    }
}
