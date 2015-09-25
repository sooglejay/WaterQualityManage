package com.gaoxian.util;


import android.app.ProgressDialog;
import android.content.Context;
import android.widget.TextView;


/**
 * Created by jiangwei on 15/9/25.
 */
public class ProgressDialogUtil {
    private ProgressDialog dialog;
    private TextView tv;
    public ProgressDialogUtil(Context context){
        initDialog(context);
    }

    private void initDialog(Context context) {
        dialog = new ProgressDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    }


    /**
     * 显示
     */
    public void show(String text){
        if(dialog != null) {
            dialog.setMessage(text);
            dialog.show();
        }
    }

    /**
     * 隐藏
     */
    public void hide(){
        if(dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
