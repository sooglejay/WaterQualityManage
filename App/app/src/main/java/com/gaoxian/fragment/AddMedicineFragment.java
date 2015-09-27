package com.gaoxian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gaoxian.Constant.StringConstant;
import com.gaoxian.R;

import com.gaoxian.events.IntEvent;
import com.gaoxian.widget.MagnifyingView;
import com.gaoxian.widget.TitleBar;


public class AddMedicineFragment extends BaseFragment{

    private TitleBar titleBar;
    private LinearLayout layout_view;
    private MagnifyingView magnifyingView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
    }

    private void setUp(View view, Bundle savedInstanceState) {
        titleBar = (TitleBar)view.findViewById(R.id.title_bar);
        layout_view = (LinearLayout)view.findViewById(R.id.layout_view);
        titleBar.initTitleBarInfo(StringConstant.tabAddMedicine);
        magnifyingView =  new MagnifyingView(this.getActivity());
        magnifyingView.attach(layout_view);
    }

}
