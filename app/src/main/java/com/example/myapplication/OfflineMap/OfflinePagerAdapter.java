package com.example.myapplication.OfflineMap;

import android.os.Parcelable;
import android.view.View;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


/**
 * ViewPager数据
 */
public class OfflinePagerAdapter extends PagerAdapter {

    private View mOfflineMapAllList;
    private View mOfflineDowloadedList;

    private ViewPager mContentViewPager;

    public OfflinePagerAdapter(ViewPager contentViewPager,
                               View offlineMapAllList, View offlineDowloadedList) {
        mContentViewPager = contentViewPager;
        this.mOfflineMapAllList = offlineMapAllList;
        this.mOfflineDowloadedList = offlineDowloadedList;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        if (arg1 == 0) {
            mContentViewPager.removeView(mOfflineMapAllList);
        } else {
            mContentViewPager.removeView(mOfflineDowloadedList);
        }

    }

    @Override
    public void finishUpdate(View arg0) {
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {

        if (arg1 == 0) {
            mContentViewPager.addView(mOfflineMapAllList);
            return mOfflineMapAllList;
        } else {
            mContentViewPager.addView(mOfflineDowloadedList);
            return mOfflineDowloadedList;
        }

    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }

}
