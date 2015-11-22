

#知乎日报

>这是一个仿造知乎日报的APP,业余学习时开发,APP并不完善,但是可以从中学到一些东西.至于API接口,感谢lzzyLeung提供的API接口.[这里][1]

##已完成功能

+ 启动动画
+ 主题日报的获取
+ 今日热点新闻的轮播
+ 使用谷歌官方的`swpieRefreshLayout`实现下拉刷新
+ 使用`RecyclerView`代替`ListView`
+ 标记已读文章
+ `viewPager`的自定义
+ `RecyclerView`实现底部自动加载更多
+ **努力的**使用了MVP结构
+ `CollapsingToolbarLayout` + `AppBarLayout`实现滚动视差特效
+ 尽量的符合`Material Design`规范


##依赖的第三方库

+ [android-async-http][2]
+ [Gson][3]
+ [ButterKnife][4]
+ [Android-Universal-Image-Loader][5]

  [1]: https://github.com/iKrelve/KuaiHu/blob/master/%E7%9F%A5%E4%B9%8E%E6%97%A5%E6%8A%A5API.md
  [2]: https://github.com/loopj/android-async-http
  [3]: https://github.com/google/gson
  [4]:https://github.com/JakeWharton/butterknife
  [5]:https://github.com/nostra13/Android-Universal-Image-Loader