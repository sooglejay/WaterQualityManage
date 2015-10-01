package com.gaoxian.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaoxian.Constant.NetWorkConstant;
import com.gaoxian.Constant.PreferenceConstant;
import com.gaoxian.Constant.StringConstant;
import com.gaoxian.R;
import com.gaoxian.api.Production.GetProductionDataRetrofitUtil;
import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.events.IntEvent;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.model.ProductionData;
import com.gaoxian.model.ProductionDataPackge;
import com.gaoxian.model.ProductionState;
import com.gaoxian.model.ProductionStatePackge;
import com.gaoxian.util.DoubleClickListener;
import com.gaoxian.util.PreferenceUtil;
import com.gaoxian.widget.ScaleView.MultiTouchListener;
import com.gaoxian.widget.TitleBar;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit.RetrofitError;
import retrofit.client.Response;


public class ProductionProcessFragment extends BaseFragment {
    private float originalScaleX, originalScaleY, originalTranslateX, originalTranslateY;

    private TitleBar titleBar;
    private FrameLayout layout_view;


    private TextView tv_JSZD01;//进水浊度
    private TextView tv_WNHD01;//污泥厚度01
    private TextView tv_GSYL01;//供水压力01
    private TextView tv_JSLL01;//进水流量
    private TextView tv_CSLL01;//出水流量
    private TextView tv_PSJYW01;//配水井液位1号
    private TextView tv_PSJYW02;//配水井液位2号
    private TextView tv_QSCYW01;//清水池液位1号
    private TextView tv_QSCYW02;//清水池液位2号


    //生产过程 界面的 获取生产控制状态信息 网络请求 后 回传的字段
    public ImageView iv_PWF01;//排污阀01
    public ImageView iv_PWF02;//排污阀02
    public ImageView iv_PWF03;//排污阀03
    public ImageView iv_PWF04;//排污阀04
    public ImageView iv_PWF05;//排污阀05
    public ImageView iv_PWF06;//排污阀06
    public ImageView iv_PWF07;//排污阀07
    public ImageView iv_PWF08;//排污阀08
    public ImageView iv_PWF09;//排污阀09
    public ImageView iv_PWF10;//排污阀10
    public ImageView iv_PWF11;//排污阀11
    public ImageView iv_PWF12;//排污阀12
    public ImageView iv_PWF13;//排污阀13
    public ImageView iv_PWF14;//排污阀14
    public ImageView iv_PWF15;//排污阀15
    public ImageView iv_PWF16;//排污阀16
    public ImageView iv_PWF17;//排污阀17
    public ImageView iv_PWF18;//排污阀18
    public ImageView iv_KGF01;//进水开关阀
    public ImageView iv_QSB01;//取水泵01

    //下面两个是 取水泵01 的左边两个取水阀，目的是演示 不同状态
    public ImageView iv_QSB02;//取水泵01
    public ImageView iv_QSB03;//取水泵01


