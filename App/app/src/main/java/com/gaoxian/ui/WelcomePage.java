package com.gaoxian.ui;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.gaoxian.R;

/**
 * Created by ll on 2015/9/10.
 */
public class WelcomePage extends Activity {
    private Animation animation;
    ImageView welcomeLogoImage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomepage);
        //LOGO的动态效果
        logo_animation();
        // 生成子线程的对象
        Thread t = new welcomethread();
        // 执行子线程
        t.start();

    }
    // 定义一个内部类创建一个新的线程
    class welcomethread extends Thread {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                sleep(5000);// 休眠5秒钟
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            Intent intent = new Intent();// 生成intent对象
            intent.setClass(WelcomePage.this, LoginPageActivity.class);// 启动LoginActivity
            startActivity(intent);
            WelcomePage.this.finish();// 结束欢迎界面
        }
    }

    // 在欢迎界面屏蔽back键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
            return false;
        }
        if(keyCode == KeyEvent.KEYCODE_HOME){
            WelcomePage.this.finish();
        }
        return false;
    }
    //logo动画效果
    private  void logo_animation()
    {
        welcomeLogoImage=(ImageView)findViewById(R.id.imageview);
        animation = AnimationUtils.loadAnimation(this, R.anim.welcome_logo);// 使用AnimationUtils类的静态方法loadAnimation()来加载XML中的动画XML文件
        animation.setFillAfter(true);
        welcomeLogoImage.startAnimation(animation);// 开始动画播出
    }

    }
