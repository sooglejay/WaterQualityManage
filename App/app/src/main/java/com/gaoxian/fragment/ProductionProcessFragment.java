package com.gaoxian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gaoxian.Constant.StringConstant;
import com.gaoxian.R;
import com.gaoxian.magnifyingview.Constants;
import com.gaoxian.magnifyingview.PreferencesManager;
import com.gaoxian.widget.MagnifyingView;
import com.gaoxian.widget.TitleBar;

public class ProductionProcessFragment extends BaseFragment {

    private TitleBar titleBar;
    private MagnifyingView testView;
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
        testView =  new MagnifyingView(this.getActivity());
        testView.attach(layout_view);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onResume() {
        super.onResume();
        m();
    }
    
    public void m()
    {

        PreferencesManager preferencesManager = PreferencesManager.getInstance(this.getActivity());
        testView.enableMagnifying(preferencesManager.readBoolean(Constants.PREFERENCES_KEY_ACTIVATION,
                Constants.PREFERENCES_DEFAULT_ACTIVATION));
        testView.setScale(preferencesManager.readFloat(Constants.PREFERENCES_KEY_SCALE,
                Constants.PREFERENCES_DEFAULT_SCALE));
        testView.setCircleRadius(preferencesManager.readInteger(Constants.PREFERENCES_KEY_RADIUS,
                Constants.PREFERENCES_DEFAULT_RADIUS));
        testView.setBorderWidth(preferencesManager.readInteger(Constants.PREFERENCES_KEY_BORDER_WIDTH,
                Constants.PREFERENCES_DEFAULT_BORDER_WIDTH));
        testView.setBorderColor(preferencesManager.readInteger(Constants.PREFERENCES_KEY_BORDER_COLOR,
                Constants.PREFERENCES_DEFAULT_BORDER_COLOR));
        testView.setOffset(preferencesManager.readInteger(Constants.PREFERENCES_KEY_OFFSET_X,
                Constants.PREFERENCES_DEFAULT_OFFSET_X), preferencesManager.readInteger(
                Constants.PREFERENCES_KEY_OFFSET_Y, Constants.PREFERENCES_DEFAULT_OFFSET_Y));

    }
}
