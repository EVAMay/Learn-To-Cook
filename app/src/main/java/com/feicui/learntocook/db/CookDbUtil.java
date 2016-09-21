package com.feicui.learntocook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.feicui.learntocook.entity.CookInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/9/12.
 */
public class CookDbUtil {
    private CookDb cookDb;
    private static  CookDbUtil sIntence;
    private SQLiteDatabase database;
    private CookDbUtil(Context context){
        this.cookDb=new CookDb(context);
        this.database=cookDb.getWritableDatabase();
    }
    public static CookDbUtil getsIntence(Context context){
        if (sIntence==null){
            sIntence=new CookDbUtil(context);
        }
        return sIntence;
    }


    /**
     * 最近浏览摘要信息添加
     *在此方法中实现摘要列表和步骤列表同步添加数据
     */

    public void addCookData(CookInfo.ResultBean.DataBean cookData){
        ContentValues values=new ContentValues();
        values.put(CookDb.ID,cookData.getId());//加入数据
        values.put(CookDb.TITLE,cookData.getTitle());
        values.put(CookDb.TAGS,cookData.getTags());
        values.put(CookDb.IMTRO,cookData.getImtro());
        values.put(CookDb.INGREDIENTS,cookData.getIngredients());
        values.put(CookDb.BURDEN,cookData.getBurden());
        values.put(CookDb.ALBUMS,cookData.getAlbums().get(0));

        database.insert(CookDb.TABLE_NAME,null,values);//添加到数据库，核心语句

    }

    /**最近浏览步骤添加
     * 添加步骤的方法
     * @param cookSteps
     */
    public void addCookSteps(CookInfo.ResultBean.DataBean.StepsBean cookSteps,CookInfo.ResultBean.DataBean cookData){
        ContentValues values=new ContentValues();
        values.put(CookDb.ID,cookData.getId());
        values.put(CookDb.IMG,cookSteps.getImg());
        values.put(CookDb.STEP,cookSteps.getStep());

        database.insert(CookDb.STEPS_TABLE,null,values);
    }

    /**
     * 收藏列表
     * @param cookData
     */
    public void addLikeCookData(CookInfo.ResultBean.DataBean cookData){
        ContentValues values=new ContentValues();
        values.put(CookDb.ID,cookData.getId());//加入数据
        values.put(CookDb.TITLE,cookData.getTitle());
        values.put(CookDb.TAGS,cookData.getTags());
        values.put(CookDb.IMTRO,cookData.getImtro());
        values.put(CookDb.INGREDIENTS,cookData.getIngredients());
        values.put(CookDb.BURDEN,cookData.getBurden());
        values.put(CookDb.ALBUMS,cookData.getAlbums().get(0));

        database.insert(CookDb.LIKE_TABLE,null,values);//添加到收藏的列表，核心语句
    }

    /**
     * 收藏列表步骤的添加
     * @param cookSteps
     * @param cookData
     */
    public void addLikeCookSteps(CookInfo.ResultBean.DataBean.StepsBean cookSteps,CookInfo.ResultBean.DataBean cookData){
        ContentValues values=new ContentValues();
        values.put(CookDb.ID,cookData.getId());
        values.put(CookDb.IMG,cookSteps.getImg());
        values.put(CookDb.STEP,cookSteps.getStep());

        database.insert(CookDb.LIKE_STEPS,null,values);
    }

    /**
     * 查找CookData摘要的方法
     * @return
     */
    public List<CookInfo.ResultBean.DataBean> findCookData(){
        List<CookInfo.ResultBean.DataBean> cookList=new ArrayList<>();
        List<String> albumsList;
        List<CookInfo.ResultBean.DataBean.StepsBean> stepsList=new ArrayList<>();

        String condition = " select distinct * from " +CookDb.TABLE_NAME;
        Cursor cursor=database.rawQuery(condition,null);

            while (cursor.moveToNext()){
                //要添加进参数，实体类中必须要有构造方法
                String id=cursor.getString(cursor.getColumnIndex(CookDb.ID));
                String title=cursor.getString(cursor.getColumnIndex(CookDb.TITLE));
                String tags=cursor.getString(cursor.getColumnIndex(CookDb.TAGS));
                String imtro=cursor.getString(cursor.getColumnIndex(CookDb.IMTRO));
                String ingredients=cursor.getString(cursor.getColumnIndex(CookDb.INGREDIENTS));
                String burden=cursor.getString(cursor.getColumnIndex(CookDb.BURDEN));
                String albums=cursor.getString(cursor.getColumnIndex(CookDb.ALBUMS));

                albumsList=new ArrayList<>();//需要在循环中new出来，否则只会将数据存到一个集合中，这里需要添加一次就创建一个集合来接收
                albumsList.add(albums);//将获取到的预览图URL添加到集合中
                stepsList=findStepsList(id);
                if (stepsList!=null){
                    cookList.add(0,new CookInfo.ResultBean.DataBean(id,title,tags,imtro,ingredients,
                            burden,albumsList,stepsList));
                }
            }


        cursor.close();//查询完关闭游标，放置内存泄漏
        return cookList;
    }

