package com.gaoxian.application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.gaoxian.util.CarNetCrashHandler;

public class GaoXianApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		SDKInitializer.initialize(this);
		CarNetCrashHandler mCustomCrashHandler = CarNetCrashHandler.getInstance();
		mCustomCrashHandler.setCustomCrashHanler(getApplicationContext());
	}

}