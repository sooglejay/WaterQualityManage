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

import com.gaoxian.Constant.PreferenceConstant;
import com.gaoxian.R;
import com.gaoxian.api.User.UserRetrofitUtil;
import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.model.UserInfo;
import com.gaoxian.util.PreferenceUtil;
import com.gaoxian.util.ProgressDialogUtil;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginPageActivity extends Activity {
    private EditText etUserName;
    private EditText etPassword;
    private Button btnLogin;
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
        etUserName=(EditText)findViewById(R.id.username_edittext);
        etPassword=(EditText)findViewById(R.id.password_edittext);
        btnLogin =(Button)findViewById(R.id.login_bn);
        cbRememberUserNameAndPassword =(CheckBox)findViewById(R.id.rememberpassword_checkbox);

        strPassword = PreferenceUtil.load(this,PreferenceConstant.USER_PASSWORD,"");
        strUserName = PreferenceUtil.load(this,PreferenceConstant.USER_NAME,"");
        cbRememberUserNameAndPassword.setChecked(!TextUtils.isEmpty(strUserName));

        etUserName.setText(strUserName);
        etUserName.setSelection(strUserName.length());
        etPassword.setText(strPassword);

        progressDialogUtil = new ProgressDialogUtil(this);
    }

    /**
     * 初始化UI事件监听器
     */
    private void setUpLisenter() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialogUtil.show("考验网速的时候到了！");
                UserRetrofitUtil.login(LoginPageActivity.this, "mytest", "123000", "weiqi", new NetCallback<NetWorkResultBean<UserInfo>>(LoginPageActivity.this) {
                    @Override
                    public void onFailure(RetrofitError error) {
                        progressDialogUtil.hide();
                    }

                    @Override
                    public void success(NetWorkResultBean<UserInfo> userInfoNetWorkResultBean, Response response) {
                        progressDialogUtil.hide();
                        PreferenceUtil.save(LoginPageActivity.this, PreferenceConstant.AreaCode,userInfoNetWorkResultBean.getData().getAreaCode());
                        Intent intent = new Intent(LoginPageActivity.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText( LoginPageActivity.this, "登录成功！", Toast.LENGTH_LONG).show();
                        LoginPageActivity.this.finish();
                    }
                });

            }
        });

        cbRememberUserNameAndPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    strUserName = etUserName.getText().toString();
                    strPassword = etPassword.getText().toString();
                    PreferenceUtil.save(LoginPageActivity.this, PreferenceConstant.USER_NAME, strUserName);
                    PreferenceUtil.save(LoginPageActivity.this, PreferenceConstant.USER_PASSWORD,strPassword);
                    Toast.makeText(LoginPageActivity.this,"hee wordl check",Toast.LENGTH_SHORT).show();
                }
                else {
                    PreferenceUtil.save(LoginPageActivity.this, PreferenceConstant.USER_NAME,"");
                    PreferenceUtil.save(LoginPageActivity.this, PreferenceConstant.USER_PASSWORD,"");
                    Toast.makeText(LoginPageActivity.this,"not  check",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
