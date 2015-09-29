package com.gaoxian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.gaoxian.Constant.IntConstant;
import com.gaoxian.Constant.PreferenceConstant;
import com.gaoxian.Constant.StringConstant;
import com.gaoxian.R;
import com.gaoxian.api.WM.GetStationsUtil;
import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.events.IntEvent;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.model.StationInfo;
import com.gaoxian.model.StationInfoPackge;
import com.gaoxian.util.PreferenceUtil;
import com.gaoxian.widget.TitleBar;
import com.gaoxian.widget.WaterStationInfoDialog;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MapInfoFragment extends BaseFragment {

    private static final LatLng GEO_CHENDU = new LatLng(28.435, 104.519);
    private TitleBar titleBar;
    private static int dialog_offset = -100;
    private static int marker_offset = -1;


    private float downX = 0, upX = 0;
    /**
     * MapView 是地图主控件
     */
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    List<StationInfo> mStationList = new ArrayList<>();
    private List<Marker> mMarkerList = new ArrayList<>();
    BitmapDescriptor mMarkerSelected = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_water_station_selected);
    BitmapDescriptor mMarkerUnSelected = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_water_station);

    //用于包裹水厂信息的覆盖物，类似于controller
    private InfoWindow mInfoWindowOverLayDialog;

    //用于显示水厂的具体信息，类似于view
    private WaterStationInfoDialog mWaterStationDialogInfo;

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
        titleBar.initTitleBarInfo(PreferenceUtil.load(this.getActivity(), PreferenceConstant.StationName,StringConstant.defaultStationName),
                StringConstant.tabMapInfo);

        initMapView(view);
        getWMGetStation();
    }

    /**
     * 初始化地图控件
     *
     * @param view
     */
    private void initMapView(View view) {
        MapStatusUpdate u1 = MapStatusUpdateFactory.newLatLng(GEO_CHENDU);
        mMapView = (MapView) view.findViewById(R.id.id_bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapStatus(u1);

        mBaiduMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = motionEvent.getX();
                        if (downX < IntConstant.ScrollBaiduMapBoundary) {
                            EventBus.getDefault().post(new IntEvent(IntEvent.Msg_Enable_ViewPager_Scroll));
                        } else {
                            EventBus.getDefault().post(new IntEvent(IntEvent.Msg_Disable_ViewPager_Scroll));
                        }
                        break;
                }
            }
        });
    }

    /**
     * 初始化地图上的覆盖物,注意    LatLng llB = new LatLng(infoList.get(i).getMAPLGTD() , infoList.get(i).getMAPLTTD()); 参数的顺序
     *
     * @param infoList
     */
    private void initMark(List<StationInfo> infoList) {
        for (int i = 0; i < infoList.size(); i++) {
            LatLng llB = new LatLng(infoList.get(i).getMAPLGTD(), infoList.get(i).getMAPLTTD());
            OverlayOptions ooB = new MarkerOptions().position(llB).icon(mMarkerUnSelected)
                    .zIndex(9);
            Marker newStationMarker = (Marker) (mBaiduMap.addOverlay(ooB));
            mMarkerList.add(newStationMarker);

            //定义文字所显示的坐标点
            LatLng waterStation1 = new LatLng(infoList.get(i).getMAPLGTD(), infoList.get(i).getMAPLTTD());
            //构建文字Option对象，用于在地图上添加文字
            OverlayOptions textOption1 = new TextOptions()
                    .bgColor(R.color.green_color)
                    .fontSize(24)
                    .fontColor(R.color.light_red)
                    .text((i + 1) + "号水厂")
                    .rotate(0)
                    .position(waterStation1);
            //在地图上添加该文字对象并显示
            mBaiduMap.addOverlay(textOption1);
        }
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
                            marker.setIcon(mMarkerUnSelected);
                            mBaiduMap.hideInfoWindow();
                            EventBus.getDefault().post(new IntEvent(IntEvent.Msg_Enter_Water_Station));
                        } else if (tag.equals(StringConstant.back)) {
                            marker.setIcon(mMarkerUnSelected);
                            mBaiduMap.hideInfoWindow();
                        }
                    }
                });

                for (int i = 0; i < mMarkerList.size(); i++) {
                    if (mMarkerList.get(i) == marker) {
                        mMarkerList.get(i).setIcon(mMarkerSelected);
                        mWaterStationDialogInfo.setStationInfo(mStationList.get(i));
                        PreferenceUtil.save(MapInfoFragment.this.getActivity(),PreferenceConstant.StationName,mStationList.get(i).getStationName());
                        // 定义用于显示该InfoWindow的坐标点
                        LatLng pt = new LatLng(mStationList.get(i).getMAPLGTD(), mStationList.get(i).getMAPLTTD());
                        // 创建InfoWindow
                        mInfoWindowOverLayDialog = new InfoWindow(mWaterStationDialogInfo, pt, dialog_offset);
                        // 显示InfoWindow
                        mBaiduMap.showInfoWindow(mInfoWindowOverLayDialog);
                    }
                }
                return true;
            }
        });
    }

    private void getWMGetStation() {
        GetStationsUtil.getStations(MapInfoFragment.this.getActivity(), PreferenceUtil.load(this.getActivity(), PreferenceConstant.AreaCode, ""), StringConstant.apiKey, new NetCallback<NetWorkResultBean<StationInfoPackge>>(MapInfoFragment.this.getActivity()) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<StationInfoPackge> stationInfoPackgeNetWorkResultBean, Response response) {
                StationInfoPackge infoPackge = stationInfoPackgeNetWorkResultBean.getData();
                mStationList = infoPackge.getStationList();
                initMark(mStationList);
                setMapLisenter();
            }
        });
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
        mBaiduMap.clear();
        mMapView.onDestroy();
        mMapView = null;
        mWaterStationDialogInfo = null;

    }

}
