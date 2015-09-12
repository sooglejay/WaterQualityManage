package com.gaoxian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.gaoxian.Constant.StringConstant;
import com.gaoxian.R;
import com.gaoxian.events.IntEvent;
import com.gaoxian.widget.TitleBar;
import com.gaoxian.widget.WaterStationInfoDialog;

import de.greenrobot.event.EventBus;

public class MapInfoFragment extends BaseFragment {

    private static final LatLng GEO_BEIJING = new LatLng(39.945, 116.404);
    private static final LatLng GEO_SHANGHAI = new LatLng(31.227, 121.481);
    private static final LatLng GEO_GUANGZHOU = new LatLng(23.155, 113.264);
    private static final LatLng GEO_SHENGZHENG = new LatLng(22.560, 114.064);


    //假定四个水厂的 经度为：
    private static final double[] Geo_latitude = {
            39.963175,
            39.942821,
            39.939723,
            39.906965,
    };

    //假定四个水厂的 纬度为：
    private static final double[] Geo_longitude = {
            116.400244,
            116.369199,
            116.425541,
            116.401394,
    };

    private TitleBar titleBar;

    /**
     * MapView 是地图主控件
     */
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Marker mMarkerA;
    private Marker mMarkerB;
    private Marker mMarkerC;
    private Marker mMarkerD;


    //用于包裹水厂信息的覆盖物，类似于controller
    private InfoWindow mInfoWindowOverLayDialog;

    //用于显示水厂的具体信息，类似于view
    private WaterStationInfoDialog mWaterStationDialogInfo;


