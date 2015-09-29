package com.gaoxian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gaoxian.Constant.PreferenceConstant;
import com.gaoxian.Constant.StringConstant;
import com.gaoxian.R;
import com.gaoxian.util.PreferenceUtil;
import com.gaoxian.widget.TitleBar;

public class ProductionProcessFragment extends BaseFragment {

    private TitleBar titleBar;
    private LinearLayout layout_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
    }

    private void setUp(View view, Bundle savedInstanceState) {
        titleBar = (TitleBar)view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo(PreferenceUtil.load(this.getActivity(), PreferenceConstant.StationName, StringConstant.defaultStationName),
                StringConstant.tabProductionProcess);
        layout_view = (LinearLayout)view.findViewById(R.id.layout_view);

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}
