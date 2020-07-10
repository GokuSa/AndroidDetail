package com.example.admin.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListWithHeaderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListWithHeaderFragment extends Fragment {
    private static final String TAG = "ListWithHeaderFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View head;
    private boolean needFadeAnimation=true;

    public ListWithHeaderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListWithHeaderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListWithHeaderFragment newInstance(String param1, String param2) {
        ListWithHeaderFragment fragment = new ListWithHeaderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_list_with_header, container, false);
        head = inflater.inflate(R.layout.layout_popup, null);

        return root;
    }

    private String[] items = new String[20];

    int oldY = 0;
    //消失的距离
    int disappear_distance;

    int up_distance;

    boolean transition;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = (ListView) view.findViewById(R.id.listview);
        View titleBar = view.findViewById(R.id.titlebar);

        for (int i = 0; i < items.length; i++) {
            items[i] = "items" + i;
        }
        listView.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, items));

        listView.addHeaderView(head);
        disappear_distance = Utils.dp2px(44);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int[] startLocation = new int[2];
                view.getLocationOnScreen(startLocation);
                Log.d(TAG, "onItemClick: " + startLocation[1]);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                View head = view.getChildAt(0);
                if (head == null) {
                    return;
                }
                View firstItem = view.getChildAt(1);
                if (firstItem == null) {
                    return;
                }
                int top = head.getTop();
                //因为head view不能完全显示垂直距离的变化，当其不可见后，需要加上Item计算垂直距离变化  这里假定Item的高度是固定的 否则更加麻烦
                int currentY = firstVisibleItem * firstItem.getHeight() - top;
                Log.d(TAG, top +" onScroll: distance" + currentY);
                //以下是head view 和 Item高度的基准切换，也即是firstVisibleItem=0时使用head view的基准;firstVisibleItem>0 使用Item为基准
                if (firstVisibleItem == 0&transition) {
                    Log.d(TAG, "onScroll: head");
                    transition =false;
                    oldY=currentY;
                }else if (firstVisibleItem > 0&!transition) {
                    Log.d(TAG, "onScroll: item");
                    transition =true;
                    oldY=currentY;
                }

                if (currentY > oldY) {
//                    向上
                    Log.d(TAG, "onScroll: up");
                    up_distance += (currentY - oldY);
                    if (up_distance >= disappear_distance&& needFadeAnimation) {
//                        titlebar.setVisibility(View.GONE);
                        needFadeAnimation = false;
                        ViewCompat.animate(titleBar).alpha(0f).setDuration(200).start();

                    }
                } else if (oldY - currentY > 1) {
                    //向下
                    Log.d(TAG, "onScroll: down");
                    up_distance = 0;
//                    titlebar.setVisibility(View.VISIBLE);
                    if (!needFadeAnimation) {
                        needFadeAnimation = true;
                        ViewCompat.animate(titleBar).alpha(1f).setDuration(200).start();
                    }
                }
                oldY = currentY;

            }
        });

    }
}