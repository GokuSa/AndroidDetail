package com.example.admin.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.githang.statusbar.StatusBarCompat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*if (Utils.isLollipop()) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setExitTransition(new Fade());
            getWindow().setReenterTransition(new Fade());
        }*/
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            //SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN让应用的内容在状态栏后面，每个有个标记FitSystemWindow不起作用；SYSTEM_UI_FLAG_LAYOUT_STABLE保持应用布局稳定，
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                //SYSTEM_UI_FLAG_LIGHT_STATUS_BAR控制状态栏图标和字体的颜色，LIGHT_STATUS_BAR显示黑色图标和字体，非LIGHT_STATUS_BAR显示白色
                option |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                //设置状态栏的颜色
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
            decorView.setSystemUiVisibility(option);
        }
        setContentView(R.layout.activity_main);
//        StatusBarCompat.setStatusBarColor(this, getResources().getColor(android.R.color.white)/*, true*/);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.center_frame, new ListWithHeaderFragment()).commitAllowingStateLoss();

    }

}
