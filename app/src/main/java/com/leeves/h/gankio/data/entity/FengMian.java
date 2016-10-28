package com.leeves.h.gankio.data.entity;

/**
 * Function：
 * Created by h on 2016/10/17.
 *
 * @author Leeves
 */

public class FengMian {

    private String meiZhiImgUrl;
    private String date;
    private String title;

    public FengMian(String meiZhiImgUrl, String date, String title) {
        this.meiZhiImgUrl = meiZhiImgUrl;
        this.date = date;
        this.title = title;
    }

    public String getMeiZhiImgUrl() {
        return meiZhiImgUrl;
    }

    public void setMeiZhiImgUrl(String meiZhiImgUrl) {
        this.meiZhiImgUrl = meiZhiImgUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
