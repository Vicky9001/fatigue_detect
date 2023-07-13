package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.entity.HistoryData;
import com.example.myapplication.entity.LianXiRenData;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻数据库API
 */
public class HistoryDao {

    public static final String HISTORY = "HISTORY";

    private MySqliteOpenHelper mOpenHelper;
    private Context mContext;

    private static HistoryDao mNewsDao;

    public HistoryDao(MySqliteOpenHelper mOpenHelper, Context context) {
        this.mOpenHelper = mOpenHelper;
        this.mContext = context;
    }

    public static HistoryDao getInstance(Context context) {
        if (mNewsDao == null) {
            mNewsDao = new HistoryDao(MySqliteOpenHelper.getInstance(context), context);
        }
        return mNewsDao;
    }

    public void add(HistoryData data) {
        SQLiteDatabase database = mOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("num", data.getNum());
        values.put("tvTime", data.getTvTime());
        values.put("imgPath1", data.getImgPath1());
        values.put("imgPath2", data.getImgPath2());
        values.put("imgPath3", data.getImgPath3());
        database.insert(HISTORY, null, values);
    }

    public void delete(int id) {
        SQLiteDatabase database = mOpenHelper.getWritableDatabase();
        database.delete(HISTORY, "id = ?", new String[]{id + ""});
    }

    public void update(HistoryData data) {
        SQLiteDatabase database = mOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("num", data.getNum());
        values.put("tvTime", data.getTvTime());
        values.put("imgPath1", data.getImgPath1());
        values.put("imgPath2", data.getImgPath2());
        values.put("imgPath3", data.getImgPath3());
        database.update(HISTORY, values, "id = ?", new String[]{data.getId() + ""});
    }

    public List<HistoryData> queryAll() {

        SQLiteDatabase database = mOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(HISTORY, null, null, null, null, null, null);
        List<HistoryData> birthdays = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int num = cursor.getInt(1);
            String tvTime = cursor.getString(2);
            String imgPath1 = cursor.getString(3);
            String imgPath2 = cursor.getString(4);
            String imgPath3 = cursor.getString(5);
            HistoryData data = new HistoryData(id,num,tvTime,imgPath1,imgPath2,imgPath3);
            birthdays.add(data);
        }
        return birthdays;
    }

}
