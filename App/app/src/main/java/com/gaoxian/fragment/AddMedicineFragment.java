package com.gaoxian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gaoxian.Constant.StringConstant;
import com.gaoxian.R;
import com.gaoxian.events.IntEvent;
import com.gaoxian.widget.ScaleView.MultiTouchListener;
import com.gaoxian.widget.TitleBar;


public class AddMedicineFragment extends BaseFragment {
    private float originalScaleX, originalScaleY, originalTranslateX, originalTranslateY;
    private static final long LONG_PRESS_TIME = 500;
    private long mCurrentClickTime;
    private int mClickCount = 0;

    private TitleBar titleBar;
    private LinearLayout layout_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
    }

    private void setUp(View view, Bundle savedInstanceState) {
        titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        layout_view = (LinearLayout) view.findViewById(R.id.layout_view);
        titleBar.initTitleBarInfo(StringConstant.tabAddMedicine);
        layout_view.setOnTouchListener(new MultiTouchListener());
        getLayoutParams(layout_view);
        layout_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickCount++;
                switch (mClickCount) {
                    case 1:
                        mCurrentClickTime = System.currentTimeMillis();
                        break;
                    case 2:
                        if (System.currentTimeMillis() - mCurrentClickTime < LONG_PRESS_TIME) {
                            mCurrentClickTime = System.currentTimeMillis();
                            mClickCount = 0;
                            resetLayoutParams(layout_view, originalScaleX, originalScaleY, originalTranslateX, originalTranslateY);
                        } else {
                            mCurrentClickTime = System.currentTimeMillis();
                            mClickCount = 0;
                        }
                        break;
                    default:
                        break;

                }
            }
        });
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

    /**
     * EvenBus 接收消息
     *
     * @param event
     */
    public void onEventMainThread(IntEvent event) {

        switch (event.getMsg()) {
            case IntEvent.Msg_ViewPager_Scroll:
                if(layout_view!=null)
                {
                    resetLayoutParams(layout_view, originalScaleX, originalScaleY, originalTranslateX, originalTranslateY);
                }
                break;
            default:
                break;
        }
    }
}
