package com.gaoxian.api.user;

import android.content.Context;


import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.model.UserInfo;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.model.RetrofitUtil;

import retrofit.RestAdapter;

/**
 * Created by kcg on 15-7-7.
 */
public class UserRetrofitUtil extends RetrofitUtil {

    /**
     * 登陆
     * @param mContext
     * @param user_name
     * @param password
     */
    public static void login(final Context mContext, String user_name, String password, String apikey,NetCallback<NetWorkResultBean<UserInfo>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        git.login(user_name, password, apikey,callback);
    }
}
