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

import com.gaoxian.Constant.PreferenceConstant;
import com.gaoxian.Constant.StringConstant;
import com.gaoxian.R;
import com.gaoxian.api.WQ.WQRetrofitUtil;
import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.model.WQinfo;
import com.gaoxian.model.WQinfoPackge;
import com.gaoxian.util.PreferenceUtil;
import com.gaoxian.widget.TitleBar;

import java.util.ArrayList;
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
                handler.postDelayed(this, 300000);//刷新频率为5分钟
                getWQInfo(getActivity());
            }
        };
        handler.postDelayed(runnable, 300000);//执行定时操作
    }

    /**
     * @param WQJCList  进水的水质数据结构
     * @param strJCinfo 进水的水质的等级信息
     */
    public void J_addView(List<WQinfo> WQJCList, String strJCinfo) {
        tv_j_water_level_str.setText(getResources().getString(R.string.j_water_level_str) + strJCinfo);
        J_Gallery.removeAllViews();
        int size = WQJCList.size();
        int y = size % 4;//对4取余
        int mode = size / 4;//对4取模
        for (int i = 0; i <= mode; i++) {
            LinearLayout horizentalLinearLayout = new LinearLayout(this.getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
            layoutParams.setMargins(12, 12, 12, 12);
            horizentalLinearLayout.setLayoutParams(layoutParams);
            if (i < mode) {
                for (int j = 0; j < 4; j++) {
                    View j_layout = mInflater.inflate(R.layout.item_water_info, J_Gallery, false);
                    Space space1 = new Space(this.getActivity());
                    TextView tv_top_name = (TextView) j_layout.findViewById(R.id.tv_top_name);
                    TextView tv_top_digit = (TextView) j_layout.findViewById(R.id.tv_top_digit);
                    TextView tv_top_unit = (TextView) j_layout.findViewById(R.id.tv_top_unit);
                    tv_top_name.setText(WQJCList.get(4 * i + j).getParameterName() + "");
                    tv_top_digit.setText(WQJCList.get(4 * i + j).getWQMonitorData() + "");
                    tv_top_unit.setText(WQJCList.get(4 * i + j).getUnit() + "");
                    j_layout.setPadding(12, 12, 12, 12);
                    j_layout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    space1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));
                    horizentalLinearLayout.addView(space1);
                    horizentalLinearLayout.addView(j_layout);
                }
                Space endSpace = new Space(this.getActivity());
                endSpace.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));
                horizentalLinearLayout.addView(endSpace);
            } else if (i == mode) {
                int p = 0;
                while (p < y) {
                    View j_layout = mInflater.inflate(R.layout.item_water_info, J_Gallery, false);
                    Space space1 = new Space(this.getActivity());
                    TextView tv_top_name = (TextView) j_layout.findViewById(R.id.tv_top_name);
                    TextView tv_top_digit = (TextView) j_layout.findViewById(R.id.tv_top_digit);
                    TextView tv_top_unit = (TextView) j_layout.findViewById(R.id.tv_top_unit);
                    tv_top_name.setText(WQJCList.get(4 * i + p).getParameterName() + "");
                    tv_top_digit.setText(WQJCList.get(4 * i + p).getWQMonitorData() + "");
                    tv_top_unit.setText(WQJCList.get(4 * i + p).getUnit() + "");
                    j_layout.setPadding(12, 12, 12, 12);
                    j_layout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    space1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));
                    horizentalLinearLayout.addView(space1);
                    horizentalLinearLayout.addView(j_layout);
                    p++;
                }
                int k = y;
                while (k < 4) {
                    View j_layout = mInflater.inflate(R.layout.item_water_info, J_Gallery, false);
                    Space space1 = new Space(this.getActivity());
                    TextView tv_top_name = (TextView) j_layout.findViewById(R.id.tv_top_name);
                    TextView tv_top_digit = (TextView) j_layout.findViewById(R.id.tv_top_digit);
                    TextView tv_top_unit = (TextView) j_layout.findViewById(R.id.tv_top_unit);
                    tv_top_name.setVisibility(View.GONE);
                    tv_top_digit.setText("无参数");
                    tv_top_unit.setVisibility(View.GONE);
                    j_layout.setPadding(12, 12, 12, 12);
                    j_layout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    space1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));
                    horizentalLinearLayout.addView(space1);
                    horizentalLinearLayout.addView(j_layout);
                    k++;
                }
                Space endSpace = new Space(this.getActivity());
                endSpace.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));
                horizentalLinearLayout.addView(endSpace);
            }
            J_Gallery.addView(horizentalLinearLayout);
        }


