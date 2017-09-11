package com.photowalking.viewUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by lionel on 2017/8/10.
 */

public class StatusBarUtil {

    private static boolean INIT = false;
    private static int STATUS_BAR_HEIGHT = 50;

    private final static String STATUS_BAR_DEF_PACKAGE = "android";
    private final static String STATUS_BAR_DEF_TYPE = "dimen";
    private final static String STATUS_BAR_NAME = "status_bar_height";

    public static void setTransparent(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity));
            statusBarView.setBackgroundColor(Color.TRANSPARENT);
            contentView.addView(statusBarView, lp);
        }
    }

    public static void setColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(activity,colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized int getStatusBarHeight(final Context context) {
        if (!INIT) {
            int resourceId = context.getResources().
                    getIdentifier(STATUS_BAR_NAME, STATUS_BAR_DEF_TYPE, STATUS_BAR_DEF_PACKAGE);
            if (resourceId > 0) {
                STATUS_BAR_HEIGHT = context.getResources().getDimensionPixelSize(resourceId);
                INIT = true;
            }
        }

        return STATUS_BAR_HEIGHT;
    }
}
