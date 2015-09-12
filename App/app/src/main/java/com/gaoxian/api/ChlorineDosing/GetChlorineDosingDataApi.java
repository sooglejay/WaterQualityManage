package com.gaoxian.api.ChlorineDosing;

import com.gaoxian.Constant.NetWorkConstant;
import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.model.ChlorineDosingData;
import com.gaoxian.model.ChlorineDosingDataPackge;
import com.gaoxian.model.ChlorineDosingStatePackge;
import com.gaoxian.model.NetWorkResultBean;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Administrator on 2015/9/12.
 */
public interface GetChlorineDosingDataApi {

    @FormUrlEncoded
    @POST("/api/ChlorineDosing/GetChlorineDosingData/")
    public void getChlorineDosingData(@Field(NetWorkConstant.stid) String stid, @Field(NetWorkConstant.apikey) String apikey, NetCallback<NetWorkResultBean<ChlorineDosingDataPackge>> callback);

    @FormUrlEncoded
    @POST("/api/ChlorineDosing/GetChlorineDosingState/")
    public void postChlorineDosingState(@Field(NetWorkConstant.stid) String stid, @Field(NetWorkConstant.apikey) String apikey, NetCallback<NetWorkResultBean<ChlorineDosingStatePackge>> callback);

}