    public ImageView iv_GSB01;//供水泵01
    public ImageView iv_GSB02;//供水泵02
    public ImageView iv_GSB03;//供水泵03

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
        setUpLisenter();
        getProductionData();
        getProductionState();
    }

    private void setUp(View view, Bundle savedInstanceState) {
        titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo(PreferenceUtil.load(this.getActivity(),
                        PreferenceConstant.StationName,
                        StringConstant.defaultStationName),
                StringConstant.tabAddMedicine);

        layout_view = (FrameLayout) view.findViewById(R.id.layout_view);
        //提取缩放前View的属性
        getLayoutParams(layout_view);


        tv_JSLL01 = (TextView) view.findViewById(R.id.tv_JSLL01);
        tv_JSZD01 = (TextView) view.findViewById(R.id.tv_JSZD01);
        tv_WNHD01 = (TextView) view.findViewById(R.id.tv_WNHD01);
        tv_GSYL01 = (TextView) view.findViewById(R.id.tv_GSYL01);
        tv_CSLL01 = (TextView) view.findViewById(R.id.tv_CSLL01);
        tv_PSJYW01 = (TextView) view.findViewById(R.id.tv_PSJYW01);
        tv_PSJYW02 = (TextView) view.findViewById(R.id.tv_PSJYW02);
        tv_QSCYW01 = (TextView) view.findViewById(R.id.tv_QSCYW01);
        tv_QSCYW02 = (TextView) view.findViewById(R.id.tv_QSCYW02);

        iv_PWF01 = (ImageView) view.findViewById(R.id.iv_PWF01);
        iv_PWF02 = (ImageView) view.findViewById(R.id.iv_PWF02);
        iv_PWF03 = (ImageView) view.findViewById(R.id.iv_PWF03);
        iv_PWF04 = (ImageView) view.findViewById(R.id.iv_PWF04);
        iv_PWF05 = (ImageView) view.findViewById(R.id.iv_PWF05);
        iv_PWF06 = (ImageView) view.findViewById(R.id.iv_PWF06);
        iv_PWF07 = (ImageView) view.findViewById(R.id.iv_PWF07);
        iv_PWF08 = (ImageView) view.findViewById(R.id.iv_PWF08);
        iv_PWF09 = (ImageView) view.findViewById(R.id.iv_PWF09);
        iv_PWF10 = (ImageView) view.findViewById(R.id.iv_PWF10);
        iv_PWF11 = (ImageView) view.findViewById(R.id.iv_PWF11);
        iv_PWF12 = (ImageView) view.findViewById(R.id.iv_PWF12);
        iv_PWF13 = (ImageView) view.findViewById(R.id.iv_PWF13);
        iv_PWF14 = (ImageView) view.findViewById(R.id.iv_PWF14);
        iv_PWF15 = (ImageView) view.findViewById(R.id.iv_PWF15);
        iv_PWF16 = (ImageView) view.findViewById(R.id.iv_PWF16);
        iv_PWF17 = (ImageView) view.findViewById(R.id.iv_PWF17);
        iv_PWF18 = (ImageView) view.findViewById(R.id.iv_PWF18);
        iv_KGF01 = (ImageView) view.findViewById(R.id.iv_KGF01);
        iv_QSB01 = (ImageView) view.findViewById(R.id.iv_QSB01);
        iv_QSB02 = (ImageView) view.findViewById(R.id.iv_QSB02);
        iv_QSB03 = (ImageView) view.findViewById(R.id.iv_QSB03);
        iv_GSB01 = (ImageView) view.findViewById(R.id.iv_GSB01);
        iv_GSB02 = (ImageView) view.findViewById(R.id.iv_GSB02);
        iv_GSB03 = (ImageView) view.findViewById(R.id.iv_GSB03);

    }

    private void setUpLisenter() {
        layout_view.setOnTouchListener(new MultiTouchListener());
        layout_view.setOnClickListener(new DoubleClickListener() {
            @Override
            public void onSingleClick(final View v) {

                final Handler handler = new Handler();
                final Runnable mRunnable = new Runnable() {
                    public void run() {
                        processSingleClickEvent(v);
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
                processDoubleClickEvent(v);

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
     * 获取生产控制信息
     */
    private void getProductionData() {
        GetProductionDataRetrofitUtil.getProductionData(ProductionProcessFragment.this.getActivity(), "511525001", "weiqi", new NetCallback<NetWorkResultBean<ProductionDataPackge>>(ProductionProcessFragment.this.getActivity()) {
            @Override
            public void onFailure(RetrofitError error) {
            }

            @Override
            public void success(NetWorkResultBean<ProductionDataPackge> productionDataPackgeNetWorkResultBean, Response response) {
                ProductionDataPackge test = productionDataPackgeNetWorkResultBean.getData();
                List<ProductionData> dataList = test.getPDDataList();
                Log.e("jwjw", test.toString());

                for (ProductionData bean : dataList) {
                    if (bean.getSCKZCode().equals(NetWorkConstant.JSZD01)) {
                        tv_JSZD01.setText("" + bean.getSCKZData());
                    } else if (bean.getSCKZCode().equals(NetWorkConstant.WNHD01)) {
                        tv_WNHD01.setText("" + bean.getSCKZData());

                    } else if (bean.getSCKZCode().equals(NetWorkConstant.GSYL01)) {
                        tv_GSYL01.setText("" + bean.getSCKZData());
                    } else if (bean.getSCKZCode().equals(NetWorkConstant.JSLL01)) {
                        tv_JSLL01.setText("" + bean.getSCKZData());

                    } else if (bean.getSCKZCode().equals(NetWorkConstant.CSLL01)) {
                        tv_CSLL01.setText("" + bean.getSCKZData());

                    } else if (bean.getSCKZCode().equals(NetWorkConstant.PSJYW01)) {
                        tv_PSJYW01.setText("" + bean.getSCKZData());

                    } else if (bean.getSCKZCode().equals(NetWorkConstant.PSJYW02)) {
                        tv_PSJYW02.setText("" + bean.getSCKZData());

                    } else if (bean.getSCKZCode().equals(NetWorkConstant.QSCYW01)) {
                        tv_QSCYW01.setText("" + bean.getSCKZData());

                    } else if (bean.getSCKZCode().equals(NetWorkConstant.QSCYW02)) {
                        tv_QSCYW02.setText("" + bean.getSCKZData());

                    }
                }
            }
        });
    }

    /**
     * 获取生产控制信息
     */
    private void getProductionState() {
        GetProductionDataRetrofitUtil.getProductionState(ProductionProcessFragment.this.getActivity(), "511525001", "weiqi", new NetCallback<NetWorkResultBean<ProductionStatePackge>>(ProductionProcessFragment.this.getActivity()) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<ProductionStatePackge> productionDataPackgeNetWorkResultBean, Response response) {
                ProductionStatePackge test = productionDataPackgeNetWorkResultBean.getData();
                List<ProductionState> dataList = test.getPSDataList();
                Log.e("jwjw", test.toString());

                for (ProductionState bean : dataList) {
                    if (bean.getSCKZCode().equals(NetWorkConstant.PWF01)) {
                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_PWF01.setImageResource(R.drawable.valve_open);
                                break;
                            case 1:
                                iv_PWF01.setImageResource(R.drawable.valve_close);
                                break;
                            case 2:
                                iv_PWF01.setImageResource(R.drawable.valve_error);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getSCKZCode().equals(NetWorkConstant.PWF02)) {
                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_PWF02.setImageResource(R.drawable.valve_open);
                                break;
                            case 1:
                                iv_PWF02.setImageResource(R.drawable.valve_close);
                                break;
                            case 2:
                                iv_PWF02.setImageResource(R.drawable.valve_error);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getSCKZCode().equals(NetWorkConstant.PWF03)) {
                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_PWF03.setImageResource(R.drawable.valve_open);
                                break;
                            case 1:
                                iv_PWF03.setImageResource(R.drawable.valve_close);
                                break;
                            case 2:
                                iv_PWF03.setImageResource(R.drawable.valve_error);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getSCKZCode().equals(NetWorkConstant.PWF04)) {
                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_PWF04.setImageResource(R.drawable.valve_open);
                                break;
                            case 1:
                                iv_PWF04.setImageResource(R.drawable.valve_close);
                                break;
                            case 2:
                                iv_PWF04.setImageResource(R.drawable.valve_error);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getSCKZCode().equals(NetWorkConstant.PWF05)) {
                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_PWF05.setImageResource(R.drawable.valve_open);
                                break;
                            case 1:
                                iv_PWF05.setImageResource(R.drawable.valve_close);
                                break;
                            case 2:
                                iv_PWF05.setImageResource(R.drawable.valve_error);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getSCKZCode().equals(NetWorkConstant.PWF06)) {
                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_PWF06.setImageResource(R.drawable.valve_open);
                                break;
                            case 1:
                                iv_PWF06.setImageResource(R.drawable.valve_close);
                                break;
                            case 2:
                                iv_PWF06.setImageResource(R.drawable.valve_error);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getSCKZCode().equals(NetWorkConstant.PWF07)) {
                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_PWF07.setImageResource(R.drawable.valve_open);
                                break;
                            case 1:
                                iv_PWF07.setImageResource(R.drawable.valve_close);
                                break;
                            case 2:
                                iv_PWF07.setImageResource(R.drawable.valve_error);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getSCKZCode().equals(NetWorkConstant.PWF08)) {
                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_PWF08.setImageResource(R.drawable.valve_open);
                                break;
                            case 1:
                                iv_PWF08.setImageResource(R.drawable.valve_close);
                                break;
                            case 2:
                                iv_PWF08.setImageResource(R.drawable.valve_error);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getSCKZCode().equals(NetWorkConstant.PWF09)) {
                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_PWF09.setImageResource(R.drawable.valve_open);
                                break;
                            case 1:
                                iv_PWF09.setImageResource(R.drawable.valve_close);
                                break;
                            case 2:
                                iv_PWF09.setImageResource(R.drawable.valve_error);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getSCKZCode().equals(NetWorkConstant.PWF10)) {
                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_PWF10.setImageResource(R.drawable.valve_open);
                                break;
                            case 1:
                                iv_PWF10.setImageResource(R.drawable.valve_close);
                                break;
                            case 2:
                                iv_PWF10.setImageResource(R.drawable.valve_error);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getSCKZCode().equals(NetWorkConstant.PWF11)) {
                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_PWF11.setImageResource(R.drawable.valve_open);
                                break;
                            case 1:
                                iv_PWF11.setImageResource(R.drawable.valve_close);
                                break;
                            case 2:
                                iv_PWF11.setImageResource(R.drawable.valve_error);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getSCKZCode().equals(NetWorkConstant.PWF12)) {
                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_PWF12.setImageResource(R.drawable.valve_open);
                                break;
                            case 1:
                                iv_PWF12.setImageResource(R.drawable.valve_close);
                                break;
                            case 2:
                                iv_PWF12.setImageResource(R.drawable.valve_error);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getSCKZCode().equals(NetWorkConstant.PWF13)) {
                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_PWF13.setImageResource(R.drawable.valve_open);
                                break;
                            case 1:
                                iv_PWF13.setImageResource(R.drawable.valve_close);
                                break;
                            case 2:
                                iv_PWF13.setImageResource(R.drawable.valve_error);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getSCKZCode().equals(NetWorkConstant.PWF14)) {
                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_PWF14.setImageResource(R.drawable.valve_open);
                                break;
                            case 1:
                                iv_PWF14.setImageResource(R.drawable.valve_close);
                                break;
                            case 2:
                                iv_PWF14.setImageResource(R.drawable.valve_error);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getSCKZCode().equals(NetWorkConstant.PWF15)) {
                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_PWF15.setImageResource(R.drawable.valve_open);
                                break;
                            case 1:
                                iv_PWF15.setImageResource(R.drawable.valve_close);
                                break;
                            case 2:
                                iv_PWF15.setImageResource(R.drawable.valve_error);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getSCKZCode().equals(NetWorkConstant.PWF16)) {
                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_PWF16.setImageResource(R.drawable.valve_open);
                                break;
                            case 1:
                                iv_PWF16.setImageResource(R.drawable.valve_close);
                                break;
                            case 2:
                                iv_PWF16.setImageResource(R.drawable.valve_error);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getSCKZCode().equals(NetWorkConstant.PWF17)) {
                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_PWF17.setImageResource(R.drawable.valve_open);
                                break;
                            case 1:
                                iv_PWF17.setImageResource(R.drawable.valve_close);
                                break;
                            case 2:
                                iv_PWF17.setImageResource(R.drawable.valve_error);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getSCKZCode().equals(NetWorkConstant.PWF18)) {
                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_PWF18.setImageResource(R.drawable.valve_open);
                                break;
                            case 1:
                                iv_PWF18.setImageResource(R.drawable.valve_close);
                                break;
                            case 2:
                                iv_PWF18.setImageResource(R.drawable.valve_error);
                                break;
                            default:
                                break;
                        }

                    } else if (bean.getSCKZCode().equals(NetWorkConstant.KGF01)) {
                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_KGF01.setImageResource(R.drawable.valve_open);
                                break;
                            case 1:
                                iv_KGF01.setImageResource(R.drawable.valve_close);
                                break;
                            case 2:
                                iv_KGF01.setImageResource(R.drawable.valve_error);
                                break;
                            default:
                                break;
                        }

                    } else if (bean.getSCKZCode().equals(NetWorkConstant.QSB01)) {
                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_QSB01.setImageResource(R.drawable.fan_top_open);

                                iv_QSB02.setImageResource(R.drawable.fan_top_close);
                                iv_QSB03.setImageResource(R.drawable.fan_top_error);
                                break;
                            case 1:
                                iv_QSB01.setImageResource(R.drawable.fan_top_close);

                                iv_QSB02.setImageResource(R.drawable.fan_top_open);
                                iv_QSB03.setImageResource(R.drawable.fan_top_error);
                                break;
                            case 2:
                                iv_QSB01.setImageResource(R.drawable.fan_top_error);

                                iv_QSB02.setImageResource(R.drawable.fan_top_open);
                                iv_QSB03.setImageResource(R.drawable.fan_top_close);
                                break;
                            default:
                                break;
                        }

                    } else if (bean.getSCKZCode().equals(NetWorkConstant.GSB01)) {

                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_GSB01.setImageResource(R.drawable.fan_bottom_open);
                                break;
                            case 1:
                                iv_GSB01.setImageResource(R.drawable.fan_bottom_close);
                                break;
                            case 2:
                                iv_GSB01.setImageResource(R.drawable.fan_bottom_error);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getSCKZCode().equals(NetWorkConstant.GSB02)) {

                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_GSB02.setImageResource(R.drawable.fan_bottom_open);
                                break;
                            case 1:
                                iv_GSB02.setImageResource(R.drawable.fan_bottom_close);
                                break;
                            case 2:
                                iv_GSB02.setImageResource(R.drawable.fan_bottom_error);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.getSCKZCode().equals(NetWorkConstant.GSB03)) {
                        switch (bean.getSCKZState()) {
                            case 0:
                                iv_GSB03.setImageResource(R.drawable.fan_bottom_open);
                                break;
                            case 1:
                                iv_GSB03.setImageResource(R.drawable.fan_bottom_close);
                                break;
                            case 2:
                                iv_GSB03.setImageResource(R.drawable.fan_bottom_error);
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
