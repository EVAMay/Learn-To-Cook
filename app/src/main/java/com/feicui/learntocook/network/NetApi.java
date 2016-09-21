package com.feicui.learntocook.network;

import com.feicui.learntocook.entity.CookClassify;
import com.feicui.learntocook.entity.CookInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/9/8.
 */
public interface NetApi {
    /**
     * 请求示例：http://apis.juhe.cn/cook/query?key=&menu=%E8%A5%BF%E7%BA%A2%E6%9F%BF&rn=10&pn=3
     */
    //获取搜索的菜谱详情
    @GET("query")
    Call<CookInfo> getCookData(@Query("key") String apiKey,
                               @Query("menu") String menu,
                               @Query("rn") String rn,
                               @Query("pn") String pn);


    /**
     * 请求示例: http://apis.juhe.cn/cook/index?key=您申请的KEY&cid=1
     */
    //分类菜谱详情
    @GET("index")
    Call<CookInfo> getClassify(@Query("key") String apiKey,
                               @Query("cid") int cid,
                               @Query("rn") String rn,
                               @Query("pn") String pn);

    /**
     * 请求地址：http://apis.juhe.cn/cook/category
     * 请求示例：http://apis.juhe.cn/cook/category?key=您申请的KEY
     */
    //菜谱分类标签查找
    @GET("category")
    Call<CookClassify> getCookClassify(@Query("key") String apiKey);

    /**
     * 请求示例：http://apis.juhe.cn/cook/queryid?key=您申请的KEY&id=1001
     */
    //通过菜谱id查找
    @GET("queryid")
    Call<CookInfo> getCookId(@Query("key") String aipKey,
                                 @Query("id") int id);
}
