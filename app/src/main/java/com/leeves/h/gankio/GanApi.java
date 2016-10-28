package com.leeves.h.gankio;


import com.leeves.h.gankio.data.GData;
import com.leeves.h.gankio.data.HttpResultData;
import com.leeves.h.gankio.data.HttpResultJsonContent;
import com.leeves.h.gankio.data.entity.BaseData;
import com.leeves.h.gankio.data.entity.GContent;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Function：
 * Created by h on 2016/10/14.
 *
 * @author Leeves
 */

public interface GanApi {

    /**
     * gan的API
     * @param requestNum
     * @param requestPages
     * @return
     */
    @GET("data/{category}/{requestNum}/{requestPages}")
    Observable<HttpResultData<List<BaseData>>> getCategoryData(
            @Path("category") String category,
            @Path("requestNum") int requestNum,
            @Path("requestPages") int requestPages
    );


    /**
     * 获得year年month月day日的数据
     * @param year
     * @param month
     * @param day
     * @return
     */
    @GET("day/{year}/{month}/{day}")
    Observable<HttpResultJsonContent<GData>> getDayData(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day
    );

    //http://gank.io/api/day/history

//    /**
//     * 获取有数据的日期
//     * @return
//     */
//    @GET("day/history")
//    Observable<HttpResult<List<String>>> getDayNum();

    /**
     * 获得num天的数据
     * @param num
     * @param pages
     * @return
     */
    @GET("history/content/{num}/{pages}")
    Observable<HttpResultData<List<GContent>>> getContent(
            @Path("num") int num,
            @Path("pages") int pages
    );


}