//        int size = WQJCList.size();
//        int width_4 = J_Gallery.getResources().getDisplayMetrics().widthPixels;
//        int half = size % 2 == 0 ? size / 2 : size / 2 + 1;
//        for (int i = 0; i < half; i++) {
//
//            View j_layout = mInflater.inflate(R.layout.item_water_info, J_Gallery, false);
//            j_layout.setLayoutParams(new LinearLayout.LayoutParams(width_4/4, ViewGroup.LayoutParams.MATCH_PARENT));
//            ( (TextView) j_layout.findViewById(R.id.tv_top_digit) ).setText(WQJCList.get(i).getWQMonitorData() + "");
//            ( (TextView) j_layout.findViewById(R.id.tv_top_unit) ).setText(WQJCList.get(i).getUnit()+"");
//            ( (TextView) j_layout.findViewById(R.id.tv_top_name) ).setText(WQJCList.get(i).getParameterName() + "");
//
//            if (half + i < size) {
//                ( (TextView) j_layout.findViewById(R.id.tv_bottom_digit) ).setText(WQJCList.get(half + i).getWQMonitorData() + "");
//                ( (TextView) j_layout.findViewById(R.id.tv_bottom_unit) ).setText(WQJCList.get(half + i).getUnit()+"");
//                ( (TextView) j_layout.findViewById(R.id.tv_bottom_name) ).setText(WQJCList.get(half + i).getParameterName() + "");
//
//            } else {
//                j_layout.findViewById(R.id.tv_bottom_digit).setVisibility(View.GONE);
//                j_layout.findViewById(R.id.tv_bottom_unit).setVisibility(View.GONE);
//                ((TextView) j_layout.findViewById(R.id.tv_bottom_name)).setText("无监控参数");
//            }
//
//            J_Gallery.addView(j_layout);
//        }
    }

    /**
     * @param WQCCList  出水的水质数据结构
     * @param strCCinfo 出水的水质的等级
     */
    public void C_addView(List<WQinfo> WQCCList, String strCCinfo) {
        tv_c_water_level_str.setText(getResources().getString(R.string.c_water_level_str) + strCCinfo);
        C_Gallery.removeAllViews();
        int size = WQCCList.size();
        int y = size % 4;//对4取余
        int mode = size / 4;//对4取模
        for (int i = 0; i <= mode; i++) {
            LinearLayout horizentalLinearLayout = new LinearLayout(this.getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
            layoutParams.setMargins(12, 12, 12, 12);
            horizentalLinearLayout.setLayoutParams(layoutParams);
            if (i < mode) {
                for (int j = 0; j < 4; j++) {
                    View j_layout = mInflater.inflate(R.layout.item_water_info, C_Gallery, false);
                    Space space1 = new Space(this.getActivity());
                    TextView tv_top_name = (TextView) j_layout.findViewById(R.id.tv_top_name);
                    TextView tv_top_digit = (TextView) j_layout.findViewById(R.id.tv_top_digit);
                    TextView tv_top_unit = (TextView) j_layout.findViewById(R.id.tv_top_unit);
                    tv_top_name.setText(WQCCList.get(4 * i + j).getParameterName() + "");
                    tv_top_digit.setText(WQCCList.get(4 * i + j).getWQMonitorData() + "");
                    tv_top_unit.setText(WQCCList.get(4 * i + j).getUnit() + "");
                    j_layout.setPadding(12, 12, 12, 12);
                    j_layout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    space1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));
                    horizentalLinearLayout.addView(space1);
                    horizentalLinearLayout.addView(j_layout);
                }
                Space endSpace = new Space(this.getActivity());
                endSpace.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));
                horizentalLinearLayout.addView(endSpace);
            } else if (i == mode) {
                int p = 0;
                while (p < y) {
                    View j_layout = mInflater.inflate(R.layout.item_water_info, C_Gallery, false);
                    Space space1 = new Space(this.getActivity());
                    TextView tv_top_name = (TextView) j_layout.findViewById(R.id.tv_top_name);
                    TextView tv_top_digit = (TextView) j_layout.findViewById(R.id.tv_top_digit);
                    TextView tv_top_unit = (TextView) j_layout.findViewById(R.id.tv_top_unit);
                    tv_top_name.setText(WQCCList.get(4 * i + p).getParameterName() + "");
                    tv_top_digit.setText(WQCCList.get(4 * i + p).getWQMonitorData() + "");
                    tv_top_unit.setText(WQCCList.get(4 * i + p).getUnit() + "");
                    j_layout.setPadding(12, 12, 12, 12);
                    j_layout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    space1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));
                    horizentalLinearLayout.addView(space1);
                    horizentalLinearLayout.addView(j_layout);
                    p++;
                }
                int k = y;
                while (k < 4) {
                    View j_layout = mInflater.inflate(R.layout.item_water_info, C_Gallery, false);
                    Space space1 = new Space(this.getActivity());
                    TextView tv_top_name = (TextView) j_layout.findViewById(R.id.tv_top_name);
                    TextView tv_top_digit = (TextView) j_layout.findViewById(R.id.tv_top_digit);
                    TextView tv_top_unit = (TextView) j_layout.findViewById(R.id.tv_top_unit);
                    tv_top_name.setVisibility(View.GONE);
                    tv_top_digit.setText("无参数");
                    tv_top_unit.setVisibility(View.GONE);
                    j_layout.setPadding(12, 12, 12, 12);
                    j_layout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    space1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));
                    horizentalLinearLayout.addView(space1);
                    horizentalLinearLayout.addView(j_layout);
                    k++;
                }
                Space endSpace = new Space(this.getActivity());
                endSpace.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));
                horizentalLinearLayout.addView(endSpace);
            }
            C_Gallery.addView(horizentalLinearLayout);
        }

