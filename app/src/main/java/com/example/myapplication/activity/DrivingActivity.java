package com.example.myapplication.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.myapplication.R;
import com.example.myapplication.fragment.MapFragment;
import com.example.myapplication.fragment.MineFragment;
import com.example.myapplication.fragment.zqy_blankFragment;
import com.example.myapplication.utils.FragmentUtils;
import com.example.myapplication.utils.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;

public class DrivingActivity extends AppCompatActivity {

    RadioGroup rg;
    private zqy_blankFragment blankFragment;
    private MineFragment lianXiRenFragment;
    private MapFragment mapFragment;
    private FragmentManager manager;
    //返回键监听
    private boolean isQuit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving);
        if (Build.VERSION.SDK_INT >= 23) {
            ImmersionBar.with(this)
                    .statusBarDarkFont(true)//状态栏字体是深色，不写默认为亮色
                    .init();
        } else {
            ImmersionBar.with(this)
                    .statusBarDarkFont(true)//状态栏字体是深色，不写默认为亮色
                    .statusBarAlpha(0.3f)  //状态栏透明度，不写默认0.0f
                    .init();
        }
        manager = getSupportFragmentManager();
        initView();
    }

    protected void initView() {
        rg = findViewById(R.id.rg);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            FragmentUtils.addFragment(manager, mapFragment, R.id.homeFrame, false);
        } else {
            FragmentUtils.showFragment(mapFragment);
        }
        RadioButton childAt = (RadioButton) rg.getChildAt(1);
        childAt.setChecked(true);
        rg.setOnCheckedChangeListener((group, checkedId) -> {
            initHideAllFragment();
            if (checkedId == R.id.rb_Home) {
                Intent intent = new Intent(DrivingActivity.this, CameraActivity.class);
                startActivity(intent);
                finish();
            } else if (checkedId == R.id.rb_daohang) {
                if (mapFragment == null) {
                    mapFragment = MapFragment.newInstance();
                    FragmentUtils.addFragment(manager, mapFragment, R.id.homeFrame, false);
                } else {
                    FragmentUtils.showFragment(mapFragment);
                }
            }else if (checkedId == R.id.rb_lianxiren) {
                if (lianXiRenFragment == null) {
                    lianXiRenFragment = MineFragment.newInstance();
                    FragmentUtils.addFragment(manager, lianXiRenFragment, R.id.homeFrame, false);
                } else {
                    FragmentUtils.showFragment(lianXiRenFragment);
                }
            }
        });
    }
//    protected void initView() {
//        rg = findViewById(R.id.rg);
//        RadioButton childAt = (RadioButton) rg.getChildAt(0);
//        childAt.setChecked(true);
//        if (blankFragment == null) {
//            blankFragment = zqy_blankFragment.newInstance("","");
//            FragmentUtils.addFragment(manager, blankFragment, R.id.homeFrame, false);
//        } else {
//            FragmentUtils.showFragment(blankFragment);
//        }
//        rg.setOnCheckedChangeListener((group, checkedId) -> {
//            initHideAllFragment();
//            if (checkedId == R.id.rb_Home) {
////                if (blankFragment == null) {
////                    blankFragment = zqy_blankFragment.newInstance("","");
////                    FragmentUtils.addFragment(manager, blankFragment, R.id.homeFrame, false);
////                } else {
////                    FragmentUtils.showFragment(blankFragment);
////                }
//                Intent intent = new Intent(DrivingActivity.this, CameraActivity.class);
//                startActivity(intent);
//            } else if (checkedId == R.id.rb_daohang) {
//                if (mapFragment == null) {
//                    mapFragment = MapFragment.newInstance();
//                    FragmentUtils.addFragment(manager, mapFragment, R.id.homeFrame, false);
////                    FragmentUtils.addFragment(manager, mapFragment, R.id.homeFrame, false);
//                } else {
//                    FragmentUtils.showFragment(mapFragment);
//                }
//            }else if (checkedId == R.id.rb_lianxiren) {
//                if (lianXiRenFragment == null) {
//                    lianXiRenFragment = MineFragment.newInstance();
//                    FragmentUtils.addFragment(manager, lianXiRenFragment, R.id.homeFrame, false);
//                } else {
//                    FragmentUtils.showFragment(lianXiRenFragment);
//                }
//            }
//        });
//    }

    private void initHideAllFragment() {
        if (blankFragment != null)
            FragmentUtils.hideFragment(blankFragment);
        if (mapFragment != null)
            FragmentUtils.hideFragment(mapFragment);
        if (lianXiRenFragment != null)
            FragmentUtils.hideFragment(lianXiRenFragment);

    }

    //返回Home键监听
    @Override
    public void onBackPressed() {
        if (!isQuit) {
            ToastUtils.showCustomToast(this, "再点击一次退出");
            isQuit = true;
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    isQuit = false;
                }
            }).start();
        } else {
            System.exit(0);
            finish();
        }
    }

    //获取状态栏高度
    protected int getHeight() {
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