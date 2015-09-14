package com.gaoxian.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import com.gaoxian.R;

public class LoginPageActivity extends Activity {

    private Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
    }
}
