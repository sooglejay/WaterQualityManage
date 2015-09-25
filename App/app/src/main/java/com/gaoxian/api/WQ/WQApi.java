package com.gaoxian.api.WQ;

import com.gaoxian.Constant.NetWorkConstant;
import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.model.WQinfo;
import com.gaoxian.model.WQinfoPackge;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Administrator on 2015/9/12.
 */
public interface WQApi {
    @GET("/api/WQ/GetWQInfo/")
    public void getWQInfo(@Query(NetWorkConstant.stid) String stid, @Query(NetWorkConstant.apikey) String apikey, NetCallback<NetWorkResultBean<WQinfoPackge>> callback);

}
