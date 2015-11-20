package com.chenjiayao.zhihudaily.model;

import java.util.List;

/**
 * Created by chen on 2015/11/20.
 */
public class beforeContent {




    private String date;


    private List<StoriesEntity> stories;



    public void setDate(String date) {
        this.date = date;
    }

    public void setStories(List<StoriesEntity> stories) {
        this.stories = stories;
    }

    public String getDate() {
        return date;
    }

    public List<StoriesEntity> getStories() {
        return stories;
    }



}
