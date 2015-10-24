package com.gaoxian.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaoxian.constant.IntConstant;
import com.gaoxian.constant.NetWorkConstant;
import com.gaoxian.constant.PreferenceConstant;
import com.gaoxian.constant.StringConstant;
import com.gaoxian.R;
import com.gaoxian.api.chlorineDosing.GetChlorineDosingDataUtil;
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

import de.greenrobot.event.EventBus;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AddMedicineFragment extends BaseFragment {
    private float originalScaleX, originalScaleY, originalTranslateX, originalTranslateY;

    private TitleBar titleBar;
    private LinearLayout layout_view;
    private View layout_bottom;//this layout is a bottom of  the imageView background

    //定时操作
    final Handler handler=new Handler();
    private Runnable runnable;

    //数字信息    加氟加药数据
    private TextView tv_YL01;//余氟 1
    private TextView tv_YL02;//余氟 2
    private TextView tv_EYHLYE01;//二氧化氯中转站液位

    private TextView tv_JLBPL01;//二氧化氯中转站泵频率01
    private TextView tv_JYBPL01;//加药计量泵频率01

    private TextView tv_JLBPL02;//二氧化氯中转站泵频率02
    private TextView tv_JYBPL02;//加药计量泵频率02

    private TextView tv_CQL01;//二氧化氯产气量01
    private TextView tv_CQL02;//二氧化氯产气量02


    //图片状态信息   加氟加药状态
    public ImageView iv_QS01;//表格中的两组 ，每组四个图片 ，状态
    public ImageView iv_QS02;//二氧化氯缺水状态02

    public ImageView iv_QY01;
    public ImageView iv_QY02;//二氧化氯欠压状态02

    public ImageView iv_CW01;//二氧化氯超温状态02
    public ImageView iv_CW02;

    public ImageView iv_YX01;
    public ImageView iv_YX02;//二氧化氯运行状态02

    public ImageView iv_DLSFM01;//动力水阀门02
    public ImageView iv_DLSFM02;

    public ImageView iv_JLBZT01;//加药剂量泵状态01
    public ImageView iv_JLBZT02;

    public ImageView iv_ZZZB01;//二氧化氯中转站泵状态01
    public ImageView iv_ZZZB02;

    public ImageView iv_JYSBZT01;//加药设备运行状态01  只有一个设置
    public ImageView iv_JYSBZT01_error;//加药设备运行状态01  如果出现故障，下面的故障灯要变红


    private TextView tv_PSJYW01;//配水井1
    private TextView tv_PSJYW02;//配水井2
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mContext = view.getContext().getApplicationContext();
        setUp(view, savedInstanceState);
        setUpListener();


        try {
            getChlorineDosingData();
            getChlorineDosingState();
        }catch (NullPointerException npe)
        {
            Log.e("jwjw", "加氟加药-空指针！");
            mContext = getActivity().getApplicationContext();
        }



        runnable=new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this,IntConstant.refreshIntervalOneMinute);//刷新频率为1分钟
                try {
                    getChlorineDosingData();
                    getChlorineDosingState();
                }catch (NullPointerException npe)
                {
                    Log.e("jwjw", "加氟加药-空指针！");
                    mContext = getActivity().getApplicationContext();
                }
            }
        };
        handler.postDelayed(runnable,IntConstant.refreshIntervalOneMinute);//执行定时操作
    }

    private void setUp(View view, Bundle savedInstanceState) {
        titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo(PreferenceUtil.load(this.getActivity(), PreferenceConstant.StationName, StringConstant.defaultStationName));

        layout_bottom = (View) view.findViewById(R.id.layout_bottom);
        layout_view = (LinearLayout) view.findViewById(R.id.layout_view);
        getLayoutParams(layout_view);

        //数据
        tv_YL01 = (TextView) view.findViewById(R.id.tv_YL01);
        tv_YL02 = (TextView) view.findViewById(R.id.tv_YL02);
        tv_EYHLYE01 = (TextView) view.findViewById(R.id.tv_EYHLYE01);
        tv_JLBPL01 = (TextView) view.findViewById(R.id.tv_JLBPL01);
        tv_JLBPL02 = (TextView) view.findViewById(R.id.tv_JLBPL02);
        tv_JYBPL01 = (TextView) view.findViewById(R.id.tv_JYBPL01);
        tv_JYBPL02 = (TextView) view.findViewById(R.id.tv_JYBPL02);
        tv_CQL01 = (TextView) view.findViewById(R.id.tv_CQL01);
        tv_CQL02 = (TextView) view.findViewById(R.id.tv_CQL02);


        //状态
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

        iv_ZZZB01 = (ImageView) view.findViewById(R.id.iv_ZZZB01);
        iv_ZZZB02 = (ImageView) view.findViewById(R.id.iv_ZZZB02);

        iv_JLBZT01 = (ImageView) view.findViewById(R.id.iv_JLBZT01);
        iv_JLBZT02 = (ImageView) view.findViewById(R.id.iv_JLBZT02);
        iv_JYSBZT01 = (ImageView) view.findViewById(R.id.iv_JYSBZT01);
        iv_JYSBZT01_error = (ImageView) view.findViewById(R.id.iv_JYSBZT01_error);


        tv_PSJYW01 = (TextView) view.findViewById(R.id.tv_PSJYW01);
        tv_PSJYW02 = (TextView) view.findViewById(R.id.tv_PSJYW02);


    }

    private void setUpListener() {
        layout_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new IntEvent(IntEvent.Msg_Enable_ViewPager_Scroll));
            }
        });
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
        EventBus.getDefault().post(new IntEvent(IntEvent.Msg_Enable_ViewPager_Scroll));
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
            case IntEvent.Msg_RefreshData:
                if (tv_PSJYW01 != null && isAdded()) {
                    tv_PSJYW01.setText(PreferenceUtil.load(mContext, PreferenceConstant.psj1, "")+"");
                    tv_PSJYW02.setText(PreferenceUtil.load(mContext, PreferenceConstant.psj2, "")+"");
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
        GetChlorineDosingDataUtil.getChlorineDosingData(mContext, PreferenceUtil.load(mContext, PreferenceConstant.AreaCode, ""), StringConstant.weiqi, new NetCallback<NetWorkResultBean<ChlorineDosingDataPackge>>(mContext) {
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

                    } else if (bean.getJLJYCode().equals(NetWorkConstant.JYBPL01)) {
                        tv_JYBPL01.setText("" + bean.getJLJYData());

                    } else if (bean.getJLJYCode().equals(NetWorkConstant.JYBPL02)) {
                        tv_JYBPL02.setText("" + bean.getJLJYData());

                    } else if (bean.getJLJYCode().equals(NetWorkConstant.JLBPL01)) {
                        tv_JLBPL01.setText("" + bean.getJLJYData());

                    } else if (bean.getJLJYCode().equals(NetWorkConstant.JLBPL02)) {
                        tv_JLBPL02.setText("" + bean.getJLJYData());

                    }
                }
            }
        });
    }

    /**
     * 获取加氯加药状态信息
     */
    private void getChlorineDosingState() {
        GetChlorineDosingDataUtil.getChlorineDosingState(mContext, PreferenceUtil.load(mContext, PreferenceConstant.AreaCode, ""), StringConstant.weiqi, new NetCallback<NetWorkResultBean<ChlorineDosingStatePackge>>(mContext) {
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
                            case IntConstant.State_open:
                                iv_QS01.setImageResource(R.drawable.circle_flag_green);
                                break;
                            case IntConstant.State_close:
                                iv_QS01.setImageResource(R.drawable.circle_flag_red);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.QS02)) {
                        switch (bean.getJLJYState()) {
                            case IntConstant.State_open:
                                iv_QS02.setImageResource(R.drawable.circle_flag_green);
                                break;
                            case IntConstant.State_close:
                                iv_QS02.setImageResource(R.drawable.circle_flag_red);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.QY01)) {
                        switch (bean.getJLJYState()) {
                            case IntConstant.State_open:
                                iv_QY01.setImageResource(R.drawable.circle_flag_green);
                                break;
                            case IntConstant.State_close:
                                iv_QY01.setImageResource(R.drawable.circle_flag_red);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.QY02)) {
                        switch (bean.getJLJYState()) {
                            case IntConstant.State_open:
                                iv_QY02.setImageResource(R.drawable.circle_flag_green);
                                break;
                            case IntConstant.State_close:
                                iv_QY02.setImageResource(R.drawable.circle_flag_red);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.CW01)) {
                        switch (bean.getJLJYState()) {
                            case IntConstant.State_open:
                                iv_CW01.setImageResource(R.drawable.circle_flag_green);
                                break;
                            case IntConstant.State_close:
                                iv_CW01.setImageResource(R.drawable.circle_flag_red);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.CW02)) {
                        switch (bean.getJLJYState()) {
                            case IntConstant.State_open:
                                iv_CW02.setImageResource(R.drawable.circle_flag_green);
                                break;
                            case IntConstant.State_close:
                                iv_CW02.setImageResource(R.drawable.circle_flag_red);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.YX01)) {
                        switch (bean.getJLJYState()) {
                            case IntConstant.State_open:
                                iv_YX01.setImageResource(R.drawable.circle_flag_green);
                                break;
                            case IntConstant.State_close:
                                iv_YX01.setImageResource(R.drawable.circle_flag_red);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.YX02)) {
                        switch (bean.getJLJYState()) {
                            case IntConstant.State_open:
                                iv_YX02.setImageResource(R.drawable.circle_flag_green);
                                break;
                            case IntConstant.State_close:
                                iv_YX02.setImageResource(R.drawable.circle_flag_red);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.DLSFM01)) {
                        switch (bean.getJLJYState()) {
                            case IntConstant.State_open:
                                iv_DLSFM01.setImageResource(R.drawable.valve_up_green);
                                break;
                            case IntConstant.State_close:
                                iv_DLSFM01.setImageResource(R.drawable.valve_up_gray);
                                break;
                            case IntConstant.State_error:
                                iv_DLSFM01.setImageResource(R.drawable.valve_up_red);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.DLSFM02)) {
                        switch (bean.getJLJYState()) {
                            case IntConstant.State_open:
                                iv_DLSFM02.setImageResource(R.drawable.valve_up_green);
                                break;
                            case IntConstant.State_close:
                                iv_DLSFM02.setImageResource(R.drawable.valve_up_gray);
                                break;
                            case IntConstant.State_error:
                                iv_DLSFM02.setImageResource(R.drawable.valve_up_red);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.JLBZT01)) {
                        switch (bean.getJLJYState()) {
                            case IntConstant.State_open:
                                iv_JLBZT01.setImageResource(R.drawable.circle_flag_green);
                                break;
                            case IntConstant.State_close:
                                iv_JLBZT01.setImageResource(R.drawable.circle_flag_red);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.JLBZT02)) {
                        switch (bean.getJLJYState()) {
                            case IntConstant.State_open:
                                iv_JLBZT02.setImageResource(R.drawable.circle_flag_green);
                                break;
                            case IntConstant.State_close:
                                iv_JLBZT02.setImageResource(R.drawable.circle_flag_red);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.ZZZB01)) {
                        switch (bean.getJLJYState()) {
                            case IntConstant.State_open:
                                iv_ZZZB01.setImageResource(R.drawable.circle_flag_green);
                                break;
                            case IntConstant.State_close:
                                iv_ZZZB01.setImageResource(R.drawable.circle_flag_red);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.ZZZB02)) {
                        switch (bean.getJLJYState()) {
                            case IntConstant.State_open:
                                iv_ZZZB02.setImageResource(R.drawable.circle_flag_green);
                                break;
                            case IntConstant.State_close:
                                iv_ZZZB02.setImageResource(R.drawable.circle_flag_red);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getJLJYCode().equals(NetWorkConstant.JYSBZT01)) {
                        switch (bean.getJLJYState()) {
                            case IntConstant.State_open:
                                iv_JLBZT02.setImageResource(R.drawable.circle_flag_green);
                                iv_JYSBZT01_error.setImageResource(R.drawable.circle_flag_green);
                                break;
                            case IntConstant.State_close:
                                iv_JLBZT02.setImageResource(R.drawable.circle_flag_red);
                                iv_JYSBZT01_error.setImageResource(R.drawable.circle_flag_green);
                                break;
                            case IntConstant.State_error:
                                iv_JLBZT02.setImageResource(R.drawable.circle_flag_red);
                                iv_JYSBZT01_error.setImageResource(R.drawable.circle_flag_red);
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
