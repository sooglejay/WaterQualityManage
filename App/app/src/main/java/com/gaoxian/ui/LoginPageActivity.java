package com.gaoxian.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.gaoxian.R;
import com.gaoxian.api.User.UserRetrofitUtil;
import com.gaoxian.api.WM.GetStationsUtil;
import com.gaoxian.api.callback.NetCallback;
import com.gaoxian.model.NetWorkResultBean;
import com.gaoxian.model.StationInfo;
import com.gaoxian.model.StationInfoPackge;
import com.gaoxian.model.UserInfo;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginPageActivity extends Activity {
    private EditText username,password;
    private Button loginbutton;
    private CheckBox rememberpassword;
    private String usernameString,passwordString;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        initUI();
        rememberpassword();
    }

    /**
     * 初始化UI
     */
    private void initUI()
    {
        username=(EditText)findViewById(R.id.username_edittext);
        password=(EditText)findViewById(R.id.password_edittext);
        loginbutton=(Button)findViewById(R.id.login_bn);
        rememberpassword=(CheckBox)findViewById(R.id.rememberpassword_checkbox);
        if(rememberpassword.isChecked())
        {
            username.setText(getusername());
            password.setText(getpassword());
        }
        usernameString=username.getText().toString();
        passwordString=password.getText().toString();
        sharedPreferences=getSharedPreferences("userinfo",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                UserRetrofitUtil.login(LoginPageActivity.this,usernameString,passwordString,"weiqi",new NetCallback<NetWorkResultBean<UserInfo>>(LoginPageActivity.this) {
//                    @Override
//                    public void onFailure(RetrofitError error) {
//                        Toast.makeText(getBaseContext(),"登录失败！",Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void success(NetWorkResultBean<UserInfo> userInfoNetWorkResultBean, Response response) {
//                       //登录成功则启动主界面
//                        Intent intent=new Intent(LoginPageActivity.this,MainActivity.class);
//                        startActivity(intent);
//                        Toast.makeText(getBaseContext(),"登录成功！",Toast.LENGTH_LONG).show();
//                    }
//                });
    
                        Intent intent=new Intent(LoginPageActivity.this,MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getBaseContext(),"登录成功！",Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 勾选“记住密码”则保存当前用户名和密码
     */
    private void rememberpassword()
    {
       if(rememberpassword.isChecked())
       {
           if(usernameString!=null&&!"".equals(usernameString)&&passwordString!=null&&!"".equals(passwordString))
           {
               editor.putString("username",usernameString);
               editor.putString("password",passwordString);
               editor.commit();
           }
       }
    }

    /**
     * 从sharedpreference获取用户名和密码
     */
    private String getusername()
    {
        return sharedPreferences.getString("username",null);
    }
    private String getpassword()
    {
        return sharedPreferences.getString("password",null);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if(!rememberpassword.isChecked())
        {
            username.setText(null);
            password.setText(null);
        }
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        if(!rememberpassword.isChecked())
        {
            username.setText(null);
            password.setText(null);
        }
    }
}
