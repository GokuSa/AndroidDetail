package com.example.admin.myapplication;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Utils.isLollipop()) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
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
            valueAnimator.cancel();
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    Log.d(TAG, "onAnimationUpdate: " + value);
                    tab_icon.setScaleX(value);
                    tab_icon.setScaleY(value);
//                    tab_icon.invalidate();
                }
            });
            valueAnimator.start();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Utils.isLollipop()) {
            finishAfterTransition();
        }

    }
}