//        int size = WQCCList.size();
//        int width_4 = C_Gallery.getResources().getDisplayMetrics().widthPixels;
//        int half = size % 2 == 0 ? size / 2 : size / 2 + 1;
//        for (int i = 0; i < half; i++) {
//
//            View c_layout = mInflater.inflate(R.layout.item_water_info,
//                    C_Gallery, false);
//
//            c_layout.setLayoutParams(new LinearLayout.LayoutParams(width_4/4, ViewGroup.LayoutParams.MATCH_PARENT));
//            ( (TextView) c_layout.findViewById(R.id.tv_top_digit) ).setText(WQCCList.get(i).getWQMonitorData() + "");
//            ( (TextView) c_layout.findViewById(R.id.tv_top_unit) ).setText(WQCCList.get(i).getUnit()+"");
//            ( (TextView) c_layout.findViewById(R.id.tv_top_name) ).setText(WQCCList.get(i).getParameterName() + "");
//
//            if (half + i < size) {
//                ( (TextView) c_layout.findViewById(R.id.tv_bottom_digit) ).setText(WQCCList.get(half + i).getWQMonitorData() + "");
//                ( (TextView) c_layout.findViewById(R.id.tv_bottom_unit) ).setText(WQCCList.get(half + i).getUnit()+"");
//                ( (TextView) c_layout.findViewById(R.id.tv_bottom_name) ).setText(WQCCList.get(half + i).getParameterName() + "");
//
//            } else {
//                c_layout.findViewById(R.id.tv_bottom_digit).setVisibility(View.GONE);
//                c_layout.findViewById(R.id.tv_bottom_unit).setVisibility(View.GONE);
//                ((TextView) c_layout.findViewById(R.id.tv_bottom_name)).setText("无监控参数");
//            }
//            C_Gallery.addView(c_layout);
//        }
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

                WQinfoPackge test = wQinfoPackgeNetWorkResultBean.getData();
                J_addView(test.getWQJCList(), test.getJCInfo());
                C_addView(test.getWQCCList(), test.getCCinfo());
            }
        });
    }

}
