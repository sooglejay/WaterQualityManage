package com.gaoxian.events;

/**
 * Created by xuejiebang-android on 15/7/18.
 */
public class IntEvent {

    //点击地图上的水厂“点击进入”按钮
    public static final int Msg_Enter_Water_Station = 1000;

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
