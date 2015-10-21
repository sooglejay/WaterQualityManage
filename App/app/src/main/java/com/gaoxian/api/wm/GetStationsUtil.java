package com.gaoxian.api.wm;

import android.content.Context;

import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.model.StationInfoPackge;
import com.gaoxian.model.RetrofitUtil;

import retrofit.RestAdapter;

/**
 * Created by kcg on 15-7-7.
 */
public class GetStationsUtil extends RetrofitUtil {

    /**
     * 接口描述：录入电话号码，获取短信验证码！
     * @param mContext
     * @param areacode
     * @param apikey
     * @param callback
     */
    public static void getStations(final Context mContext, String areacode, String apikey, NetCallback<NetWorkResultBean<StationInfoPackge>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        GetStationsApi git = restAdapter.create(GetStationsApi.class);
        git.getStations(areacode,apikey,callback);
    }
}
