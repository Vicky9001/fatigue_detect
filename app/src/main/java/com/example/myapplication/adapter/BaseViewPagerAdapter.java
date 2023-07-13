package com.example.myapplication.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;


public class BaseViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> list;
    private List<String> titles;
    private Context context;
    public Fragment currentFragment = null;


    public BaseViewPagerAdapter(FragmentManager fm, List<Fragment> list, List<String> titles, Context context) {
        super(fm);
        this.list = list;
        this.titles = titles;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        currentFragment = (Fragment) object;
    }

    @Override
    public int getCount() {
        if (list != null)
            return list.size();
        else
            return 0;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        if (titles != null)
//            return titles.get(position);
//        else
//            return "";
//    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && titles.size() > 0) {
            SpannableString span = new SpannableString(titles.get(position));
            TypefaceSpan mTypeFaceSpan = new TypefaceSpan("default");
            span.setSpan(mTypeFaceSpan, 0, titles.get(position).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return span;
        } else {
            return "";
        }
    }

}
