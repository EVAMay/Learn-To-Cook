package com.feicui.learntocook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenovo on 2016/9/12.
 */
public class CookDb extends SQLiteOpenHelper{

    public static final String DB_NAME="cookDB";//数据库名
    public static final String TABLE_NAME="cookData";//最近浏览摘要部分表名
    public static final String STEPS_TABLE="steps";//最近浏览步骤部分表名
    public static final String LIKE_TABLE="likeCook";//收藏摘要表名
    public static final String LIKE_STEPS="likeSteps";//收藏步骤表名
    public static final String ID="cookId";//第一列列名
    public static final String TITLE="cookTitle";//菜名
    public static final String TAGS="tags";//分类
    public static final String IMTRO="imtro";//简介
    public static final String INGREDIENTS="ingredients";//主料
    public static final String BURDEN="burden";//配料
    public static final String ALBUMS="albums";//预览图
    public static final String IMG="img";//步骤图片
    public static final String STEP="step";//步骤
    public CookDb(Context context) {
        super(context, DB_NAME, null, 1);
    }

    /**
     * 创建表
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        //最近浏览摘要部分的表
        sqLiteDatabase.execSQL("create table "+TABLE_NAME+" ( " +
                ID+" varchar(255), "+
                TITLE+" varchar(255), "+
                TAGS+" varchar(255), "+
                IMTRO+" varchar(255), "+
                INGREDIENTS+" varchar(255), "+
                BURDEN+" varchar(255), "+
                ALBUMS+" varchar(255)"+" );");

        //最近浏览操作步骤的表
        sqLiteDatabase.execSQL("create table "+STEPS_TABLE+" ( "+
                ID+" varchar(255), "+
                IMG+" varchar(255), "+
                STEP+" varchar(255)"+" );");

        //收藏的摘要部分的表
        sqLiteDatabase.execSQL("create table "+LIKE_TABLE+" ( "+
                ID+" varchar(255), "+
                TITLE+" varchar(255), "+
                TAGS+" varchar(255), "+
                IMTRO+" varchar(255), "+
                INGREDIENTS+" varchar(255), "+
                BURDEN+" varchar(255), "+
                ALBUMS+" varchar(255)"+" );");

        //收藏的操作步骤的表
        sqLiteDatabase.execSQL("create table "+LIKE_STEPS+" ( "+
                ID+" varchar(255), "+
                IMG+" varchar(255), "+
                STEP+" varchar(255)"+" );");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        if (newVersion >= oldVersion) {

            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + STEPS_TABLE);

            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LIKE_TABLE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LIKE_STEPS);
            onCreate(sqLiteDatabase);

//        }
    }

}
