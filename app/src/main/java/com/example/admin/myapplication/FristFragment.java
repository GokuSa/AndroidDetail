package com.example.admin.myapplication;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.transition.Transition;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.githang.statusbar.StatusBarCompat;


/**
 * A simple {@link Fragment} subclass.
 */
public class FristFragment extends Fragment {
    private static final String TAG = "FristFragment";
    private RelativeLayout titleBar;
    private int topBarHeight;
    private NestedScrollView nestedScrollView;

    public FristFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frist, container, false);
    }

    private int lastVisibleScrollY;

    private boolean needFadeAnimation = true;

    private boolean animationOut = false;
    private View rootView;

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        this.rootView = rootView;
        titleBar = (RelativeLayout) rootView.findViewById(R.id.mobark_topbar_layout);
        topBarHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 44, getResources().getDisplayMetrics());
        View editsearch = rootView.findViewById(R.id.seach_bar);
        View topSearch = rootView.findViewById(R.id.topbar_search);

        nestedScrollView = (NestedScrollView) rootView.findViewById(R.id.nest_scrollView);


        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            android.util.Log.d(TAG, scrollY + " OnScroll() " + oldScrollY);
            if (scrollY > oldScrollY) {
                //上滑达到titleBar高度，并且没有执行过渐变动画
                if ((scrollY - lastVisibleScrollY) >= topBarHeight && needFadeAnimation) {
                    needFadeAnimation = false;
                    Log.d(TAG, scrollY + "执行隐藏动画" + oldScrollY);
                    ViewCompat.animate(titleBar).alpha(0f).setDuration(200).start();
                }
            } else/* if ((oldScrollY - scrollY) > 1)*/ {
                lastVisibleScrollY = oldScrollY;
                //下滑超过1px 防止上划时手指向下抖动，并且titleBar还没有完全显示，执行显示动画
                if (!needFadeAnimation) {
                    needFadeAnimation = true;
                    Log.d(TAG, scrollY + "执行显示动画" + oldScrollY);
                    ViewCompat.animate(titleBar).alpha(1f).setDuration(200).start();
                }
            }
        });
        View search = rootView.findViewById(R.id.iv_search);
        search.setOnClickListener((v) -> {
            View poplayout = LayoutInflater.from(getContext()).inflate(R.layout.layout_popup, null);
            PopupWindow popupWindow = new PopupWindow(poplayout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setAnimationStyle(R.style.popupwindow_anim_style);
            poplayout.findViewById(R.id.one).setOnClickListener((view) -> {
                popupWindow.dismiss();
                changeStatusBarColor();
            });
            poplayout.findViewById(R.id.two).setOnClickListener((view) -> {
                popupWindow.dismiss();
            });
            poplayout.findViewById(R.id.three).setOnClickListener((view) -> {
                popupWindow.dismiss();
            });

            popupWindow.showAtLocation(rootView, Gravity.END | Gravity.TOP, Utils.dp2px(6), Utils.dp2px(64));


            /*Intent intent = new Intent(getActivity(), SecondActivity.class);
            if (Utils.isLollipop()) {
                nestedScrollView.setVisibility(View.GONE);
                topSearch.animate().translationY(-topBarHeight).setInterpolator(new LinearInterpolator()).setDuration(200).start();
                editsearch.setVisibility(View.VISIBLE);
            } else {
                startActivity(intent);
            }*/
        });

        rootView.findViewById(R.id.tv_cancle).setOnClickListener((v -> {
//            editsearch.animate().translationY(0).setDuration(200).start();

            topSearch.animate().translationY(0).setInterpolator(new LinearInterpolator()).setDuration(200).withEndAction(() -> {
                editsearch.setVisibility(View.INVISIBLE);
                nestedScrollView.setVisibility(View.VISIBLE);
            }).start();
        }));


    }


    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getActivity().getWindow().getDecorView();
            int option = decorView.getSystemUiVisibility();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                option &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            }
            decorView.setSystemUiVisibility(option);
        }
    }


}
