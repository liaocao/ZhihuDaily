package com.chenjiayao.zhihudaily.model;

import java.util.List;

/**
 * Created by chen on 2015/11/21.
 */
public class ThemeStories {


    private String description;
    private String background;
    private int color;
    private String name;

    //这个字段好像没有什么卵用
    private String image;
    private String image_source;


    private List<StoriesEntity> stories;


    //暂时不实现
    private List<EditorsEntity> editors;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public void setStories(List<StoriesEntity> stories) {
        this.stories = stories;
    }

    public void setEditors(List<EditorsEntity> editors) {
        this.editors = editors;
    }

    public String getDescription() {
        return description;
    }

    public String getBackground() {
        return background;
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getImage_source() {
        return image_source;
    }

    public List<StoriesEntity> getStories() {
        return stories;
    }

    public List<EditorsEntity> getEditors() {
        return editors;
    }


    public static class EditorsEntity {
        private String url;
        private String bio;
        private int id;
        private String avatar;
        private String name;

        public void setUrl(String url) {
            this.url = url;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public String getBio() {
            return bio;
        }

        public int getId() {
            return id;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getName() {
            return name;
        }
    }
}
