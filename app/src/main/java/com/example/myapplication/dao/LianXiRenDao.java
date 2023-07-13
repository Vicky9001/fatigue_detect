package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.myapplication.entity.LianXiRenData;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻数据库API
 */
public class LianXiRenDao {

    public static final String NEWS = "NEWS";

    private MySqliteOpenHelper mOpenHelper;
    private Context mContext;

    private static LianXiRenDao mNewsDao;

    public LianXiRenDao(MySqliteOpenHelper mOpenHelper, Context context) {
        this.mOpenHelper = mOpenHelper;
        this.mContext = context;
    }

    public static LianXiRenDao getInstance(Context context) {
        if (mNewsDao == null) {
            mNewsDao = new LianXiRenDao(MySqliteOpenHelper.getInstance(context), context);
        }
        return mNewsDao;
    }

    public void add(LianXiRenData data) {
        SQLiteDatabase database = mOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", data.getName());
        values.put("phoneNumber", data.getPhoneNumber());
        values.put("sex", data.getSex());
        values.put("imgPath", data.getImgPath());
        database.insert(NEWS, null, values);
    }

    public void delete(int id) {
        SQLiteDatabase database = mOpenHelper.getWritableDatabase();
        database.delete(NEWS, "id = ?", new String[]{id + ""});
    }

    public void update(LianXiRenData data) {
        SQLiteDatabase database = mOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", data.getName());
        values.put("phoneNumber", data.getPhoneNumber());
        values.put("sex", data.getSex());
        values.put("imgPath", data.getImgPath());
        database.update(NEWS, values, "id = ?", new String[]{data.getId() + ""});
    }

    public List<LianXiRenData> queryAll() {

        SQLiteDatabase database = mOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(NEWS, null, null, null, null, null, null);
        List<LianXiRenData> birthdays = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String phoneNumber = cursor.getString(2);
            int sex = cursor.getInt(3);
            String imgPath = cursor.getString(4);
            LianXiRenData data = new LianXiRenData();
            data.setId(id);
            data.setName(name);
            data.setPhoneNumber(phoneNumber);
            data.setSex(sex);
            data.setImgPath(imgPath);
            birthdays.add(data);
        }
        return birthdays;
    }

}
