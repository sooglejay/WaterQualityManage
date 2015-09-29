package com.gaoxian.events;

/**
 * Created by xuejiebang-android on 15/7/18.
 */
public class IntEvent {

    //点击地图上的水厂“点击进入”按钮
    public static final int Msg_Enter_Water_Station = 1000;//进入水厂
    public static final int Msg_ViewPager_PageChanged = 1001;//ViewPager滑动或者切换page
    public static final int Msg_Disable_ViewPager_Scroll = 1002;//不允许ViewPager滑动
    public static final int Msg_Enable_ViewPager_Scroll = 1003;//不允许ViewPager滑动
    private int msg = 0;

    public IntEvent(int msg) {
        this.msg = msg;
    }

    public int getMsg() {
        return msg;
    }
    public void setMsg(int msg) {
        this.msg = msg;
    }

}
