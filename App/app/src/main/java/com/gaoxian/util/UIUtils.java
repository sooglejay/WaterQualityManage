package com.gaoxian.util;
import android.content.Context;
import android.util.TypedValue;

public class UIUtils {
    /**
     * px转换为dp
     *
     * @param context
     * @param dp
     * @return
     */
    public static float dp2px(Context context, int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * px转换为sp
     *
     * @param context
     * @param sp
     * @return
     */
    public static float sp2px(Context context, int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

}
