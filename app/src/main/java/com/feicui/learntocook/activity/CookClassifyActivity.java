package com.feicui.learntocook.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ExpandableListView;

import com.feicui.learntocook.R;
import com.feicui.learntocook.adapter.ExpandableListAdapter;
import com.feicui.learntocook.entity.CookClassify;
import com.feicui.learntocook.network.NetApi;
import com.feicui.learntocook.network.NetClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lenovo on 2016/9/13.
 * 请求示例：http://apis.juhe.cn/cook/category?key=您申请的KEY
 */
public class CookClassifyActivity extends AppCompatActivity {
    private final String APIKEY="1217ca9885abe430e9aae68d4c55d784";
    private NetApi netApi;
    private List<CookClassify.ResultBean> groupList=null;
    private List<List<CookClassify.ResultBean.ListBean>> itemList=null;
    private List<CookClassify.ResultBean.ListBean> itemData=null;
    private ExpandableListAdapter adapter;
    private ExpandableListView expandableListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookclassify);
        expandableListView= (ExpandableListView) findViewById(R.id.expandableList);

        netApi= NetClient.getInstance(getBaseContext()).getNetApi();//务必记得回调这个接口，否则会空指针报错
        Call<CookClassify> cookClassifyCall=netApi.getCookClassify(APIKEY);
        cookClassifyCall.enqueue(cookclassifyback);
    }
    private Callback<CookClassify> cookclassifyback=new Callback<CookClassify>() {
        @Override
        public void onResponse(Call<CookClassify> call, Response<CookClassify> response) {
            groupList=response.body().getResult();//获取的是分类名
            for (int i=0;i<groupList.size();i++){
                itemData.add(response.body().getResult().get(i).getList().get(i));
                Log.d("debug","子item"+itemData.toString());
            }


//            itemList.add(itemData);


            adapter=new ExpandableListAdapter(groupList,itemList,getBaseContext());
            expandableListView.setAdapter(adapter);
                    String str=response.body().getResult().toString();
            Log.d("debug","获取到菜谱分类是："+str);
        }

        @Override
        public void onFailure(Call<CookClassify> call, Throwable t) {

        }
    };
}
