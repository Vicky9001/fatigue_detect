package com.example.myapplication.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activity.CreateLianXiRenActivity;
import com.example.myapplication.adapter.LianXiRenAdapter;
import com.example.myapplication.dao.LianXiRenDao;
import com.example.myapplication.entity.LianXiRenData;
import com.example.myapplication.utils.ToastUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LianXiRenFragment extends Fragment {

    private View viewGroup;
    RecyclerView lv;
    TextView tvNoData;
    private LianXiRenDao mNewsDao;
    private List<LianXiRenData> mList;
    LianXiRenAdapter mAdapter ;

    public LianXiRenFragment() {
        // Required empty public constructor
    }

    public static LianXiRenFragment newInstance() {
        LianXiRenFragment fragment = new LianXiRenFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = inflater.inflate(R.layout.fragment_lian_xi_ren, container, false);
        mNewsDao = LianXiRenDao.getInstance(getActivity());
        initView();
        initData();
        return viewGroup;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            refreshData(mNewsDao.queryAll());
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData(mNewsDao.queryAll());
    }

    private void refreshData(List<LianXiRenData> musicData){
        mList.clear();
        mList.addAll(musicData);
        mAdapter.notifyDataSetChanged();
        if (musicData.isEmpty()){
            lv.setVisibility(View.GONE);
            tvNoData.setVisibility(View.VISIBLE);
        }else{
            lv.setVisibility(View.VISIBLE);
            tvNoData.setVisibility(View.GONE);
        }
    }

    private void initData() {
        mList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        lv.setLayoutManager(linearLayoutManager);
        mAdapter = new LianXiRenAdapter(mList,getActivity());
        lv.setAdapter(mAdapter);

        mAdapter.setOnDeleteClickListener((position, courseData) -> {
            showDeleteDialog(courseData);
        });
        mAdapter.setOnItemAdapterClickListener((position, musicData) -> {
            Intent intent = new Intent(getActivity(), CreateLianXiRenActivity.class);
            intent.putExtra("item",musicData);
            startActivity(intent);
        });

        mAdapter.setOnPhoneClickListener((position, musicData) -> {
            showPhoneDialog(musicData);
        });
    }

    private void showPhoneDialog(LianXiRenData NewsData) {
        if (AndPermission.hasPermissions(getActivity(), Permission.CALL_PHONE)){
            callPhone(NewsData.getPhoneNumber());
        }else{
            AndPermission.with(this)
                    .runtime()
                    .permission(Permission.CALL_PHONE)
                    .onGranted(permissions -> {
                        // Storage permission are allowed.
                        callPhone(NewsData.getPhoneNumber());
                    })
                    .onDenied(permissions -> {
                        // Storage permission are not allowed.
                        Toast.makeText(getActivity(), "获取权限失败，请重新获取", Toast.LENGTH_SHORT).show();

                    })
                    .start();
        }
    }

    private void showDeleteDialog(LianXiRenData NewsData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("是否删除");
        builder.setMessage("是否删除这条联系人");
        builder.setNegativeButton("取消", (dialog1, which) -> dialog1.dismiss());
        builder.setPositiveButton("确定", (dialog12, which) -> {
            mNewsDao.delete(NewsData.getId());
            initData();
            ToastUtils.showCustomCenterToast(getActivity(),"删除数据成功");
        });
        AlertDialog dialog1 = builder.create();
        dialog1.show();
    }

    private void initView() {
        lv = viewGroup.findViewById(R.id.lv);
        tvNoData = viewGroup.findViewById(R.id.tv_noData);
    }

    public void callPhone(String phoneNum){
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
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