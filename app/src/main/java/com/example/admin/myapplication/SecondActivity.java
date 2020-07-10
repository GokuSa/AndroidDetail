package com.example.admin.myapplication;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";
    private int statusBarHeight;
    private int screenWidth;

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

        DisplayMetrics displayMetric = getResources().getDisplayMetrics();
        screenWidth =displayMetric.widthPixels;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_second);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(android.R.color.white), true);

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
        ((ProgressBar)findViewById(R.id.progress))
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
        tab_icon.getLocationInWindow(location);
        int left = location[0];
        int top = location[1];
        final Dialog dialog = new Dialog(this, R.style.SquareEntranceDialogStyle);
        View layout = View.inflate(this, R.layout.layout_blur_dialog, null);
        dialog.setContentView(layout);

        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.dialogWindowAnim);

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
        Log.d(TAG, "blur take away:" + (System.currentTimeMillis() - startMs) + "ms");
        // 设置成dialog的背景
        layout.setBackgroundDrawable(new BitmapDrawable(getResources(), finalBlurBg));
        bmp.recycle();

        dialog.setOnDismissListener(dialog1 -> {
            // 对话框取消时释放背景图bitmap
            Log.d(TAG, "onDismiss: ");
            if (finalBlurBg != null && !finalBlurBg.isRecycled()) {
                finalBlurBg.recycle();
            }
        });
        LinearLayout content = (LinearLayout) layout.findViewById(R.id.content);
        View icon =  layout.findViewById(R.id.icon);
        layout.findViewById(R.id.one).setOnClickListener(v -> {
            Log.d(TAG, "showBlurDialog: one");
            dialog.dismiss();
        });
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) content.getLayoutParams();
        boolean closeRight = (left + Utils.dp2px(176))>screenWidth;
        if (closeRight) {
            params.leftMargin=left-Utils.dp2px(176-50);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) icon.getLayoutParams();
            layoutParams.gravity= Gravity.END;
        }else{
            params.leftMargin=left;
        }
        params.topMargin=top- statusBarHeight-Utils.dp2px(100+8);
        content.setLayoutParams(params);

        layout.setOnClickListener((v) -> {
            dialog.dismiss();
        });
        dialog.show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Utils.isLollipop()) {
            finishAfterTransition();
        }

    }
}