package com.gaoxian.api.production;

import com.gaoxian.constant.NetWorkConstant;
import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.model.ProductionDataPackge;
import com.gaoxian.model.ProductionStatePackge;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Administrator on 2015/9/12.
 */
public interface GetProductionDataApi {
    @GET("/api/Production/GetProductionData")
    public void getProductionData(@Query(NetWorkConstant.stid) String stid,@Query(NetWorkConstant.apikey)String apikey,NetCallback<NetWorkResultBean<ProductionDataPackge>> NetCallback);

    @GET("/api/Production/GetProductionState/")
    public void getProductionState(@Query(NetWorkConstant.stid) String stid, @Query(NetWorkConstant.apikey) String apikey, NetCallback<NetWorkResultBean<ProductionStatePackge>> callback);

}
