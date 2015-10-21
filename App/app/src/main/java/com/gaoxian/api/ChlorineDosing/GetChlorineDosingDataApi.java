package com.gaoxian.api.chlorineDosing;

import com.gaoxian.constant.NetWorkConstant;
import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.model.ChlorineDosingDataPackge;
import com.gaoxian.model.ChlorineDosingStatePackge;
import com.gaoxian.model.NetWorkResultBean;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Administrator on 2015/9/12.
 */
public interface GetChlorineDosingDataApi {

    @GET("/api/ChlorineDosing/GetChlorineDosingData/")
    public void getChlorineDosingData(@Query(NetWorkConstant.stid) String stid, @Query(NetWorkConstant.apikey) String apikey, NetCallback<NetWorkResultBean<ChlorineDosingDataPackge>> callback);

    @GET("/api/ChlorineDosing/GetChlorineDosingState/")
    public void getChlorineDosingState(@Query(NetWorkConstant.stid) String stid, @Query(NetWorkConstant.apikey) String apikey, NetCallback<NetWorkResultBean<ChlorineDosingStatePackge>> callback);

}
