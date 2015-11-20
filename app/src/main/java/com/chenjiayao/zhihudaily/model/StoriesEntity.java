package com.chenjiayao.zhihudaily.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chen on 2015/11/20.
 */
public class StoriesEntity implements Serializable {


    private int type;
    private int id;
    private String title;
    private List<String> images;

    private boolean multipic;

    String date;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isMultipic() {
        return multipic;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public List<String> getImages() {
        return images;
    }
}
