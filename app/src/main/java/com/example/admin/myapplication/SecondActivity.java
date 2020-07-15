package com.example.admin.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;

import static android.view.Window.ID_ANDROID_CONTENT;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";
    private int statusBarHeight;
    private int screenWidth;
    private int screenHeight;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Utils.isLollipop()) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        statusBarHeight = 0;
        int resourceId = getApplicationContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getApplicationContext().getResources().getDimensionPixelSize(resourceId);
        }
        Log.d(TAG, "statusBarHeight: " + statusBarHeight);

        DisplayMetrics displayMetric = getResources().getDisplayMetrics();
        screenWidth = displayMetric.widthPixels;
        screenHeight = displayMetric.heightPixels;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);


        }
        setContentView(R.layout.activity_second);
//        StatusBarCompat.setStatusBarColor(this, getResources().getColor(android.R.color.white), true);
        view = findViewById(R.id.container);
        EditText search = (EditText) findViewById(R.id.search_all);
        TextView tv = (TextView) findViewById(R.id.contact_tv);
        tv.setCompoundDrawablePadding(12);
        tv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.company_logo), null, null, null);
//        Utils.showKeyboard(search);

        View tab_icon = findViewById(R.id.chat_group_img);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f, 1.06f, 0.94f, 1.0f).setDuration(300);
        tab_icon.setOnClickListener((v -> {
            showBlurDialog(tab_icon);
            /*valueAnimator.cancel();
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    Log.d(TAG, "onAnimationUpdate: " + value);
                    tab_icon.setScaleX(value);
                    tab_icon.setScaleY(value);
                }
            });
            valueAnimator.start();*/

        }));
        ((ProgressBar) findViewById(R.id.progress))
                .getIndeterminateDrawable()
                .setColorFilter(Color.parseColor("#1890FF"), PorterDuff.Mode.SRC_IN);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void addTransitionListener() {
        final Transition enterTransition = getWindow().getEnterTransition();
        if (enterTransition != null) {
            // There is an entering shared element transition so add a listener to it
            enterTransition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    // As the transition has ended, we can now load the full-size image
                    Log.d(TAG, "enterTransition onTransitionEnd: ");
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionStart(Transition transition) {
                    // No-op
                    Log.d(TAG, "enterTransition onTransitionStart: ");
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionPause(Transition transition) {
                    // No-op
                }

                @Override
                public void onTransitionResume(Transition transition) {
                    // No-op
                }
            });
        }

        final Transition exitTransition = getWindow().getExitTransition();
        if (exitTransition != null) {
            // There is an entering shared element transition so add a listener to it
            exitTransition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    // As the transition has ended, we can now load the full-size image
                    Log.d(TAG, "exitTransition onTransitionEnd: ");
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionStart(Transition transition) {
                    // No-op
                    Log.d(TAG, "exitTransition onTransitionStart: ");
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionPause(Transition transition) {
                    // No-op
                }

                @Override
                public void onTransitionResume(Transition transition) {
                    // No-op
                }
            });
        }

    }

    private void showBlurDialog(View tab_icon) {
        int[] location = new int[2];
//        tab_icon.getLocationInWindow(location);
        tab_icon.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        boolean closeRight = (left + Utils.dp2px(184)) > screenWidth;

        final Dialog dialog = new Dialog(this, R.style.dialog);
        View layout = View.inflate(this, closeRight ? R.layout.layout_blur_dialog_right : R.layout.layout_blur_dialog, null);
        layout.setOnClickListener((v) -> {
            dialog.dismiss();
        });
        dialog.setContentView(layout);

        Window window = dialog.getWindow();

        window.setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        window.setWindowAnimations(R.style.dialogWindowAnim);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f, 0.9f, 1.2f).setDuration(800);

        LinearLayout content = (LinearLayout) layout.findViewById(R.id.content);
        View items = layout.findViewById(R.id.items);
        View icon = layout.findViewById(R.id.icon);
        layout.findViewById(R.id.one).setOnClickListener(v -> {
            dialog.dismiss();
        });
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) content.getLayoutParams();
        if (closeRight) {
            params.leftMargin = left - Utils.dp2px(184 - 50);
//            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) icon.getLayoutParams();
//            layoutParams.gravity= Gravity.END;
        } else {
            params.leftMargin = left;
        }
        if (Utils.isMIUI()) {
            statusBarHeight = 0;
        }
        params.topMargin = top - statusBarHeight - Utils.dp2px(108 + 8);
        content.setLayoutParams(params);
        dialog.show();

        long startMs = System.currentTimeMillis();
        // 获取截图
        View activityView = getWindow().getDecorView();
        activityView.setDrawingCacheEnabled(true);
        activityView.destroyDrawingCache();
        activityView.buildDrawingCache();
        Bitmap bmp = activityView.getDrawingCache();
        Log.d(TAG, "getDrawingCache take away:" + (System.currentTimeMillis() - startMs) + "ms");
        // 模糊处理并保存
        final Bitmap finalBlurBg = Utils.blur(this, bmp);
        view.setBackground(new BitmapDrawable(getResources(), finalBlurBg));

        Log.d(TAG, "blur take away:" + (System.currentTimeMillis() - startMs) + "ms");
        bmp.recycle();
        dialog.setOnDismissListener(dialog1 -> {
            // 对话框取消时释放背景图bitmap
            if (finalBlurBg != null && !finalBlurBg.isRecycled()) {
                finalBlurBg.recycle();
            }
            valueAnimator.cancel();
            tab_icon.setVisibility(View.VISIBLE);
            view.setVisibility(View.GONE);
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if (value < 1f) {
                    tab_icon.setScaleX(value);
                    tab_icon.setScaleY(value);
                }else {
                    tab_icon.setVisibility(View.INVISIBLE);
                }
                icon.setScaleX(value);
                icon.setScaleY(value);

            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                items.setVisibility(View.VISIBLE);
                view.setVisibility(View.VISIBLE);
            }
        });
//        tab_icon.setVisibility(View.VISIBLE);
//        icon.setVisibility(View.VISIBLE);
        valueAnimator.start();


    }


    private void fullScreenImmersive(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            view.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Utils.isLollipop()) {
            finishAfterTransition();
        }

    }
}