package com.gaoxian.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import de.greenrobot.event.EventBus;

public class BaseFragment extends Fragment {
    public void onEvent(Object object){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        // setHasOptionsMenu(true);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
