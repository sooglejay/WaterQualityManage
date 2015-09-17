package com.gaoxian.api.Production;

import android.content.Context;

import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.model.ProductionDataPackge;
import com.gaoxian.model.ProductionStatePackge;
import com.gaoxian.model.RetrofitUtil;

import retrofit.RestAdapter;

/**
 * Created by Administrator on 2015/9/12.
 */
public class GetProductionDataUtil extends RetrofitUtil {
    /**
     * 生产过程数据信息
     * @param mContext
     * @param stid
     * @param apikey
     * @param callback
     */
    public static void getProductionData(final Context mContext, String stid, String apikey, NetCallback<NetWorkResultBean<ProductionDataPackge>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        GetProductionDataApi git = restAdapter.create(GetProductionDataApi.class);
        git.getProductionData(stid, apikey, callback);
    }

    /**
     * 生产过程状态信息
     * @param mContext
     * @param stid
     * @param apikey
     * @param callback
     */
    public static void postProductionData(final Context mContext, String stid, String apikey, NetCallback<NetWorkResultBean<ProductionStatePackge>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        GetProductionDataApi git = restAdapter.create(GetProductionDataApi.class);
        git.postProductionState(stid, apikey, callback);
    }


}
