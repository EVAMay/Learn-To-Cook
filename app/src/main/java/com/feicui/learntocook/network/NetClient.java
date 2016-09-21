package com.feicui.learntocook.network;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lenovo on 2016/9/8.
 */
public class NetClient {
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private NetApi netApi;
    private final String URL="http://apis.juhe.cn/cook/";
    private static NetClient instance;
    public static NetClient getInstance(Context context){
        if (instance==null){
            instance=new NetClient();
        }
        return instance;
    }
    private NetClient(){
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)//添加日志拦截器
                .build();
        retrofit=new Retrofit.Builder()
                .baseUrl(URL)//基本连接
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())//转换工厂 数据的解析
                .build();
        netApi=retrofit.create(NetApi.class);
    }
    //目的：为了在联网中get出API中不同的接口
    public NetApi getNetApi() {
        return netApi;
    }


}
