package com.leeves.h.gankio.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Function：
 * Created by h on 2016/10/19.
 *
 * @author
 */

public class MyDateUtils {

    public static int toGetYear(String date) {
        return Integer.valueOf(date.substring(0,4));
    }

    public static int toGetMonth(String date) {
        return Integer.valueOf(date.substring(5,7));
    }

    public static int toGetDay(String date) {
        return Integer.valueOf(date.substring(8,10));
    }


    /**
     * 解析图片
     *
     * @param htmlString
     * @return String Url
     */
    public static String meiZhiImgUrl(String htmlString) {
        Document document = Jsoup.parse(htmlString);
        return document.select("p img").first().attr("src");
    }

}
