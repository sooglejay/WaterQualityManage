package com.gaoxian.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.gaoxian.R;
import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.api.user.UserRetrofitUtil;
import com.gaoxian.constant.PreferenceConstant;
import com.gaoxian.constant.StringConstant;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.model.UserInfo;
import com.gaoxian.util.PreferenceUtil;
import com.gaoxian.util.ProgressDialogUtil;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginPageActivity extends Activity {
    private EditText et_user_name;
    private EditText et_password;
    private Button bt_login;
    private CheckBox cbRememberUserNameAndPassword;
    private String strUserName,strPassword;

    private ProgressDialogUtil progressDialogUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        setUpView();
        setUpLisenter();
    }

    /**
     * 初始化UI
     */
    private void setUpView()
    {
        et_user_name =(EditText)findViewById(R.id.et_user_name);
        et_password =(EditText)findViewById(R.id.et_password);
        bt_login =(Button)findViewById(R.id.bt_login);
        cbRememberUserNameAndPassword =(CheckBox)findViewById(R.id.rememberpassword_checkbox);

        strPassword = PreferenceUtil.load(this,PreferenceConstant.USER_PASSWORD,"");
        strUserName = PreferenceUtil.load(this,PreferenceConstant.USER_NAME,"");
        cbRememberUserNameAndPassword.setChecked(!TextUtils.isEmpty(strUserName));

        et_user_name.setText(strUserName);
        et_user_name.setSelection(strUserName.length());
        et_password.setText(strPassword);

        progressDialogUtil = new ProgressDialogUtil(this);
    }

    /**
     * 初始化UI事件监听器
     */
    private void setUpLisenter() {
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               strUserName = et_user_name.getText().toString();
               strPassword = et_password.getText().toString();

                if(TextUtils.isEmpty(strUserName)||TextUtils.isEmpty(strPassword))
                {
                    Toast.makeText(LoginPageActivity.this,"请输入用户名或密码！",Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialogUtil.show("正在登陆...");
                //mytest  123000
                UserRetrofitUtil.login(LoginPageActivity.this,strUserName, strPassword, StringConstant.weiqi, new NetCallback<NetWorkResultBean<UserInfo>>(LoginPageActivity.this) {
                    @Override
                    public void onFailure(RetrofitError error) {
                        progressDialogUtil.hide();
                        Toast.makeText(LoginPageActivity.this,"无法连接服务器",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void success(NetWorkResultBean<UserInfo> userInfoNetWorkResultBean, Response response) {
                        progressDialogUtil.hide();

                        UserInfo test = userInfoNetWorkResultBean.getData();
                        Log.e("jwjw", test.toString());


                        PreferenceUtil.save(LoginPageActivity.this, PreferenceConstant.AreaCode, userInfoNetWorkResultBean.getData().getAreaCode());
                        Intent intent = new Intent(LoginPageActivity.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(LoginPageActivity.this, "登录成功！", Toast.LENGTH_LONG).show();
                        LoginPageActivity.this.finish();
                    }
                });

            }
        });

        cbRememberUserNameAndPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strUserName = et_user_name.getText().toString();
                    strPassword = et_password.getText().toString();
                    PreferenceUtil.save(LoginPageActivity.this, PreferenceConstant.USER_NAME, strUserName);
                    PreferenceUtil.save(LoginPageActivity.this, PreferenceConstant.USER_PASSWORD, strPassword);
                } else {
                    PreferenceUtil.save(LoginPageActivity.this, PreferenceConstant.USER_NAME, "");
                    PreferenceUtil.save(LoginPageActivity.this, PreferenceConstant.USER_PASSWORD, "");
                }
            }
        });
    }

}
