package com.gaoxian.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gaoxian.R;
import com.gaoxian.api.ChlorineDosing.GetChlorineDosingDataUtil;
import com.gaoxian.api.Production.GetProductionDataRetrofitUtil;
import com.gaoxian.api.WM.GetStationsUtil;
import com.gaoxian.api.WQ.WQRetrofitUtil;
import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.model.ChlorineDosingDataPackge;
import com.gaoxian.model.ChlorineDosingStatePackge;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.model.ProductionDataPackge;
import com.gaoxian.model.ProductionStatePackge;
import com.gaoxian.model.StationInfoPackge;
import com.gaoxian.model.WQinfoPackge;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class Test_interface extends Activity {

    private Button btnTest;
    private TextView tvShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_interface);
        setUpView();
        setUpLisenter();
    }

    private void setUpView() {
        btnTest = (Button) findViewById(R.id.btn_test);
        tvShow = (TextView) findViewById(R.id.tv_show);
    }

    private void setUpLisenter() {
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Test_interface.this, "hello world!", Toast.LENGTH_SHORT).show();
                testWQ();
            }
        });
    }

    /**
     * 测试 WM / getStation
     */
    private void testWMGetStation() {
        GetStationsUtil.getStations(Test_interface.this, "511512", "weiqi", new NetCallback<NetWorkResultBean<StationInfoPackge>>(Test_interface.this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<StationInfoPackge> stationInfoPackgeNetWorkResultBean, Response response) {
                StationInfoPackge test = stationInfoPackgeNetWorkResultBean.getData();
                tvShow.setText(test.toString());
            }
        });
    }

    /**
     * 测试  WQ / 水质信息
     */
    private void testWQ() {
        WQRetrofitUtil.getWQInfo(Test_interface.this, "511525001", "weiqi", new NetCallback<NetWorkResultBean<WQinfoPackge>>(Test_interface.this) {
            @Override
            public void onFailure(RetrofitError error) {
                tvShow.setText("error_reason:" + error.getResponse().getReason());
                tvShow.append("\nstatus:" + error.getResponse().getStatus());
                tvShow.append("\nbody:" + error.getResponse().getBody());
            }

            @Override
            public void success(NetWorkResultBean<WQinfoPackge> wQinfoPackgeNetWorkResultBean, Response response) {

                WQinfoPackge test = wQinfoPackgeNetWorkResultBean.getData();
                tvShow.setText(test.toString());
            }
        });
    }

    /**
     * 获取加氯加药状态信息
     */
    private void getChlorineDosingState() {
        GetChlorineDosingDataUtil.getChlorineDosingState(Test_interface.this, "511525001", "weiqi", new NetCallback<NetWorkResultBean<ChlorineDosingStatePackge>>(Test_interface.this) {
            @Override
            public void onFailure(RetrofitError error) {
                tvShow.setText("error_reason:" + error.getResponse().getReason());
                tvShow.append("\nstatus:" + error.getResponse().getStatus());
                tvShow.append("\nbody:" + error.getResponse().getBody());
            }

            @Override
            public void success(NetWorkResultBean<ChlorineDosingStatePackge> chlorineDosingStatePackgeNetWorkResultBean, Response response) {
                ChlorineDosingStatePackge test = chlorineDosingStatePackgeNetWorkResultBean.getData();
                tvShow.setText(test.toString());
            }
        });
    }

    /**
     * 测试加氟加药
     */
    private void testChlorineDosing() {
        GetChlorineDosingDataUtil.getChlorineDosingData(Test_interface.this, "511525001", "weiqi", new NetCallback<NetWorkResultBean<ChlorineDosingDataPackge>>(Test_interface.this) {
            @Override
            public void onFailure(RetrofitError error) {
                tvShow.setText("error_reason:" + error.getResponse().getReason());
                tvShow.append("\nstatus:" + error.getResponse().getStatus());
                tvShow.append("\nbody:" + error.getResponse().getBody());
            }

            @Override
            public void success(NetWorkResultBean<ChlorineDosingDataPackge> chlorineDosingDataPackgeNetWorkResultBean, Response response) {
                ChlorineDosingDataPackge test = chlorineDosingDataPackgeNetWorkResultBean.getData();
                tvShow.setText(test.toString());
            }
        });
    }

    /**
     * 获取生产控制信息
     */
    private void GetProductionData() {
        GetProductionDataRetrofitUtil.getProductionData(Test_interface.this, "511525001", "weiqi", new NetCallback<NetWorkResultBean<ProductionDataPackge>>(Test_interface.this) {
            @Override
            public void onFailure(RetrofitError error) {
                tvShow.setText("error_reason:" + error.getResponse().getReason());
                tvShow.append("\nstatus:" + error.getResponse().getStatus());
                tvShow.append("\nbody:" + error.getResponse().getBody());
            }

            @Override
            public void success(NetWorkResultBean<ProductionDataPackge> productionDataPackgeNetWorkResultBean, Response response) {
                ProductionDataPackge test = productionDataPackgeNetWorkResultBean.getData();
                tvShow.setText(test.toString());
            }
        });
    }

    /**
     * 获取生产控制信息
     */
    private void getProductionState() {
        GetProductionDataRetrofitUtil.getProductionState(Test_interface.this, "511525001", "weiqi", new NetCallback<NetWorkResultBean<ProductionStatePackge>>(Test_interface.this) {
            @Override
            public void onFailure(RetrofitError error) {
                tvShow.setText("error_reason:" + error.getResponse().getReason());
                tvShow.append("\nstatus:" + error.getResponse().getStatus());
                tvShow.append("\nbody:" + error.getResponse().getBody());
            }

            @Override
            public void success(NetWorkResultBean<ProductionStatePackge> productionDataPackgeNetWorkResultBean, Response response) {
                ProductionStatePackge test = productionDataPackgeNetWorkResultBean.getData();
                tvShow.setText(test.toString());
            }
        });
    }

}
