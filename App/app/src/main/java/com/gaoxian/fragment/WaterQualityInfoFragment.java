package com.gaoxian.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.Space;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaoxian.Constant.IntConstant;
import com.gaoxian.Constant.PreferenceConstant;
import com.gaoxian.Constant.StringConstant;
import com.gaoxian.R;
import com.gaoxian.api.WQ.WQRetrofitUtil;
import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.model.WQinfo;
import com.gaoxian.model.WQinfoPackge;
import com.gaoxian.util.PreferenceUtil;
import com.gaoxian.util.UIUtils;
import com.gaoxian.widget.TitleBar;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;


public class WaterQualityInfoFragment extends BaseFragment {

    //定时操作
    final Handler handler = new Handler();
    private Runnable runnable;

    private TitleBar titleBar;
    private TextView tv_j_water_level_str;
    private TextView tv_c_water_level_str;

    private LinearLayout J_Gallery;//进水
    private LinearLayout C_Gallery;//出水
    private LayoutInflater mInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_3, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
        getWQInfo(this.getActivity());
    }

    private void setUp(View view, Bundle savedInstanceState) {
        titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo(PreferenceUtil.load(this.getActivity(), PreferenceConstant.StationName, StringConstant.defaultStationName),
                StringConstant.tabWaterQualityInfo);

        mInflater = LayoutInflater.from(this.getActivity());
        J_Gallery = (LinearLayout) view.findViewById(R.id.id_j_gallery);
        C_Gallery = (LinearLayout) view.findViewById(R.id.id_c_gallery);

        //进厂和出厂水质 等级 TextView
        tv_j_water_level_str = (TextView) view.findViewById(R.id.tv_j_water_level_str);
        tv_c_water_level_str = (TextView) view.findViewById(R.id.tv_c_water_level_str);

        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, IntConstant.refreshIntervalFiveMinute);//刷新频率为5分钟
                getWQInfo(getActivity());
            }
        };
        handler.postDelayed(runnable, IntConstant.refreshIntervalFiveMinute);//执行定时操作
    }

    /**
     * @param wQinfoListDatas 水质信息数据结构
     * @param layoutGroup     展示水质信息的顶层布局
     */
    public void addView(List<WQinfo> wQinfoListDatas, LinearLayout layoutGroup) {
        layoutGroup.removeAllViews();
        int marginBottom = (int) UIUtils.dp2px(this.getActivity(),10);
        int size = wQinfoListDatas.size();
        int y = size % 4;//对4取余
        int mode = size / 4;//对4取模
        for (int i = 0; i <= mode; i++) {
            LinearLayout horizentalLinearLayout = new LinearLayout(this.getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1.0f);
            if (i < mode) {
                layoutParams.setMargins(0,0,0, marginBottom);
                horizentalLinearLayout.setLayoutParams(layoutParams);
                for (int j = 0; j < 4; j++) {
                    View c_layout = mInflater.inflate(R.layout.item_water_info, layoutGroup, false);
                    Space space1 = new Space(this.getActivity());
                    TextView tv_name = (TextView) c_layout.findViewById(R.id.tv_name);
                    TextView tv_digit = (TextView) c_layout.findViewById(R.id.tv_digit);
                    TextView tv_unit = (TextView) c_layout.findViewById(R.id.tv_unit);
                    tv_name.setText(wQinfoListDatas.get(4 * i + j).getParameterName() + "");
                    tv_digit.setText(wQinfoListDatas.get(4 * i + j).getWQMonitorData() + "");
                    tv_unit.setText(wQinfoListDatas.get(4 * i + j).getUnit() + "");
                    c_layout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    space1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));
                    horizentalLinearLayout.addView(space1);
                    horizentalLinearLayout.addView(c_layout);
                }
                Space endSpace = new Space(this.getActivity());
                endSpace.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));
                horizentalLinearLayout.addView(endSpace);

            } else if (i == mode) {
                //最后一行的不设置bottom
                layoutParams.setMargins(0,0,0,0);
                horizentalLinearLayout.setLayoutParams(layoutParams);
                int p = 0;
                while (p < y) {
                    View c_layout = mInflater.inflate(R.layout.item_water_info, layoutGroup, false);
                    Space space1 = new Space(this.getActivity());
                    TextView tv_top_name = (TextView) c_layout.findViewById(R.id.tv_name);
                    TextView tv_top_digit = (TextView) c_layout.findViewById(R.id.tv_digit);
                    TextView tv_top_unit = (TextView) c_layout.findViewById(R.id.tv_unit);
                    tv_top_name.setText(wQinfoListDatas.get(4 * i + p).getParameterName() + "");
                    tv_top_digit.setText(wQinfoListDatas.get(4 * i + p).getWQMonitorData() + "");
                    tv_top_unit.setText(wQinfoListDatas.get(4 * i + p).getUnit() + "");
                    c_layout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    space1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));
                    horizentalLinearLayout.addView(space1);
                    horizentalLinearLayout.addView(c_layout);
                    p++;
                }
                int k = y;
                while (k < 4) {
                    View c_layout = mInflater.inflate(R.layout.item_water_info, layoutGroup, false);
                    Space space1 = new Space(this.getActivity());
                    TextView tv_top_name = (TextView) c_layout.findViewById(R.id.tv_name);
                    TextView tv_top_digit = (TextView) c_layout.findViewById(R.id.tv_digit);
                    TextView tv_top_unit = (TextView) c_layout.findViewById(R.id.tv_unit);
                    tv_top_name.setText("");
                    tv_top_digit.setText("无参数");
                    tv_top_unit.setText("");
                    c_layout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1.0f));
                    space1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));
                    horizentalLinearLayout.addView(space1);
                    horizentalLinearLayout.addView(c_layout);
                    k++;
                }
                Space endSpace = new Space(this.getActivity());
                endSpace.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));
                horizentalLinearLayout.addView(endSpace);
            }
            layoutGroup.addView(horizentalLinearLayout);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }


    /**
     * 获取服务端  WQ / 水质信息
     */
    private void getWQInfo(Context context) {
        WQRetrofitUtil.getWQInfo(context, "511525001", "weiqi", new NetCallback<NetWorkResultBean<WQinfoPackge>>(context) {
            @Override
            public void onFailure(RetrofitError error) {
            }

            @Override
            public void success(NetWorkResultBean<WQinfoPackge> wQinfoPackgeNetWorkResultBean, Response response) {
                setWaterQualityInfo(wQinfoPackgeNetWorkResultBean.getData());
            }
        });
    }

    /**
     * 设置水质信息
     * @param bean
     */
    private void setWaterQualityInfo(WQinfoPackge bean) {
        //进水水质
        tv_j_water_level_str.setText(getResources().getString(R.string.j_water_level_str) + bean.getJCInfo());
        addView(bean.getWQJCList(), J_Gallery);

        //出水水质
        tv_c_water_level_str.setText(getResources().getString(R.string.c_water_level_str) + bean.getJCInfo());
        addView(bean.getWQCCList(), C_Gallery);
    }

}
