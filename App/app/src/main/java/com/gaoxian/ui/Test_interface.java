package com.gaoxian.ui;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.gaoxian.R;
import com.gaoxian.api.WM.GetStationsUtil;
import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.model.StationInfo;
import com.gaoxian.model.StationInfoPackge;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class Test_interface extends Activity {

    private Button test_button;
    private EditText result;
    private CheckBox Getstations, GetWQInfo;
    private String result_String;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_interface);
        initUI();
        test_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Getstations.isChecked())
                {

                    GetStationsUtil.getStations(Test_interface.this,"511525001","weiqi", new NetCallback<NetWorkResultBean<StationInfoPackge>>(Test_interface.this) {
                        @Override
                        public void onFailure(RetrofitError error) {
                            Toast.makeText(Test_interface.this,"failed",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void success(NetWorkResultBean<StationInfoPackge> stationInfoPackgeNetWorkResultBean, Response response) {

                            StationInfoPackge n = stationInfoPackgeNetWorkResultBean.getData();


                        }
                    });
                }

                if(GetWQInfo.isChecked())
                {

                }
            }
        });
    }

    private void initUI() {
        test_button = (Button) findViewById(R.id.testbutton);
        result = (EditText) findViewById(R.id.result_edittext);
        Getstations = (CheckBox) findViewById(R.id.getstations);
        GetWQInfo = (CheckBox) findViewById(R.id.GetWQInfo);
    }


}
