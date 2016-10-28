package com.leeves.h.gankio.data;

import com.google.gson.annotations.SerializedName;
import com.leeves.h.gankio.data.entity.BaseData;

import java.util.List;

/**
 * Function：
 * Created by h on 2016/10/17.
 *
 * @author Leeves
 */

public class GData {

    @SerializedName("Android")
    public List<BaseData> androidList;
    @SerializedName("iOS")
    public List<BaseData> iOSList;
    @SerializedName("休息视频")
    public List<BaseData> videoList;
    @SerializedName("前端")
    public List<BaseData> frontEndList;
    @SerializedName("拓展资源")
    public List<BaseData> expandList;
    @SerializedName("福利")
    public List<BaseData> girlsList;

}
