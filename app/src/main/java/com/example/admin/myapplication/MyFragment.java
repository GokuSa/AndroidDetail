package com.example.admin.myapplication;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.githang.statusbar.StatusBarCompat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment extends Fragment implements View.OnTouchListener {
    private static final String TAG = "MyFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private float curr;
    private View bg;
    private NestedScrollView container;
    private int mZoomViewWidth = 0;
    private int mZoomViewHeight = 0;
    private View layout;
    private boolean scaling = false;
    private boolean canScale = false;

    public MyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFragment newInstance(String param1, String param2) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setTranslucent(getActivity().getWindow(), true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        container = (NestedScrollView) view.findViewById(R.id.container);
        View topbar = view.findViewById(R.id.mobark_topbar_layout);
        container.setOnTouchListener(this);
        int distance = Utils.dp2px(180 - 69);
        container.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.d(TAG, "onScrollChange() called with: scrollY = [" + scrollY + "], oldScrollY = [" + oldScrollY + "]");
                if (scrollY < distance) {
                    topbar.setAlpha(scrollY * 1.0f / distance);
                } else {
                    topbar.setAlpha(1.0f);
                }
            }
        });
        bg = view.findViewById(R.id.bg);
        layout = view.findViewById(R.id.layout);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        Log.d(TAG, "onTouch: " + event);
        if (mZoomViewWidth <= 0 || mZoomViewHeight <= 0) {
            mZoomViewWidth = bg.getMeasuredWidth();
            mZoomViewHeight = bg.getMeasuredHeight();
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (!scaling) {
                    if (v.getScrollY() == 0) {
                        curr = event.getY();
                        Log.d(TAG, "curr: "+curr);
                    } else {
                        break;
                    }
                }

                int distance = (int) ((event.getY() - curr) * 0.8f);
                Log.d(TAG, "distance: " + distance);
                if ( distance >= 0) {
                    scaling = true;
                    setZoom(distance);
                    layout.scrollTo(0, -distance / 2);
                }else{
                    break;
                }
               /* if (distance < 0) {
                    break;
                }*/

                return true;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "ACTION_UP: "+layout.getScrollY());
                scaling = false;
                if (layout.getScrollY()<0) {
                    Log.d(TAG, "onTouch: antion up");
                    replyImage();
                    replyLayout(layout.getScrollY());
                }
                break;
        }

        return v.onTouchEvent(event);
    }

    private void replyImage() {
        int distance = bg.getMeasuredWidth() - mZoomViewWidth;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(distance, 0).setDuration(150);
        valueAnimator.addUpdateListener(animation -> {
            setZoom((Integer) animation.getAnimatedValue());

        });
        valueAnimator.start();
    }

    private void replyLayout(int distance) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(distance, 0).setDuration(150);
        valueAnimator.addUpdateListener(animation -> {
            layout.scrollTo(0, (Integer) animation.getAnimatedValue());

        });
        valueAnimator.start();
    }


    public void setZoom(int distance) {

        ViewGroup.LayoutParams lp = bg.getLayoutParams();
        lp.width = mZoomViewWidth + distance;
        lp.height = (int) (mZoomViewHeight * ((mZoomViewWidth + distance) * 1f / mZoomViewWidth));
        ((ViewGroup.MarginLayoutParams) lp).setMargins(-(lp.width - mZoomViewWidth) / 2, 0, 0, 0);
        bg.setLayoutParams(lp);
    }
}