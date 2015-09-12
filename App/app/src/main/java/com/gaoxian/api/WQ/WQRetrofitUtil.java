package com.gaoxian.api.WQ;

import android.content.Context;

import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.model.WQinfoPackge;
import com.gaoxian.util.RetrofitUtil;

import retrofit.RestAdapter;

/**
 * Created by Administrator on 2015/9/12.
 */
public class WQRetrofitUtil extends RetrofitUtil {
    /**
     * @param mContext
     * @param stid
     * @param apikey
     */
    public static void getWQInfo(final Context mContext, String stid, String apikey,NetCallback<NetWorkResultBean<WQinfoPackge>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        WQApi git = restAdapter.create(WQApi.class);
        git.getWQInfo(stid, apikey, callback);
    }
}
