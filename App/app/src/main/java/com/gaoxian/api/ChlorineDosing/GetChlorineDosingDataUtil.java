package com.gaoxian.api.ChlorineDosing;

import android.content.Context;

import com.gaoxian.api.WQ.WQApi;
import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.model.ChlorineDosingData;
import com.gaoxian.model.ChlorineDosingDataPackge;
import com.gaoxian.model.ChlorineDosingStatePackge;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.model.WQinfo;
import com.gaoxian.util.RetrofitUtil;

import retrofit.RestAdapter;

/**
 * Created by Administrator on 2015/9/12.
 */
public class GetChlorineDosingDataUtil extends RetrofitUtil {

    /**
     * 加氯加药数据信息
     * @param mContext
     * @param stid
     * @param apikey
     * @param callback
     */
    public static void getChlorineDosingData(final Context mContext, String stid, String apikey, NetCallback<NetWorkResultBean<ChlorineDosingDataPackge>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        GetChlorineDosingDataApi git = restAdapter.create(GetChlorineDosingDataApi.class);
        git.getChlorineDosingData(stid, apikey, callback);
    }

    /**
     * 加氯加药状态信息
     * @param mContext
     * @param stid
     * @param apikey
     * @param callback
     */
    public static void postChlorineDosingData(final Context mContext, String stid, String apikey, NetCallback<NetWorkResultBean<ChlorineDosingStatePackge>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        GetChlorineDosingDataApi git = restAdapter.create(GetChlorineDosingDataApi.class);
        git.postChlorineDosingState(stid, apikey, callback);
    }

}
