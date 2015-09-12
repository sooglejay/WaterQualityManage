package com.gaoxian.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gaoxian.Constant.StringConstant;
import com.gaoxian.R;

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

    public interface OverLayOnClickLisenter
    {
        public void onClick(View v);
    }


}
