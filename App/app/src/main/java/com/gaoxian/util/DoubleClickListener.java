package com.gaoxian.util;

import android.view.View;

import java.util.Timer;

/**
 * Created by Administrator on 2015/9/29.
 */
public abstract class DoubleClickListener implements View.OnClickListener {

    public static final long DOUBLE_CLICK_TIME_DELTA = 200;//milliseconds
    public Timer timer = null;  //at class level;

    long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long clickTime = System.currentTimeMillis();
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA){
            onDoubleClick(v);
        } else {
            onSingleClick(v);
        }
        lastClickTime = clickTime;
    }

    public abstract void onSingleClick(View v);
    public abstract void onDoubleClick(View v);
}
