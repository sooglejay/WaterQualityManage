package com.gaoxian.api.user;

import com.gaoxian.constant.NetWorkConstant;
import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.model.UserInfo;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by kcg on 15-7-8.
 */
public interface UserApi {

    @GET("/api/User/Login/")
    public void login(@Query(NetWorkConstant.name) String name,@Query(NetWorkConstant.password) String password,@Query(NetWorkConstant.apikey)String apikey,NetCallback<NetWorkResultBean<UserInfo>> NetCallback);
}
