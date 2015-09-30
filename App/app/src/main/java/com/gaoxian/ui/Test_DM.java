package com.gaoxian.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import com.gaoxian.R;

import java.util.List;

/**
 * Created by Administrator on 2015/9/30.
 */
public class Test_DM extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaa_test);
        final PackageManager packageManager = this.getPackageManager();
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        // mainIntent.setPackage(packageName);
        final List<ResolveInfo> apps = packageManager.queryIntentActivities(mainIntent, 0);


        // 方法1 Android获得屏幕的宽和高
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = screenWidth = display.getWidth();
        int screenHeight = screenHeight = display.getHeight();

        // 方法2
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        TextView tv = (TextView)this.findViewById(R.id.tv);
        float width=dm.widthPixels*dm.density;
        float height=dm.heightPixels*dm.density;
        tv.setText("First method:"+dm.toString()+"\n"+"Second method:"+"Y="+screenWidth+";X="+screenHeight);

        //dm.widthPixels,dm.heightPixels


    }
}
