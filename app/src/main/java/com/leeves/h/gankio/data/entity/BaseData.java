package com.leeves.h.gankio.data.entity;

import java.util.List;

/**
 * Function：
 * Created by h on 2016/10/17.
 *
 * @author Leeves
 */

public class BaseData {

    /**
     * _id : 5802dc5e421aa90e714dc97e
     * createdAt : 2016-10-16T09:48:14.52Z
     * desc : 推荐一个 TV 类的 Android App，仿泰捷视频。
     * images : ["http://img.gank.io/3b76e341-7940-485f-a342-02a949365b4a"]
     * publishedAt : 2016-10-17T11:32:00.914Z
     * source : chrome
     * type : Android
     * url : https://github.com/hejunlin2013/TVSample
     * used : true
     * who : 代码家
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
