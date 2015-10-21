package com.gaoxian.api.wm;

import com.gaoxian.constant.NetWorkConstant;
import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.model.StationInfoPackge;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Administrator on 2015/9/12.
 */
public interface GetStationsApi {
    @GET("/api/WM/GetStations/")
    public void getStations(@Query(NetWorkConstant.areacode) String areacode,@Query(NetWorkConstant.apikey)String apikey,NetCallback<NetWorkResultBean<StationInfoPackge>> NetCallback);

}
