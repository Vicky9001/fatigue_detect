package com.example.myapplication.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 *  MySqliteOpenHelper 工具类 单例模式(1.构造函数私有化，2.对外提供函数。)
 */
// 该类就可以理解为一个数据库了。
public class MySqliteOpenHelper extends SQLiteOpenHelper {

    // 单例模式
    private static MySqliteOpenHelper mInstance;

    // 提供对外提供的函数。
    public static synchronized MySqliteOpenHelper getInstance(Context context){
        if (mInstance == null){
            mInstance = new MySqliteOpenHelper(context,"AndroidWork.db",null,1);
        }
        return mInstance;
    }

    /**
     * 通过构造函数，将数据库名称 ， 数据库版本号等定义出来。因此，必须要声明一个构造函数。
     * @param context context就是环境，在此环境下进行创建数据库。
     * @param name 数据库名称
     * @param factory 工厂
     * @param version 版本 数据库升级后需要不断加1操作！
     */
    public MySqliteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 数据库初始化用的:
     *      创建表 表数据初始化 数据库第一次创建的时候调用。第二次发现有该表了就不会重复创建了！
     *      此函数只会执行一次。 因此，添加了新的数据库或者修改了数据库，必须要对应项目目录将databases目录下的db等文件删除。
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 创建表：person表。

        // 主键： _id(标准) 或者 id(不标准) 必须唯一，也可以添加自动增长 。
        sqLiteDatabase.execSQL(NEWS_SQL);
        sqLiteDatabase.execSQL(HISTORY_SQL);
    }

    // 数据库升级用的
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL(NEWS_SQL);
                db.execSQL(HISTORY_SQL);
                break;
        }
    }

    private static final String NEWS_SQL;
    private static final String HISTORY_SQL;

    static {
        NEWS_SQL = new StringBuffer()
                .append("create table ")
                .append(LianXiRenDao.NEWS)
                .append("(")
                .append("id Integer primary key autoincrement,")
                .append("name varchar(255),")
                .append("phoneNumber text,")
                .append("sex Integer,")
                .append("imgPath text")
                .append(");")
                .toString();
        HISTORY_SQL = new StringBuffer()
                .append("create table ")
                .append(HistoryDao.HISTORY)
                .append("(")
                .append("id Integer primary key autoincrement,")
                .append("num Integer,")
                .append("tvTime text,")
                .append("imgPath1 text,")
                .append("imgPath2 text,")
                .append("imgPath3 text")
                .append(");")
                .toString();
    }

}