    /**
     * 收藏列表摘要的查找方法
     * @return
     */
    public List<CookInfo.ResultBean.DataBean> findLikeCookData(){
        List<CookInfo.ResultBean.DataBean> cookList=new ArrayList<>();
        List<String> albumsList;
        List<CookInfo.ResultBean.DataBean.StepsBean> stepsList=new ArrayList<>();
        String condition=" select * from "+CookDb.LIKE_TABLE;
        Cursor cursor=database.rawQuery(condition,null);

            while (cursor.moveToNext()){
                //要添加进参数，实体类中必须要有构造方法
                String id=cursor.getString(cursor.getColumnIndex(CookDb.ID));
                String title=cursor.getString(cursor.getColumnIndex(CookDb.TITLE));
                String tags=cursor.getString(cursor.getColumnIndex(CookDb.TAGS));
                String imtro=cursor.getString(cursor.getColumnIndex(CookDb.IMTRO));
                String ingredients=cursor.getString(cursor.getColumnIndex(CookDb.INGREDIENTS));
                String burden=cursor.getString(cursor.getColumnIndex(CookDb.BURDEN));
                String albums=cursor.getString(cursor.getColumnIndex(CookDb.ALBUMS));

                albumsList=new ArrayList<>();//需要在循环中new出来，否则只会将数据存到一个集合中，这里需要添加一次就创建一个集合来接收
                albumsList.add(albums);//将获取到的预览图URL添加到集合中
                stepsList=findStepsList(id);
                if (stepsList!=null){
                    cookList.add(0,new CookInfo.ResultBean.DataBean(id,title,tags,imtro,ingredients,
                            burden,albumsList,stepsList));
                }
            }


        cursor.close();//查询完关闭游标，放置内存泄漏
        return cookList;
    }

    /**
     * 最近浏览查找步骤列表的方法
     * @return
     */
    public List<CookInfo.ResultBean.DataBean.StepsBean> findStepsList(String stepId ){
        List<CookInfo.ResultBean.DataBean.StepsBean> stepsList = new ArrayList<>();
        String condition=" select * from "+CookDb.STEPS_TABLE+" where cookId = "+stepId;//SQL查询条件
        Cursor cursor=database.rawQuery(condition,null);//参数列表为查询的条件
        while (cursor.moveToNext()){
           String id=cursor.getString(cursor.getColumnIndex(CookDb.ID));
            String step=cursor.getString(cursor.getColumnIndex(CookDb.STEP));
            String img=cursor.getString(cursor.getColumnIndex(CookDb.IMG));
            stepsList.add(0,new CookInfo.ResultBean.DataBean.StepsBean(img,step));
        }
        return stepsList;
    }

    /**
     * 收藏列表的步骤查找
     */
    public List<CookInfo.ResultBean.DataBean.StepsBean> findLikeStepsList(String stepId ){
        List<CookInfo.ResultBean.DataBean.StepsBean> stepsList = new ArrayList<>();
        String condition=" select * from "+CookDb.LIKE_STEPS+" where cookId = "+stepId;//SQL查询条件
        Cursor cursor=database.rawQuery(condition,null);//参数列表为查询的条件
        while (cursor.moveToNext()){
            String id=cursor.getString(cursor.getColumnIndex(CookDb.ID));
            String step=cursor.getString(cursor.getColumnIndex(CookDb.STEP));
            String img=cursor.getString(cursor.getColumnIndex(CookDb.IMG));
            stepsList.add(0,new CookInfo.ResultBean.DataBean.StepsBean(img,step));
        }
        return stepsList;
    }

    /**
     * 使用SQL删除收藏信息
     * @param cookData
     */
    public void deleteLikeCook(CookInfo.ResultBean.DataBean cookData){
        String sql=" delete from "+CookDb.LIKE_TABLE+" where "+CookDb.ID+
                " = '"+cookData.getId()+"'";//sql语句定义条件
        database.execSQL(sql);//执行sql语句
    }
}
