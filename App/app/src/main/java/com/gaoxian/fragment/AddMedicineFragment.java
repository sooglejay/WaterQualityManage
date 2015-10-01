package com.gaoxian.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.gaoxian.Constant.PreferenceConstant;
import com.gaoxian.Constant.StringConstant;
import com.gaoxian.R;
import com.gaoxian.api.ChlorineDosing.GetChlorineDosingDataUtil;
import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.events.IntEvent;
import com.gaoxian.model.ChlorineDosingDataPackge;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.util.DoubleClickListener;
import com.gaoxian.util.PreferenceUtil;
import com.gaoxian.widget.ScaleView.MultiTouchListener;
import com.gaoxian.widget.TitleBar;

import java.util.Timer;
import java.util.TimerTask;

import retrofit.RetrofitError;
import retrofit.client.Response;


public class AddMedicineFragment extends BaseFragment {
    private float originalScaleX, originalScaleY, originalTranslateX, originalTranslateY;

    private TitleBar titleBar;
    private RelativeLayout layout_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
        setUpListener();
        getChlorineDosingData();
    }

    private void setUp(View view, Bundle savedInstanceState) {
        titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo(PreferenceUtil.load(this.getActivity(), PreferenceConstant.StationName, StringConstant.defaultStationName),
                StringConstant.tabAddMedicine);

        layout_view = (RelativeLayout) view.findViewById(R.id.layout_view);
        getLayoutParams(layout_view);

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
        resetLayoutParams(layout_view,originalScaleX,originalScaleY,originalTranslateX,originalTranslateY);
    }

    private void getLayoutParams(View layout_view) {
        originalScaleX = layout_view.getScaleX();
        originalScaleY = layout_view.getScaleY();
        originalTranslateX = layout_view.getTranslationX();
        originalTranslateY = layout_view.getTranslationY();
    }
    public void resetLayoutParams(View layout_view,float originalScaleX, float originalScaleY, float originalTranslateX, float originalTranslateY) {
        layout_view.setTranslationX(originalTranslateX);
        layout_view.setTranslationY(originalTranslateY);
        layout_view.setScaleX(originalScaleX);
        layout_view.setScaleY(originalScaleY);
    }

    public void onEventMainThread(IntEvent event) {

        switch (event.getMsg()) {
            case IntEvent.Msg_ResetViewScale:
                if(layout_view!=null)
                {
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
        GetChlorineDosingDataUtil.getChlorineDosingData(AddMedicineFragment.this.getActivity(),PreferenceUtil.load(AddMedicineFragment.this.getActivity(),PreferenceConstant.AreaCode,""), "weiqi", new NetCallback<NetWorkResultBean<ChlorineDosingDataPackge>>(AddMedicineFragment.this.getActivity()) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<ChlorineDosingDataPackge> chlorineDosingDataPackgeNetWorkResultBean, Response response) {
                ChlorineDosingDataPackge test = chlorineDosingDataPackgeNetWorkResultBean.getData();
                Log.e("jwjw",test.toString());
            }
        });
    }

}
