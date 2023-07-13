package com.example.myapplication.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;


/**
 * Created by CUIMENGQI on 2016/4/11.
 */
public class ToastUtils {

    private ToastUtils() {
        throw new AssertionError();
    }

    public static void show(Context context, int resId) {
        showToast(context, context.getResources().getString(resId));
    }

    public static void show(Context context, String text) {
        showToast(context, text);
    }

    private static Toast mToast;

    public static void showToast(Context context, String text) {
        if (context == null){
            return;
        }
        if (TextUtils.isEmpty(text))
            return;
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void showToast(Context context, String text,int time) {
        if (context == null){
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(context, text, time);
        } else {
            mToast.setText(text);
            mToast.setDuration(time);
        }
        mToast.show();
    }

    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

    public static void showCustomToast(Context context , String text) {
        if (context == null){
            return;
        }
        if (TextUtils.isEmpty(text))
            return;
        LayoutInflater inflater = LayoutInflater.from(context);
        View toastView = inflater.inflate(R.layout.layout_toast, null);   // 用布局解析器来解析一个布局去适配Toast的setView方法
        TextView tvText = toastView.findViewById(R.id.tv_text);
        tvText.setText(text);
        Toast toast = new Toast(context);
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showCustomToast(Context context, String text, int time) {
        if (context == null) {
            return;
        }
        if (TextUtils.isEmpty(text))
            return;
        LayoutInflater inflater = LayoutInflater.from(context);
        View toastView = inflater.inflate(R.layout.layout_toast, null);   // 用布局解析器来解析一个布局去适配Toast的setView方法
        TextView tvText = toastView.findViewById(R.id.tv_text);
        tvText.setText(text);
        Toast toast = new Toast(context);
        toast.setView(toastView);
        toast.setDuration(time);
        toast.show();
    }

    public static void showCustomCenterToast(Context context , String text) {
        if (context == null){
            return;
        }
        if (TextUtils.isEmpty(text))
            return;
        LayoutInflater inflater = LayoutInflater.from(context);
        View toastView = inflater.inflate(R.layout.layout_toast, null);   // 用布局解析器来解析一个布局去适配Toast的setView方法
        TextView tvText = toastView.findViewById(R.id.tv_text);
        tvText.setText(text);
        Toast toast = new Toast(context);
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showCustomCenterToast(Context context , String text,int time) {
        if (context == null){
            return;
        }
        if (TextUtils.isEmpty(text))
            return;
        LayoutInflater inflater = LayoutInflater.from(context);
        View toastView = inflater.inflate(R.layout.layout_toast, null);   // 用布局解析器来解析一个布局去适配Toast的setView方法
        TextView tvText = toastView.findViewById(R.id.tv_text);
        tvText.setText(text);
        Toast toast = new Toast(context);
        toast.setView(toastView);
        toast.setDuration(time);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showCustomCenterToast(Context context , String text,int xOffSet,int yOffset) {
        if (context == null){
            return;
        }
        if (TextUtils.isEmpty(text))
            return;
        LayoutInflater inflater = LayoutInflater.from(context);
        View toastView = inflater.inflate(R.layout.layout_toast, null);   // 用布局解析器来解析一个布局去适配Toast的setView方法
        TextView tvText = toastView.findViewById(R.id.tv_text);
        tvText.setText(text);
        Toast toast = new Toast(context);
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, xOffSet, yOffset);
        toast.show();
    }
}
