package com.gaoxian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gaoxian.Constant.StringConstant;
import com.gaoxian.R;

import com.gaoxian.events.IntEvent;
import com.gaoxian.widget.MagnifyingView;
import com.gaoxian.widget.TitleBar;

public class ProductionProcessFragment extends BaseFragment {

    private TitleBar titleBar;
    private MagnifyingView magnifyingView;
    private LinearLayout layout_view;
    private ImageView bg_image;

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
        titleBar.initTitleBarInfo(StringConstant.tabProductionProcess);
        layout_view = (LinearLayout)view.findViewById(R.id.layout_view);
        bg_image = (ImageView)view.findViewById(R.id.bg_image);
        bg_image.setImageResource(R.drawable.productionprocess_background);
        magnifyingView =  new MagnifyingView(this.getActivity());
        magnifyingView.attach(layout_view);
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
