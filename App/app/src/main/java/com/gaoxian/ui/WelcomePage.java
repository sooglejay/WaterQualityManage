package com.gaoxian.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.gaoxian.R;

import java.util.Objects;

/**
 * Created by ll on 2015/9/10.
 */
public class WelcomePage extends Activity {
    private ImageView welcomeLogoImage;
    private AnimatorSet set = new AnimatorSet();//动画集
    private ObjectAnimator xAnimator;//x轴动画
    private ObjectAnimator yAnimator;//y轴动画


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomepage);

        welcomeLogoImage = (ImageView) findViewById(R.id.imageview);
        xAnimator = ObjectAnimator.ofFloat(welcomeLogoImage, "scaleX", 0f,1f);
        yAnimator = ObjectAnimator.ofFloat(welcomeLogoImage, "scaleY", 0f,1f);
        set.setDuration(1000);
        set.playTogether(xAnimator, yAnimator);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                welcomeLogoImage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(WelcomePage.this, LoginPageActivity.class));
                WelcomePage.this.finish();// 结束欢迎界面
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        set.start();

    }

    // 在欢迎界面屏蔽back键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
            return false;
        }
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            WelcomePage.this.finish();
        }
        return false;
    }

}
