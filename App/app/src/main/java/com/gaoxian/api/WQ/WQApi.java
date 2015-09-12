package com.gaoxian.api.WQ;

import com.gaoxian.Constant.NetWorkConstant;
import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.model.WQinfo;
import com.gaoxian.model.WQinfoPackge;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Administrator on 2015/9/12.
 */
public interface WQApi {

    @FormUrlEncoded
    @POST("/api/WQ/GetWQInfo/")
    public void getWQInfo(@Field(NetWorkConstant.stid) String stid, @Field(NetWorkConstant.apikey) String apikey, NetCallback<NetWorkResultBean<WQinfoPackge>> callback);

}
