package com.example.myapplication.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.myapplication.R;
import com.example.myapplication.entity.LianXiRenData;


import java.util.List;

public class LianXiRenAdapter extends RecyclerView.Adapter<LianXiRenAdapter.LianXiRenHolder> {

    private List<LianXiRenData> courseData;
    private Context context;

    public LianXiRenAdapter(List<LianXiRenData> courseData, Context context) {
        this.courseData = courseData;
        this.context = context;
    }

    @NonNull
    @Override
    public LianXiRenHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_os, parent, false);
        return new LianXiRenHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull LianXiRenHolder holder, int position) {

        LianXiRenData course = courseData.get(position);
        holder.tv_courseName.setText("联系人名称 : "+ (TextUtils.isEmpty(course.getName())?"":course.getName()));
        holder.tv_couseNum.setText("手机号 : "+ course.getPhoneNumber());
        holder.tv_week.setText("联系人性别 : "+ (course.getSex()==1?"男":"女"));

        holder.btDelete.setOnClickListener(v -> {
            if (onDeleteClickListener!=null)
                onDeleteClickListener.onDeleteClick(position,course);
        });
        holder.item.setOnClickListener(v -> {
            if (onItemAdapterClickListener!=null)
                onItemAdapterClickListener.onClick(position,course);
        });
        holder.imgPhone.setOnClickListener(v -> {
            if (onPhoneClickListener!=null)
                onPhoneClickListener.onPhone(position,course);
        });
        Glide.with(context)
                .load(course.getImgPath())
                .priority(Priority.LOW)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return courseData.size();
    }

    public class LianXiRenHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView tv_courseName,tv_couseNum,tv_week;
        ImageView   btDelete,imgPhone;
        ConstraintLayout item;

        public LianXiRenHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.itemView);
            img = itemView.findViewById(R.id.img);
            btDelete = itemView.findViewById(R.id.imgCancel);
            imgPhone = itemView.findViewById(R.id.imgPhone);
            tv_courseName = itemView.findViewById(R.id.tv_courseName);
            tv_couseNum = itemView.findViewById(R.id.tv_couseNum);
            tv_week = itemView.findViewById(R.id.tv_week);
        }
    }


    private OnItemAdapterClickListener onItemAdapterClickListener;


    public void setOnItemAdapterClickListener(OnItemAdapterClickListener onItemAdapterClickListener) {
        this.onItemAdapterClickListener = onItemAdapterClickListener;
    }

    public interface OnItemAdapterClickListener{
        void onClick(int position,LianXiRenData courseData);
    }


    private OnDeleteClickListener onDeleteClickListener;

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public interface OnDeleteClickListener{
        void onDeleteClick(int position,LianXiRenData courseData);
    }


    private OnPhoneClickListener onPhoneClickListener;

    public void setOnPhoneClickListener(OnPhoneClickListener onPhoneClickListener) {
        this.onPhoneClickListener = onPhoneClickListener;
    }

    public interface OnPhoneClickListener{
        void onPhone(int position,LianXiRenData courseData);
    }
}
