package com.gaoxian.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaoxian.R;


public class TitleBar extends FrameLayout {
    private TextView mTitleTv = null;
    private TextView mTabName = null;


    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initUI(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    public TitleBar(Context context) {
        super(context);
        initUI(context);
    }

    private void initUI(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_titlebar, this, true);
        mTitleTv = (TextView) findViewById(R.id.tv_title_name);
        mTabName = (TextView) findViewById(R.id.tv_tab_name);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void initTitleBarInfo(String title,String tabName) {

        mTitleTv.setText(title+"");
        mTabName.setText(tabName+"");
    }
}