    // 初始化全局 bitmap 信息，不用时及时 recycle
    BitmapDescriptor bdA = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_marka);
    BitmapDescriptor bdB = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_markb);
    BitmapDescriptor bdC = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_markc);
    BitmapDescriptor bdD = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_markd);
    BitmapDescriptor bd = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_gcoding);
    BitmapDescriptor bdGround = BitmapDescriptorFactory
            .fromResource(R.drawable.ground_overlay);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_4, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void setUp(View view, Bundle savedInstanceState) {
        titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo(StringConstant.tabMapInfo);

        initMapView(view);

        initStationPointOverlay();

        setMapLisenter();

    }

    /**
     * 初始化地图控件
     *
     * @param view
     */
    private void initMapView(View view) {
        MapStatusUpdate u1 = MapStatusUpdateFactory.newLatLng(GEO_BEIJING);
        mMapView = (MapView) view.findViewById(R.id.id_bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapStatus(u1);
    }


    /**
     * 设置地图上覆盖物的点击事件
     */
    private void setMapLisenter() {

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {
                Button button = new Button(getActivity());
                button.setBackgroundResource(R.drawable.popup);

                //自定义弹出对话框样式的覆盖物
                mWaterStationDialogInfo = new WaterStationInfoDialog(getActivity());
                mWaterStationDialogInfo.setLisenter(new WaterStationInfoDialog.OverLayOnClickLisenter() {
                    @Override
                    public void onClick(View v) {
                        String tag = (String) v.getTag();
                        if (tag.equals(StringConstant.enter)) {
                            Toast.makeText(getActivity(), "你点击的是enter", Toast.LENGTH_SHORT).show();
                            mBaiduMap.hideInfoWindow();
                            EventBus.getDefault().post(new IntEvent(IntEvent.Msg_Enter_Water_Station));
                        } else if (tag.equals(StringConstant.back)) {
                            Toast.makeText(getActivity(), "你点击的是back", Toast.LENGTH_SHORT).show();
                            mBaiduMap.hideInfoWindow();
                        }
                    }
                });
                if (marker == mMarkerA) {
                    // 定义用于显示该InfoWindow的坐标点
                    LatLng pt = new LatLng(Geo_latitude[0], Geo_longitude[0]);
                    // 创建InfoWindow
                    mInfoWindowOverLayDialog = new InfoWindow(mWaterStationDialogInfo, pt, -47);
                    // 显示InfoWindow
                    mBaiduMap.showInfoWindow(mInfoWindowOverLayDialog);
                } else if (marker == mMarkerB) {
                    // 定义用于显示该InfoWindow的坐标点
                    LatLng pt = new LatLng(Geo_latitude[1], Geo_longitude[1]);
                    // 创建InfoWindow
                    mInfoWindowOverLayDialog = new InfoWindow(mWaterStationDialogInfo, pt, -47);
                    // 显示InfoWindow
                    mBaiduMap.showInfoWindow(mInfoWindowOverLayDialog);
                } else if (marker == mMarkerC) {
                    // 定义用于显示该InfoWindow的坐标点
                    LatLng pt = new LatLng(Geo_latitude[2], Geo_longitude[2]);
                    // 创建InfoWindow
                    mInfoWindowOverLayDialog = new InfoWindow(mWaterStationDialogInfo, pt, -47);
                    // 显示InfoWindow
                    mBaiduMap.showInfoWindow(mInfoWindowOverLayDialog);
                } else if (marker == mMarkerD) {
                    // 定义用于显示该InfoWindow的坐标点
                    LatLng pt = new LatLng(Geo_latitude[3], Geo_longitude[3]);
                    // 创建InfoWindow
                    mInfoWindowOverLayDialog = new InfoWindow(mWaterStationDialogInfo, pt, -47);
                    // 显示InfoWindow
                    mBaiduMap.showInfoWindow(mInfoWindowOverLayDialog);
                }
                return true;
            }
        });
    }



    /**
     * 初始化水厂站点在地图上的位置
     */
    public void initStationPointOverlay() {

        //下面显示水厂的图标
        LatLng llA = new LatLng(Geo_latitude[0], Geo_longitude[0]);
        LatLng llB = new LatLng(Geo_latitude[1], Geo_longitude[1]);
        LatLng llC = new LatLng(Geo_latitude[2], Geo_longitude[2]);
        LatLng llD = new LatLng(Geo_latitude[3], Geo_longitude[3]);

        OverlayOptions ooA = new MarkerOptions().position(llA).icon(bdA)
                .zIndex(5).draggable(true);
        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));


        OverlayOptions ooB = new MarkerOptions().position(llB).icon(bdB)
                .zIndex(5);
        mMarkerB = (Marker) (mBaiduMap.addOverlay(ooB));


        OverlayOptions ooC = new MarkerOptions().position(llC).icon(bdC)
                .zIndex(5);
        mMarkerC = (Marker) (mBaiduMap.addOverlay(ooC));


        OverlayOptions ooD = new MarkerOptions().position(llD).icon(bdD)
                .zIndex(5);
        mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));


        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            public void onMarkerDrag(Marker marker) {
            }

            public void onMarkerDragEnd(Marker marker) {
                Toast.makeText(getActivity(), "拖拽结束，新位置：" + marker.getPosition().latitude + ", " + marker.getPosition().longitude, Toast.LENGTH_LONG).show();
            }

            public void onMarkerDragStart(Marker marker) {
            }
        });

        //下面显示水厂文字覆盖物

        //定义文字所显示的坐标点
        LatLng waterStation1 = new LatLng(Geo_latitude[0], Geo_longitude[0]);
        LatLng waterStation2 = new LatLng(Geo_latitude[1], Geo_longitude[1]);
        LatLng waterStation3 = new LatLng(Geo_latitude[2], Geo_longitude[2]);
        LatLng waterStation4 = new LatLng(Geo_latitude[3], Geo_longitude[3]);

        //构建文字Option对象，用于在地图上添加文字
        OverlayOptions textOption1 = new TextOptions()
                .bgColor(R.color.green_color)
                .fontSize(24)
                .fontColor(R.color.light_red)
                .text("1号水厂")
                .rotate(0)
                .position(waterStation1);
        OverlayOptions textOption2 = new TextOptions()
                .bgColor(R.color.green_color)
                .fontSize(24)
                .fontColor(R.color.light_red)
                .text("2号水厂")
                .rotate(0)
                .position(waterStation2);
        OverlayOptions textOption3 = new TextOptions()
                .bgColor(R.color.green_color)
                .fontSize(24)
                .fontColor(R.color.light_red)
                .text("3号水厂")
                .rotate(0)
                .position(waterStation3);
        OverlayOptions textOption4 = new TextOptions()
                .bgColor(R.color.green_color)
                .fontSize(24)
                .fontColor(R.color.light_red)
                .text("4号水厂")
                .rotate(0)
                .position(waterStation4);
        //在地图上添加该文字对象并显示
        mBaiduMap.addOverlay(textOption1);
        mBaiduMap.addOverlay(textOption2);
        mBaiduMap.addOverlay(textOption3);
        mBaiduMap.addOverlay(textOption4);
    }

    /**
     * 清除所有Overlay
     *
     * @param view
     */
    public void clearOverlay(View view) {
        mBaiduMap.clear();
    }

    /**
     * 重新添加Overlay
     *
     * @param view
     */
    public void resetOverlay(View view) {
        clearOverlay(null);
        initStationPointOverlay();
    }


    @Override
    public void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mMapView = null;
        mWaterStationDialogInfo = null;

        // 回收 bitmap 资源
        bdA.recycle();
        bdB.recycle();
        bdC.recycle();
        bdD.recycle();
        bd.recycle();
        bdGround.recycle();
    }

}
