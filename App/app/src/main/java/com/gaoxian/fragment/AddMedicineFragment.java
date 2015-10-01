package com.gaoxian.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gaoxian.Constant.NetWorkConstant;
import com.gaoxian.Constant.PreferenceConstant;
import com.gaoxian.Constant.StringConstant;
import com.gaoxian.R;
import com.gaoxian.api.ChlorineDosing.GetChlorineDosingDataUtil;
import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.events.IntEvent;
import com.gaoxian.model.ChlorineDosingData;
import com.gaoxian.model.ChlorineDosingDataPackge;
import com.gaoxian.model.ChlorineDosingState;
import com.gaoxian.model.ChlorineDosingStatePackge;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.util.DoubleClickListener;
import com.gaoxian.util.PreferenceUtil;
import com.gaoxian.widget.ScaleView.MultiTouchListener;
import com.gaoxian.widget.TitleBar;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit.RetrofitError;
import retrofit.client.Response;


public class AddMedicineFragment extends BaseFragment {
    private float originalScaleX, originalScaleY, originalTranslateX, originalTranslateY;

    private TitleBar titleBar;
    private RelativeLayout layout_view;

    private TextView tv_YL01;//余氟 1
    private TextView tv_YL02;//余氟 2
    private TextView tv_EYHLYE01;//二氧化氯中转站液位
    private TextView tv_JLBPL01;//二氧化氯中转站泵频率01
    private TextView tv_JLBPL02;//二氧化氯中转站泵频率02
    private TextView tv_CQL01;//二氧化氯产气量01
    private TextView tv_CQL02;//二氧化氯产气量02


    public ImageView iv_QS01;//表格中的两组 ，每组四个图片 ，状态
    public ImageView iv_QS02;

    public ImageView iv_QY01;
    public ImageView iv_QY02;

    public ImageView iv_CW01;
    public ImageView iv_CW02;

    public ImageView iv_YX01;
    public ImageView iv_YX02;

    public ImageView iv_DLSFM01;
    public ImageView iv_DLSFM02;

