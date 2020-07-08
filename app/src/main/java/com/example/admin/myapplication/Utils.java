package com.example.admin.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class Utils {
    private Utils(){}
    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static void showKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }

    public static void StatusBarLightModeTransparent(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            setStatusBarColorWithHeight(activity,activity.getResources().getColor(R.color.colorAccent));
        }
    }

    public static void setStatusBarColorWithHeight(Activity activity,int color) {
        if (Build.VERSION.SDK_INT >= 23) {//Build.VERSION_CODES.M
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

            // 部分机型的statusbar会有半透明的黑色背景
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);// SDK21
            if (getNavigationBarHeight(activity) > 0) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

                // 部分机型的statusbar会有半透明的黑色背景
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            //清除系统提供的默认保护色
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            );
            //设置系统UI的显示方式
            window.getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //添加属性可以自定义设置系统工具栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);// SDK21
            if (getNavigationBarHeight(activity) > 0) {
                window.setNavigationBarColor(activity.getResources().getColor(android.R.color.black));// SDK21
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //4.4~5.0之间自己设置
//            //设置statusbar隐藏
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //创建statusbarview设置背景 高度等于系统的statusbar高度
//            View statusBarView = getStatusBarView(activity);
//            statusBarView.setBackgroundColor(Color.TRANSPARENT);
//            //获得contentview 并添加创建的statusbarview
//            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getStatusBarHeigh(activity));
//            params.gravity = Gravity.TOP;
//            statusBarView.setLayoutParams(params);
//            decorView.addView(statusBarView);
        } else {
            //4.4以下无法设置statusbar颜色
        }
    }

    public static  int getNavigationBarHeight(Activity activity) {
        int height = 0;
        int id = activity.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0) {
            height = activity.getResources().getDimensionPixelSize(id);
        }
        return height;
    }

    public static int getStatusBarHeigh(Context context) {
        int height = 0;
        int id = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (id > 0) {
            height = context.getResources().getDimensionPixelSize(id);
        }
        return height;
    }

    public static int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }
}
