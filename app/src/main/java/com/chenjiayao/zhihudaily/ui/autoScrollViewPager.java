package com.chenjiayao.zhihudaily.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
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



    /**
     * 用来填充一个title和imageview的view
     */
    List<View> newsViews;

    /**
     * 用来存放每一个指示器
     */
    private List<ImageView> dots;

    Context context;

    List<LatestNews.TopStoriesEntity> topStoriesEntities;

    ImageLoader imageLoader;
    DisplayImageOptions options;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

            handler.sendEmptyMessageDelayed(0, 4000);
        }
    };


    public interface OnItemClickListener {
        void onItemClick(View v, LatestNews.TopStoriesEntity entity);
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
        newsViews = new ArrayList<>();
        dots = new ArrayList<>();
    }

    public void setTopStoriesEntities(List<LatestNews.TopStoriesEntity> list) {
        this.topStoriesEntities = list;
        reset();
    }

    private void reset() {
        newsViews.clear();
        initUI();
    }

    private void initUI() {

        /**
         * 填充指示器
         */
        View view = LayoutInflater.from(context).inflate(R.layout.dot_layout, this, true);


        viewPager = (ViewPager) view.findViewById(R.id.vp);
        inflateDot = (LinearLayout) view.findViewById(R.id.ll_dot);

        inflateDot.removeAllViews();

        int len = topStoriesEntities.size();
        //0  1  2  3  4

        for (int i = 0; i < len; i++) {

            ImageView iv_dot = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            inflateDot.addView(iv_dot, params);
            dots.add(iv_dot);

            View fm = LayoutInflater.from(context).inflate(R.layout.view_pager_item, null);
            ImageView imageView = (ImageView) fm.findViewById(R.id.item_top_picture);
            TextView textView = (TextView) fm.findViewById(R.id.item_top_title);

            imageLoader.displayImage(topStoriesEntities.get(i).getImage(), imageView, options);
            textView.setText(topStoriesEntities.get(i).getTitle());

            newsViews.add(fm);
            fm.setOnClickListener(this);

            if( 0== i){
                dots.get(i).setImageResource(R.drawable.dot_focus);
            }else{
                dots.get(i).setImageResource(R.drawable.dot_blur);
            }
        }

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % len));
        viewPager.setOnPageChangeListener(this);

        startPlay();
    }


    private void startPlay() {
        handler.sendEmptyMessageDelayed(0, 4000);
    }


    /**
     * 每个页面的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (listener != null) {
            LatestNews.TopStoriesEntity entity = topStoriesEntities.get(viewPager.getCurrentItem() % topStoriesEntities.size());
            listener.onItemClick(v, entity);
        }
    }

    //////////////////////////////////////////////////////////////
    //viewPager的adapter
    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            container.addView(newsViews.get(position % newsViews.size()));
            return newsViews.get(position % newsViews.size());
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
            if (position % dots.size() == i) {
                dots.get(i).setImageResource(R.drawable.dot_focus);
            } else {
                dots.get(i).setImageResource(R.drawable.dot_blur);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
