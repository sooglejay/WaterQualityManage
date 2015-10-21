package com.gaoxian.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaoxian.constant.StringConstant;
import com.gaoxian.R;
import com.gaoxian.model.StationInfo;

/**
 * Created by Administrator on 2015/9/12.
 */
public class WaterStationInfoDialog extends LinearLayout {

    private TextView enter;
    private TextView back;

    public void setLisenter(OverLayOnClickLisenter lisenter) {
        this.lisenter = lisenter;
    }

    private OverLayOnClickLisenter lisenter;
    private TextView tvStationTitle;
    private TextView tvToxic;
    private TextView tvJWaterQuality;
    private TextView tvCWaterQuality;

    public WaterStationInfoDialog(Context context) {
        super(context);
        initView(context);
    }

    public WaterStationInfoDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WaterStationInfoDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WaterStationInfoDialog(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(final Context mContext) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_water_station_info,this,true);
        tvStationTitle = (TextView)view.findViewById(R.id.tv_station_title);
        tvToxic = (TextView)view.findViewById(R.id.tv_toxic);
        tvJWaterQuality = (TextView)view.findViewById(R.id.tv_j_water_quality);
        tvCWaterQuality = (TextView)view.findViewById(R.id.tv_c_water_quality);

        enter = (TextView)view.findViewById(R.id.tv_enter);
        enter.setTag(StringConstant.enter);

        back = (TextView)view.findViewById(R.id.tv_back);
        back.setTag(StringConstant.back);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lisenter.onClick(v);
            }
        });

        enter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lisenter.onClick(v);
            }
        });

    }

    /**
     * 设置水厂的基本信息
     * @param stationInfo
     */
    public void setStationInfo(StationInfo stationInfo)
    {
        tvStationTitle.setText(""+stationInfo.getStationName());
        tvToxic.setText("编号："+stationInfo.getSTCD());
        tvCWaterQuality.setText("出厂水质："+stationInfo.getCCInfo());
        tvJWaterQuality.setText("进出水质："+stationInfo.getJCinfo());
    }
    public interface OverLayOnClickLisenter
    {
        public void onClick(View v);
    }


}
