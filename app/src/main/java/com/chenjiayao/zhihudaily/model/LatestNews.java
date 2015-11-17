package com.chenjiayao.zhihudaily.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chen on 2015/11/15.
 */
public class LatestNews implements Serializable {


    /**
     * date : 20151115
     * stories : [{"title":"传说中的烧脑神作，从彗星来的那一夜讲起","ga_prefix":"111521","images":["http://pic1.zhimg.com/7fe0f4f42d9932029417e252107efdf8.jpg"],"multipic":true,"type":0,"id":7358092},{"title":"想初学些法律知识，该看什么书？","ga_prefix":"111520","images":["http://pic4.zhimg.com/1f87cc2c28d071aa4deb8bd825b5f447.jpg"],"multipic":true,"type":0,"id":7425026},{"images":["http://pic2.zhimg.com/9e1bb9d64bdcb4765d3be36152be4849.jpg"],"type":0,"id":7430465,"ga_prefix":"111519","title":"哎呀，礼拜一要交的作业忘了写"},{"title":"在香港，有这样一所大学兼具古香古韵与欧式风情","ga_prefix":"111518","images":["http://pic4.zhimg.com/bbb44836deee99e5a01773401ea423ab.jpg"],"multipic":true,"type":0,"id":7423987},{"images":["http://pic1.zhimg.com/ffef2be2f4306779b38237212d9ca9e4.jpg"],"type":0,"id":7441350,"ga_prefix":"111517","title":"喝瓶装水总觉得有怪味，是不是添加了什么物质？"},{"title":"袋鼠国曾经仙人掌成灾，一个重要的原因是，能吃","ga_prefix":"111516","images":["http://pic4.zhimg.com/19545eef032027f9a23af13a62bbc943.jpg"],"multipic":true,"type":0,"id":7441221},{"title":"唐老鸭为什么不穿裤子？","ga_prefix":"111515","images":["http://pic1.zhimg.com/f1ba986cd87a86a5508fca5631d124f0.jpg"],"multipic":true,"type":0,"id":7442372},{"images":["http://pic4.zhimg.com/4a3c94d9397a4e398ec78746b59f03f3.jpg"],"type":0,"id":7429165,"ga_prefix":"111514","title":"迎面飞过来一个球，我一下子就接住了，反应就是这么快"},{"images":["http://pic3.zhimg.com/14f6501a29146242143eb328e6e9d8a6.jpg"],"type":0,"id":7431718,"ga_prefix":"111513","title":"从 CD 到 MP3，音量和耳朵在相互适应"},{"images":["http://pic1.zhimg.com/47cffd2db4e88810f4885b3883d99fa0.jpg"],"type":0,"id":7440007,"ga_prefix":"111512","title":"喂，地球吗？这里是太空"},{"title":"酒搭生蚝，爆浆、鲜香、滑嫩，只能说过瘾","ga_prefix":"111511","images":["http://pic1.zhimg.com/6a5f4b9caf751a44ed74371b2f99d844.jpg"],"multipic":true,"type":0,"id":7428974},{"images":["http://pic4.zhimg.com/17c97975ed07f8234b13fd29729fc4bf.jpg"],"type":0,"id":7439946,"ga_prefix":"111510","title":"又到了滑雪的季节，做好准备工作才能安全愉快地玩耍"},{"images":["http://pic2.zhimg.com/db6fbe485a956479631b04f2c8c72fb1.jpg"],"type":0,"id":7435009,"ga_prefix":"111509","title":"清朝人口猛涨过亿，一个原因居然是\u2026\u2026玉米"},{"title":"虚空之遗：一如既往的暴雪风格，故事要从星际争霸 1 讲起","ga_prefix":"111508","images":["http://pic1.zhimg.com/20e9cd91cff19afc8e1f960bec777e88.jpg"],"multipic":true,"type":0,"id":7426915},{"title":"看雪山不必跑很远，四川的景色已是一级棒","ga_prefix":"111507","images":["http://pic2.zhimg.com/4cd64a6b11c8cc2d6b3eef9dd4329b9d.jpg"],"multipic":true,"type":0,"id":7425409},{"images":["http://pic2.zhimg.com/4c73e94991df8da017ab8841d1942e3d.jpg"],"type":0,"id":7439423,"ga_prefix":"111507","title":"1090 度火焰燃烧，100G 重力撞击，保存着飞机所有秘密的它都得承受"},{"images":["http://pic2.zhimg.com/2490616c237ead74ace4144b3d055f41.jpg"],"type":0,"id":7440758,"ga_prefix":"111507","title":"恐怖袭击怎样改变了人们的生活"},{"images":["http://pic3.zhimg.com/4f53a9b0522264aeb64e28ffa2a84bf6.jpg"],"type":0,"id":7440134,"ga_prefix":"111506","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic3.zhimg.com/6d01ee60e279959ceccf86d1635c4232.jpg","type":0,"id":7430465,"ga_prefix":"111519","title":"哎呀，礼拜一要交的作业忘了写"},{"image":"http://pic2.zhimg.com/1b225fdd82c0dcd3dddc28c8486cfed5.jpg","type":0,"id":7441221,"ga_prefix":"111516","title":"袋鼠国曾经仙人掌成灾，一个重要的原因是，能吃"},{"image":"http://pic3.zhimg.com/13dd13a00a038b6fec54a4ca411bb212.jpg","type":0,"id":7442372,"ga_prefix":"111515","title":"唐老鸭为什么不穿裤子？"},{"image":"http://pic4.zhimg.com/9253afa058ef16b3a10b1092c94424af.jpg","type":0,"id":7426915,"ga_prefix":"111508","title":"虚空之遗：一如既往的暴雪风格，故事要从星际争霸 1 讲起"},{"image":"http://pic3.zhimg.com/3e386c9154d71c28b8f3462226bf0666.jpg","type":0,"id":7440758,"ga_prefix":"111507","title":"恐怖袭击怎样改变了人们的生活"}]
     */

    private String date;

    //今天的新闻,这个放RecyclerView列表
    private List<StoriesEntity> stories;

    //今天的推荐新闻,这个要放在RecyclerView前面的轮播器里面
    private List<TopStoriesEntity> top_stories;


    public void setDate(String date) {
        this.date = date;
    }

    public void setStories(List<StoriesEntity> stories) {
        this.stories = stories;
    }

    public void setTop_stories(List<TopStoriesEntity> top_stories) {
        this.top_stories = top_stories;
    }

    public String getDate() {
        return date;
    }

    public List<StoriesEntity> getStories() {
        return stories;
    }

    public List<TopStoriesEntity> getTop_stories() {
        return top_stories;
    }


    //今天的新闻
    public static class StoriesEntity {

        private String title;
        private String ga_prefix;
        private boolean multipic;
        private int type;
        private int id;
        private List<String> images;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public void setMultipic(boolean multipic) {
            this.multipic = multipic;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public String getTitle() {
            return title;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public boolean isMultipic() {
            return multipic;
        }

        public int getType() {
            return type;
        }

        public int getId() {
            return id;
        }

        public List<String> getImages() {
            return images;
        }
    }

    /**
     * 推荐,在轮播器里面的
     */
    public static class TopStoriesEntity {
        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public void setImage(String image) {
            this.image = image;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getImage() {
            return image;
        }

        public int getType() {
            return type;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public String getTitle() {
            return title;
        }
    }

    @Override
    public String toString() {
        return "LatestNews{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                ", top_stories=" + top_stories +
                '}';
    }
}
