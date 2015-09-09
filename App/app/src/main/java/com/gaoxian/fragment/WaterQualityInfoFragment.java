package com.gaoxian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaoxian.Constant.StringConstant;
import com.gaoxian.R;
import com.gaoxian.widget.TitleBar;


public class WaterQualityInfoFragment extends BaseFragment {

    private TitleBar titleBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_3, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
    }

    private void setUp(View view, Bundle savedInstanceState) {
        titleBar = (TitleBar)view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo(StringConstant.tabWaterQualityInfo);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

}
