package com.gaoxian.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gaoxian.R;
import com.gaoxian.events.IntEvent;
import com.gaoxian.fragment.AddMedicineFragment;
import com.gaoxian.fragment.MapInfoFragment;
import com.gaoxian.fragment.ProductionProcessFragment;
import com.gaoxian.fragment.WaterQualityInfoFragment;
import com.gaoxian.widget.ScrollableViewPager;
import com.gaoxian.widget.TabBar;

import de.greenrobot.event.EventBus;

public class MainActivity extends BaseActivity {
    private ScrollableViewPager viewPager = null;
    private TabBar tabBar = null;
    private View lineView = null;
    private ViewPagerAdapter viewPagerAdapter = null;
    private int currentPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUp();
    }

    private void setUp() {
        lineView = findViewById(R.id.line_view);
        tabBar = (TabBar) findViewById(R.id.home_bottomBar);
        initViewPager();
    }

    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        viewPager = (ScrollableViewPager) findViewById(R.id.view_pager);
        viewPager.removeAllViews();
        viewPager.setOffscreenPageLimit(10);

        viewPagerAdapter = new ViewPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabBar.changeImageView(viewPager.getCurrentItem(), position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                tabBar.selectTab(position);
                EventBus.getDefault().post(new IntEvent(IntEvent.Msg_ResetViewScale));

                //下面的这句话，必须要加，因为百度地图ontouch 方法里面禁用了滑动
                EventBus.getDefault().post(new IntEvent(IntEvent.Msg_Enable_ViewPager_Scroll));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //选中当前
        viewPager.setCurrentItem(currentPos);
        tabBar.selectTab(viewPager.getCurrentItem());
        tabBar.setOnTabClickListener(new TabBar.OnTabClickListener() {
            @Override
            public void onTabClick(int position) {
                viewPager.setCurrentItem(position, false);
            }
        });
    }

    /*
     * 首页viewpager的adapter
     */
    private static final class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new ProductionProcessFragment();
                    break;
                case 1:
                    fragment = new AddMedicineFragment();
                    break;
                case 2:
                    fragment = new WaterQualityInfoFragment();
                    break;
                case 3:
                    fragment = new MapInfoFragment();
                    break;
                default:
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


    /**
     * EvenBus 接收消息
     *
     * @param event
     */
    public void onEventMainThread(IntEvent event) {

        switch (event.getMsg()) {
            case IntEvent.Msg_Enter_Water_Station:
                viewPager.setCurrentItem(0, false);
                break;
            case IntEvent.Msg_Disable_ViewPager_Scroll:
                viewPager.setScrollable(false);
                break;
            case IntEvent.Msg_Enable_ViewPager_Scroll:
                viewPager.setScrollable(true);
                break;
            default:
                break;
        }
    }
}
