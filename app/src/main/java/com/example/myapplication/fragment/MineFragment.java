package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.activity.CreateLianXiRenActivity;
import com.example.myapplication.adapter.BaseViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends Fragment {

    private View viewGroup;
    private TabLayout tabLayout;
    Toolbar toolbar;
    private ViewPager vp;
    private ImageView imgAdd;
    List<Fragment> fragments;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }
//绑定页面为fragment_mine
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewGroup = inflater.inflate(R.layout.fragment_mine, container, false);
        toolbar = viewGroup.findViewById(R.id.toolbar);
        toolbar.setPadding(0, getHeight(), 0, 0);
        tabLayout = viewGroup.findViewById(R.id.tab);
        vp = viewGroup.findViewById(R.id.vp);
        imgAdd = viewGroup.findViewById(R.id.imgAdd);
        imgAdd.setVisibility(View.GONE);
        imgAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreateLianXiRenActivity.class);
            startActivity(intent);
        });
        initData();
        return viewGroup;
    }

    private void initData() {
        fragments = new ArrayList<>();
        String[] str = {"历史记录", "联系人"};
        fragments.add(HomeFragment.newInstance());
        fragments.add(LianXiRenFragment.newInstance());

        vp.setAdapter(new BaseViewPagerAdapter(getChildFragmentManager(), fragments, Arrays.asList(str), getContext()));
        vp.setOffscreenPageLimit(str.length);
        vp.setCurrentItem(0);
        tabLayout.setupWithViewPager(vp);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    imgAdd.setVisibility(View.GONE);
                }else{
                    imgAdd.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //获取状态栏高度
    protected int getHeight(){
        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }
}