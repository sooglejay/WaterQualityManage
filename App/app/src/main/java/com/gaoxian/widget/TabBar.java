package com.gaoxian.widget;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaoxian.R;


public class TabBar extends LinearLayout {
    private TextView tv0;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;

    private ImageView iv0;
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;

    private ImageView iv0Selected;
    private ImageView iv1Selected;
    private ImageView iv2Selected;
    private ImageView iv3Selected;


    private View container0;
    private View container1;
    private View container2;
    private View container3;

    private AnimatorListener mAnimatorListener;

    private boolean isShowAnimator = false;

    private OnTabClickListener onTabClickListener;

    public TabBar(Context context) {
        super(context);
        initView(context);
    }

    public TabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void setOnTabClickListener(OnTabClickListener listener) {
        this.onTabClickListener = listener;
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_tab_bar, this, true);

        tv0 = (TextView) findViewById(R.id.tv0);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);

        iv0 = (ImageView) findViewById(R.id.iv0);
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);


        iv0Selected = (ImageView) findViewById(R.id.iv0_selected);
        iv1Selected = (ImageView) findViewById(R.id.iv1_selected);
        iv2Selected = (ImageView) findViewById(R.id.iv2_selected);
        iv3Selected = (ImageView) findViewById(R.id.iv3_selected);

        container0 = findViewById(R.id.layout0);
        container1 = findViewById(R.id.layout1);
        container2 = findViewById(R.id.layout2);
        container3 = findViewById(R.id.layout3);

        mAnimatorListener = new AnimatorListener();

        container0.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTab(0);
                if (onTabClickListener != null) {
                    onTabClickListener.onTabClick(0);
                }
            }
        });

        container1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTab(1);
                if (onTabClickListener != null) {
                    onTabClickListener.onTabClick(1);
                }
            }
        });

        container2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTab(2);
                if (onTabClickListener != null) {
                    onTabClickListener.onTabClick(2);
                }
            }
        });

        container3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTab(3);
                if (onTabClickListener != null) {
                    onTabClickListener.onTabClick(3);
                }
            }
        });


    }

    /**
     * 设置tab是否显示
     *
     * @param pos
     * @param flag
     */
    public void setTabVisibility(int pos, int flag) {
        switch (pos) {
            case 0:
                container0.setVisibility(flag);
                break;
            case 1:
                container1.setVisibility(flag);
                break;
            case 2:
                container2.setVisibility(flag);
            case 3:
                container3.setVisibility(flag);
                break;
            default:
                break;
        }
    }

    /**
     * 选择tab
     *
     * @param position
     */
    public void selectTab(int position) {
        switch (position) {
            case 0:
                tv0.setTextColor(getResources().getColor(R.color.tab_blue_color));
                tv1.setTextColor(getResources().getColor(R.color.middle_gray_color));
                tv2.setTextColor(getResources().getColor(R.color.middle_gray_color));
                tv3.setTextColor(getResources().getColor(R.color.middle_gray_color));
//
//                iv0Selected.animate().alpha(1.0f).setListener(mAnimatorListener).start();
//                iv0.animate().alpha(0f).setListener(mAnimatorListener).start();

                iv0Selected.setAlpha(1.0f);
                iv0.setAlpha(0f);


                iv1Selected.setAlpha(0f);
                iv1.setAlpha(1.0f);
                iv2Selected.setAlpha(0f);
                iv2.setAlpha(1.0f);
                iv3Selected.setAlpha(0f);
                iv3.setAlpha(1.0f);
                break;
            case 1:

                tv0.setTextColor(getResources().getColor(R.color.middle_gray_color));
                tv1.setTextColor(getResources().getColor(R.color.tab_blue_color));
                tv2.setTextColor(getResources().getColor(R.color.middle_gray_color));
                tv3.setTextColor(getResources().getColor(R.color.middle_gray_color));

                iv0Selected.setAlpha(0f);
                iv0.setAlpha(1.0f);

                iv1Selected.setAlpha(1.0f);
                iv1.setAlpha(0f);
//                iv1Selected.animate().alpha(1.0f).setListener(mAnimatorListener).start();
//                iv1.animate().alpha(0f).setListener(mAnimatorListener).start();

                iv2Selected.setAlpha(0f);
                iv2.setAlpha(1.0f);

                iv3Selected.setAlpha(0f);
                iv3.setAlpha(1.0f);
                break;
            case 2:

                tv0.setTextColor(getResources().getColor(R.color.middle_gray_color));
                tv1.setTextColor(getResources().getColor(R.color.middle_gray_color));
                tv2.setTextColor(getResources().getColor(R.color.tab_blue_color));
                tv3.setTextColor(getResources().getColor(R.color.middle_gray_color));
                iv0Selected.setAlpha(0f);
                iv0.setAlpha(1.0f);
                iv1Selected.setAlpha(0f);
                iv1.setAlpha(1.0f);


                iv2Selected.setAlpha(1.0f);
                iv2.setAlpha(0f);

                iv3Selected.setAlpha(0f);
                iv3.setAlpha(1.0f);
//                iv2Selected.animate().alpha(1.0f).setListener(mAnimatorListener).start();
//                iv2.animate().alpha(0f).setListener(mAnimatorListener).start();
                break;
            case 3:

                tv0.setTextColor(getResources().getColor(R.color.middle_gray_color));
                tv1.setTextColor(getResources().getColor(R.color.middle_gray_color));
                tv2.setTextColor(getResources().getColor(R.color.middle_gray_color));
                tv3.setTextColor(getResources().getColor(R.color.tab_blue_color));

                iv0Selected.setAlpha(0f);
                iv0.setAlpha(1.0f);


                iv1Selected.setAlpha(0f);
                iv1.setAlpha(1.0f);
//                iv1Selected.animate().alpha(1.0f).setListener(mAnimatorListener).start();
//                iv1.animate().alpha(0f).setListener(mAnimatorListener).start();

                iv2Selected.setAlpha(0f);
                iv2.setAlpha(1.0f);

                iv3Selected.setAlpha(1.0f);
                iv3.setAlpha(0f);

                break;
            default:
                break;
        }
    }

    private class AnimatorListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {
            isShowAnimator = true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            isShowAnimator = false;
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

    public void changeImageView(int currentPosition, int position, float positionOffset, int positionOffsetPixels) {

        if (position != currentPosition) {
            changeCurrentAlpha(currentPosition, positionOffset);

        } else {
            changePreviousAlpha(position, positionOffset);
        }

    }

    /**
     * 调整
     *
     * @param position
     * @param positionOffset
     */
    private void changePreviousAlpha(int position, float positionOffset) {
        if (position == 0) {
            iv0Selected.setAlpha(1 - positionOffset);
            iv0.setAlpha(positionOffset);

//            iv1Selected.setAlpha(positionOffset);
//            iv1.setAlpha(1 - positionOffset);

//            int currentAlpha = (int)(positionOffset*255);
//            int nextAlpha = (int)((1-positionOffset)*255);
//            tv0.setTextColor(Color.argb(currentAlpha, Color.red(getResources().getColor(R.color.middle_gray_color)), Color.green(getResources().getColor(R.color.middle_gray_color)), Color.blue(getResources().getColor(R.color.middle_gray_color) )));
//            tv1.setTextColor(Color.argb(nextAlpha, Color.red(getResources().getColor(R.color.tab_blue_color)), Color.green(getResources().getColor(R.color.tab_blue_color)), Color.blue(getResources().getColor(R.color.tab_blue_color))));
//

        } else if (position == 1) {
            iv1Selected.setAlpha(1 - positionOffset);
            iv1.setAlpha(positionOffset);
        } else if (position == 2) {
            iv2Selected.setAlpha(1 - positionOffset);
            iv2.setAlpha(positionOffset);
        } else if (position == 3) {
            iv3Selected.setAlpha(1 - positionOffset);
            iv3.setAlpha(positionOffset);
        }

    }

    private void changeCurrentAlpha(int position, float positionOffset) {
        if (position == 0) {
            iv0Selected.setAlpha(positionOffset);
            iv0.setAlpha(1 - positionOffset);
        } else if (position == 1) {
            iv1Selected.setAlpha(positionOffset);
            iv1.setAlpha(1 - positionOffset);
        } else if (position == 2) {
            iv2Selected.setAlpha(positionOffset);
            iv2.setAlpha(1 - positionOffset);
        } else if (position == 3) {
            iv3Selected.setAlpha(positionOffset);
            iv3.setAlpha(1 - positionOffset);
        }
    }


    public interface OnTabClickListener {
        public void onTabClick(int position);
    }
}