    public ImageView iv_JLBZT01;
    public ImageView iv_JLBZT02;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
        setUpListener();
        getChlorineDosingData();
        getChlorineDosingState();
    }

    private void setUp(View view, Bundle savedInstanceState) {
        titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo(PreferenceUtil.load(this.getActivity(), PreferenceConstant.StationName, StringConstant.defaultStationName),
                StringConstant.tabAddMedicine);

        layout_view = (RelativeLayout) view.findViewById(R.id.layout_view);
        getLayoutParams(layout_view);

        tv_YL01 = (TextView) view.findViewById(R.id.tv_YL01);
        tv_YL02 = (TextView) view.findViewById(R.id.tv_YL02);
        tv_EYHLYE01 = (TextView) view.findViewById(R.id.tv_EYHLYE01);
        tv_JLBPL01 = (TextView) view.findViewById(R.id.tv_JLBPL01);
        tv_JLBPL02 = (TextView) view.findViewById(R.id.tv_JLBPL02);
        tv_CQL01 = (TextView) view.findViewById(R.id.tv_CQL01);
        tv_CQL02 = (TextView) view.findViewById(R.id.tv_CQL02);

        iv_QS01 = (ImageView) view.findViewById(R.id.iv_QS01);
        iv_QS02 = (ImageView) view.findViewById(R.id.iv_QS02);

        iv_QY01 = (ImageView) view.findViewById(R.id.iv_QY01);
        iv_QY02 = (ImageView) view.findViewById(R.id.iv_QY02);

        iv_CW01 = (ImageView) view.findViewById(R.id.iv_CW01);
        iv_CW02 = (ImageView) view.findViewById(R.id.iv_CW02);

        iv_YX01 = (ImageView) view.findViewById(R.id.iv_YX01);
        iv_YX02 = (ImageView) view.findViewById(R.id.iv_YX02);

        iv_DLSFM01 = (ImageView) view.findViewById(R.id.iv_DLSFM01);
        iv_DLSFM02 = (ImageView) view.findViewById(R.id.iv_DLSFM02);

        iv_JLBZT01 = (ImageView) view.findViewById(R.id.iv_JLBZT01);
        iv_JLBZT02 = (ImageView) view.findViewById(R.id.iv_JLBZT02);

    }

    private void setUpListener() {
        layout_view.setOnTouchListener(new MultiTouchListener());
        layout_view.setOnClickListener(new DoubleClickListener() {
            @Override
            public void onSingleClick(final View v) {

                final Handler handler = new Handler();
                final Runnable mRunnable = new Runnable() {
                    public void run() {
                        processSingleClickEvent(v); //Do what ever u want on single click

                    }
                };

                TimerTask timertask = new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(mRunnable);
                    }
                };
                timer = new Timer();
                timer.schedule(timertask, DOUBLE_CLICK_TIME_DELTA);

            }

            @Override
            public void onDoubleClick(View v) {
                if (timer != null) {
                    timer.cancel(); //Cancels Running Tasks or Waiting Tasks.
                    timer.purge();  //Frees Memory by erasing cancelled Tasks.
                }
                processDoubleClickEvent(v);//Do what ever u want on Double Click

            }
        });
    }

    private void processSingleClickEvent(View v) {
//        Log.e("jwjw", "single tap");
    }

    private void processDoubleClickEvent(View v) {
//        Log.e("jwjw", "double tap");
        resetLayoutParams(layout_view, originalScaleX, originalScaleY, originalTranslateX, originalTranslateY);
    }

    private void getLayoutParams(View layout_view) {
        originalScaleX = layout_view.getScaleX();
        originalScaleY = layout_view.getScaleY();
        originalTranslateX = layout_view.getTranslationX();
        originalTranslateY = layout_view.getTranslationY();
    }

    public void resetLayoutParams(View layout_view, float originalScaleX, float originalScaleY, float originalTranslateX, float originalTranslateY) {
        layout_view.setTranslationX(originalTranslateX);
        layout_view.setTranslationY(originalTranslateY);
        layout_view.setScaleX(originalScaleX);
        layout_view.setScaleY(originalScaleY);
    }

    public void onEventMainThread(IntEvent event) {

        switch (event.getMsg()) {
            case IntEvent.Msg_ResetViewScale:
                if (layout_view != null) {
                    resetLayoutParams(layout_view, originalScaleX, originalScaleY, originalTranslateX, originalTranslateY);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 测试加氟加药
     */
    private void getChlorineDosingData() {
        GetChlorineDosingDataUtil.getChlorineDosingData(AddMedicineFragment.this.getActivity(), PreferenceUtil.load(AddMedicineFragment.this.getActivity(), PreferenceConstant.AreaCode, ""), StringConstant.apiKey, new NetCallback<NetWorkResultBean<ChlorineDosingDataPackge>>(AddMedicineFragment.this.getActivity()) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<ChlorineDosingDataPackge> chlorineDosingDataPackgeNetWorkResultBean, Response response) {
                ChlorineDosingDataPackge test = chlorineDosingDataPackgeNetWorkResultBean.getData();
                Log.e("jwjw", test.toString());
                List<ChlorineDosingData> dataList = test.getCDDataList();
                for (ChlorineDosingData bean : dataList) {
                    if (bean.getJLJYCode().equals(NetWorkConstant.YL01)) {
                        tv_YL01.setText("" + bean.getJLJYData());
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.YL02)) {
                        tv_YL02.setText("" + bean.getJLJYData());

                    } else if (bean.getJLJYCode().equals(NetWorkConstant.EYHLYE01)) {
                        tv_EYHLYE01.setText("" + bean.getJLJYData());
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.CQL01)) {
                        tv_CQL01.setText("" + bean.getJLJYData());

                    } else if (bean.getJLJYCode().equals(NetWorkConstant.CQL02)) {
                        tv_CQL02.setText("" + bean.getJLJYData());

                    } else if (bean.getJLJYCode().equals(NetWorkConstant.JLBPL01)) {
                        tv_JLBPL01.setText("" + bean.getJLJYData());

                    } else if (bean.getJLJYCode().equals(NetWorkConstant.JLBPL02)) {
                        tv_JLBPL01.setText("" + bean.getJLJYData());

                    }
                }
            }
        });
    }

    /**
     * 获取加氯加药状态信息
     */
    private void getChlorineDosingState() {
        GetChlorineDosingDataUtil.getChlorineDosingState(AddMedicineFragment.this.getActivity(), PreferenceUtil.load(AddMedicineFragment.this.getActivity(), PreferenceConstant.AreaCode, ""), StringConstant.apiKey, new NetCallback<NetWorkResultBean<ChlorineDosingStatePackge>>(AddMedicineFragment.this.getActivity()) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<ChlorineDosingStatePackge> chlorineDosingStatePackgeNetWorkResultBean, Response response) {
                ChlorineDosingStatePackge test = chlorineDosingStatePackgeNetWorkResultBean.getData();
                List<ChlorineDosingState> dataList = test.getCDStateList();
                Log.e("jwjw", test.toString());

                for (ChlorineDosingState bean : dataList) {
                    if (bean.getJLJYCode().equals(NetWorkConstant.QS01)) {
                        switch (bean.getJLJYState()) {
                            case 0:
                                iv_QS01.setImageResource(R.drawable.circle_flag_green);
                                break;
                            case 1:
                                iv_QS01.setImageResource(R.drawable.circle_flag_red);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.QS02)) {
                        switch (bean.getJLJYState()) {
                            case 0:
                                iv_QS02.setImageResource(R.drawable.fan_right_green);
                                break;
                            case 1:
                                iv_QS02.setImageResource(R.drawable.fan_right_gray);
                                break;
                             case 2:
                                iv_QS02.setImageResource(R.drawable.fan_right_red);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.QY01)) {
                        switch (bean.getJLJYState()) {
                            case 0:
                                iv_QY01.setImageResource(R.drawable.circle_flag_green);
                                break;
                            case 1:
                                iv_QY01.setImageResource(R.drawable.circle_flag_red);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.QY02)) {
                        switch (bean.getJLJYState()) {
                            case 0:
                                iv_QY02.setImageResource(R.drawable.fan_left_green);
                                break;
                            case 1:
                                iv_QY02.setImageResource(R.drawable.fan_left_gray);
                                break;
                            case 2:
                                iv_QY02.setImageResource(R.drawable.fan_left_red);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.CW01)) {
                        switch (bean.getJLJYState()) {
                            case 0:
                                iv_CW01.setImageResource(R.drawable.circle_flag_green);
                                break;
                            case 1:
                                iv_CW01.setImageResource(R.drawable.circle_flag_red);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.CW02)) {
                        switch (bean.getJLJYState()) {
                            case 0:
                                iv_CW02.setImageResource(R.drawable.fan_right_green);
                                break;
                            case 1:
                                iv_CW02.setImageResource(R.drawable.fan_right_gray);
                                break;
                            case 2:
                                iv_CW02.setImageResource(R.drawable.fan_right_red);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.YX01)) {
                        switch (bean.getJLJYState()) {
                            case 0:
                                iv_YX01.setImageResource(R.drawable.circle_flag_green);
                                break;
                            case 1:
                                iv_YX01.setImageResource(R.drawable.circle_flag_red);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.YX02)) {
                        switch (bean.getJLJYState()) {
                            case 0:
                                iv_YX02.setImageResource(R.drawable.fan_left_green);
                                break;
                            case 1:
                                iv_YX02.setImageResource(R.drawable.fan_left_gray);
                                break;
                            case 2:
                                iv_YX02.setImageResource(R.drawable.fan_left_red);
                                break;
                            default:
                                break;
                        }
                    }else if (bean.getJLJYCode().equals(NetWorkConstant.DLSFM01)) {
                        switch (bean.getJLJYState()) {
                            case 0:
                                iv_DLSFM01.setImageResource(R.drawable.valve_up_green);
                                break;
                            case 1:
                                iv_DLSFM01.setImageResource(R.drawable.valve_up_gray);
                                break;
                            case 2:
                                iv_DLSFM01.setImageResource(R.drawable.valve_up_red);
                                break;
                            default:
                                break;
                        }
                    }else if (bean.getJLJYCode().equals(NetWorkConstant.DLSFM02)) {
                        switch (bean.getJLJYState()) {
                            case 0:
                                iv_DLSFM02.setImageResource(R.drawable.valve_up_green);
                                break;
                            case 1:
                                iv_DLSFM02.setImageResource(R.drawable.valve_up_gray);
                                break;
                            case 2:
                                iv_DLSFM02.setImageResource(R.drawable.valve_up_red);
                                break;
                            default:
                                break;
                        }
                    }else if (bean.getJLJYCode().equals(NetWorkConstant.JLBZT01)) {
                        switch (bean.getJLJYState()) {
                            case 0:
                                iv_JLBZT01.setImageResource(R.drawable.valve_left_green);
                                break;
                            case 1:
                                iv_JLBZT01.setImageResource(R.drawable.valve_left_gray);
                                break;
                            case 2:
                                iv_JLBZT01.setImageResource(R.drawable.valve_left_red);
                                break;
                            default:
                                break;
                        }
                    }else if (bean.getJLJYCode().equals(NetWorkConstant.JLBZT02)) {
                        switch (bean.getJLJYState()) {
                            case 0:
                                iv_JLBZT02.setImageResource(R.drawable.valve_left_green);
                                break;
                            case 1:
                                iv_JLBZT02.setImageResource(R.drawable.valve_left_gray);
                                break;
                            case 2:
                                iv_JLBZT02.setImageResource(R.drawable.valve_left_red);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }

        });
    }
}
