package com.gaoxian.api.Production;

import com.gaoxian.Constant.NetWorkConstant;
import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.model.ProductionDataPackge;
import com.gaoxian.model.ProductionStatePackge;
import com.gaoxian.model.StationInfoPackge;
import com.gaoxian.model.WQinfoPackge;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Administrator on 2015/9/12.
 */
public interface GetProductionDataApi {
    @GET("/api/Production/GetProductionData")
    public void getProductionData(@Query(NetWorkConstant.stid) String stid,@Query(NetWorkConstant.apikey)String apikey,NetCallback<NetWorkResultBean<ProductionDataPackge>> NetCallback);

    @FormUrlEncoded
    @POST("/api/Production/GetProductionState/")
    public void postProductionState(@Field(NetWorkConstant.stid) String stid, @Field(NetWorkConstant.apikey) String apikey, NetCallback<NetWorkResultBean<ProductionStatePackge>> callback);

}
