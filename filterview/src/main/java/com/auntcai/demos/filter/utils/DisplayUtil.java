package com.auntcai.demos.filter.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Description: 显示相关util
 *
 * @author chengsiyu
 * @since 16/5/14.
 */
public class DisplayUtil {
    private static int mScreenWidth;
    private static int mScreenHeight;

    public static int dip2px(Context context, float dipValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / m + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        if (mScreenWidth == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
            mScreenWidth = dm.widthPixels;
        }
        return mScreenWidth;
    }

    public static int getScreenHeight(Context context) {
        if (mScreenHeight == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
            mScreenHeight = dm.heightPixels;
        }
        return mScreenHeight;
    }
}
