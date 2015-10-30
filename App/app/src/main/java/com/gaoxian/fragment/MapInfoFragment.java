package com.gaoxian.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import com.gaoxian.constant.IntConstant;
import com.gaoxian.constant.PreferenceConstant;
import com.gaoxian.constant.StringConstant;
import com.gaoxian.R;
import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.api.wm.GetStationsUtil;
import com.gaoxian.events.IntEvent;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.model.StationInfo;
import com.gaoxian.model.StationInfoPackge;
import com.gaoxian.util.PreferenceUtil;
import com.gaoxian.util.UIUtils;
import com.gaoxian.widget.WaterStationInfoDialog;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MapInfoFragment extends BaseFragment {

    private static final LatLng GEO_CHENDU = new LatLng(28.435, 104.519);
    private static int dialog_offset = -100;
    private static int marker_offset = -1;

    private Context mContext;
    private float downX = 0, upX = 0;
    /**
     * MapView 是地图主控件
     */
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    List<StationInfo> mStationList = new ArrayList<>();
    private List<Marker> mMarkerList = new ArrayList<>();
    private BitmapDescriptor mMarkerSelected ;
    private BitmapDescriptor mMarkerUnSelected;

    //用于包裹水厂信息的覆盖物，类似于controller
    private InfoWindow mInfoWindowOverLayDialog;

    //用于显示水厂的具体信息，类似于view
    private WaterStationInfoDialog mWaterStationDialogInfo;

    private String stationNameString = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_4, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mContext = getActivity().getApplicationContext();
        setUp(view, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            stationNameString = PreferenceUtil.load(this.getActivity(),PreferenceConstant.StationName,"");
        }

    }

    private void setUp(View view, Bundle savedInstanceState) {
        initMapView(view);
        try {
            getWMGetStation(mContext);
        } catch (NullPointerException npe) {
            Log.e("jwjw", "地理信息-空指针！");
        }

    }

    /**
     * 初始化地图控件
     *
     * @param view
     */
    private void initMapView(View view) {

        mMarkerSelected = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_water_station_selected);
        mMarkerUnSelected = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_water_station);



        MapStatusUpdate u1 = MapStatusUpdateFactory.newLatLng(GEO_CHENDU);
        mMapView = (MapView) view.findViewById(R.id.id_bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapStatus(u1);

        //
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
                    .fontSize((int)UIUtils.sp2px(mContext,20))
                    .fontColor(Color.parseColor("#1f8faf"))
                    .text(infoList.get(i).getStationName() + "")
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
                for (int i = 0; i < mMarkerList.size(); i++) {
                    if (mMarkerList.get(i) == marker) {
                        mMarkerList.get(i).setIcon(mMarkerSelected);
                        mWaterStationDialogInfo.setStationInfo(mStationList.get(i));
                        final String stcd =  mStationList.get(i).getSTCD();
                        final String stationName = mStationList.get(i).getStationName();
                        PreferenceUtil.save(MapInfoFragment.this.getActivity(), PreferenceConstant.StationName,stationName);
                        PreferenceUtil.save(MapInfoFragment.this.getActivity(), PreferenceConstant.STCD, stcd);
                        mWaterStationDialogInfo.setLisenter(new WaterStationInfoDialog.OverLayOnClickLisenter() {
                            @Override
                            public void onClick(View v) {
                                PreferenceUtil.save(MapInfoFragment.this.getActivity(), PreferenceConstant.StationName,stationName);
                                PreferenceUtil.save(MapInfoFragment.this.getActivity(), PreferenceConstant.STCD, stcd);
                                String tag = (String) v.getTag();
                                if (tag.equals(StringConstant.enter)) {
                                    marker.setIcon(mMarkerUnSelected);
                                    mBaiduMap.hideInfoWindow();
                                    IntEvent event = new IntEvent(IntEvent.Msg_Enter_Water_Station);
                                    EventBus.getDefault().post(event);
                                } else if (tag.equals(StringConstant.back)) {
                                    marker.setIcon(mMarkerUnSelected);
                                    mBaiduMap.hideInfoWindow();
                                }
                            }
                        });
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

    private void getWMGetStation(Context context) {
        GetStationsUtil.getStations(context, PreferenceUtil.load(context, PreferenceConstant.AreaCode, ""), StringConstant.weiqi, new NetCallback<NetWorkResultBean<StationInfoPackge>>(context) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<StationInfoPackge> stationInfoPackgeNetWorkResultBean, Response response) {
                StationInfoPackge infoPackge = stationInfoPackgeNetWorkResultBean.getData();
                Log.e("jwjw", infoPackge.toString());
                mStationList = infoPackge.getStationList();
                if(TextUtils.isEmpty(stationNameString)) {
                    if (mStationList != null && mStationList.size() > 0) {
                        PreferenceUtil.save(MapInfoFragment.this.getActivity(), PreferenceConstant.StationName, mStationList.get(0).getStationName());
                        EventBus.getDefault().post(new IntEvent(IntEvent.Msg_RefreshTitleBar));
                    }
                }
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
    }

}
