package com.chenjiayao.zhihudaily.model;

import java.util.List;

/**
 * Created by chen on 2015/11/20.
 */
public class Content {


    /**
     * image_source : 站酷海洛创意
     * title : 野生动物从不刷牙，有点担心他们的牙齿出问题
     * image : http://pic1.zhimg.com/3d8395f01761c77e87b673d0806191a4.jpg
     * share_url : http://daily.zhihu.com/story/7053854
     * js : []
     * recommenders : [{"avatar":"http://pic3.zhimg.com/bbb689a7a_m.jpg"}]
     * ga_prefix : 081710
     * type : 0
     * id : 7053854
     * css : ["http://news.at.zhihu.com/css/news_qa.auto.css?v=77778"]
     */

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private String ga_prefix;
    private int type;
    private int id;
    private List<?> js;
    /**
     * avatar : http://pic3.zhimg.com/bbb689a7a_m.jpg
     */

    private List<RecommendersEntity> recommenders;
    private List<String> css;

    public void setBody(String body) {
        this.body = body;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJs(List<?> js) {
        this.js = js;
    }

    public void setRecommenders(List<RecommendersEntity> recommenders) {
        this.recommenders = recommenders;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    public String getBody() {
        return body;
    }

    public String getImage_source() {
        return image_source;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getShare_url() {
        return share_url;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public List<?> getJs() {
        return js;
    }

    public List<RecommendersEntity> getRecommenders() {
        return recommenders;
    }

    public List<String> getCss() {
        return css;
    }



    public static class RecommendersEntity {
        private String avatar;

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAvatar() {
            return avatar;
        }
    }
}
