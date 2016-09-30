package com.feicui.learntocook.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.feicui.learntocook.R;
import com.feicui.learntocook.activity.DetailActivity;
import com.feicui.learntocook.adapter.CookItemAdapter;
import com.feicui.learntocook.entity.CookInfo;
import com.feicui.learntocook.network.NetApi;
import com.feicui.learntocook.network.NetClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lenovo on 2016/9/7.
 */
public class Fragment_All extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView_all;
    private PtrClassicFrameLayout ptrClassicFrameLayout;//支持下拉刷新的ViewGroup
//    private final String APIKEY="1217ca9885abe430e9aae68d4c55d784";
    private final String APIKEY="09712326e450de60408f6415b4b705f6";
    private  String PN="15";//数据返回起始下标
    private  String RN="15";//数据返回条数，最大30
    private String menu="";
    private int cid=0;//菜谱分类的id
    private List<CookInfo.ResultBean.DataBean> cookItemList;
    private CookItemAdapter adapter;
    private NetApi netApi;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_all,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cid=getArguments().getInt("cid");
        Log.d("debug","fragment获取的cid是"+cid);
        if (getArguments().get("menu")!=null){
            menu=getArguments().getString("menu");
        }
        recyclerView_all= (RecyclerView) view.findViewById(R.id.recyclerView_all);
        progressBar= (ProgressBar) view.findViewById(R.id.progressBar);
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        refreshLayout.setColorSchemeResources(R.color.colorAccent);
        refreshLayout.setOnRefreshListener(this);

        netApi= NetClient.getInstance(getContext()).getNetApi();
        //判断cid，为0就使用搜索菜谱名的方法进行联网，不为0就是用分类的方法进行联网
        if (cid==0){
           findCook();//根据搜索菜谱名获取网络数据的方法
        }else {
           ClassifyCook();//根据分类获取菜谱的方法
        }
//        // 配置上拉加载
//        Mugen.with(recyclerView_all, new MugenCallbacks() {
//            @Override
//            public void onLoadMore() {
//                progressBar.setVisibility(View.VISIBLE);
//                PN=cookItemList.size()+"";
//                //判断cid，为0就使用搜索菜谱名的方法进行联网，不为0就是用分类的方法进行联网
//                if (cid==0){
//                    findCook();//根据搜索菜谱名获取网络数据的方法
//                }else {
//                    ClassifyCook();//根据分类获取菜谱的方法
//                }
//
//            }
//
//            @Override
//            public boolean isLoading() {
//                return progressBar.getVisibility()==View.VISIBLE;
//            }
//
//            @Override
//            public boolean hasLoadedAllItems() {
//                return true;
//            }
//        }).start();
        adapter=new CookItemAdapter(getContext());

    }

    /**
     * 根据搜索菜谱名获取网络数据的方法
     */
    private void findCook(){
        Call<CookInfo> call=netApi.getCookData(APIKEY,menu,RN,PN);
        call.enqueue(callback_find);

    }
    /**
     * 搜索菜谱名的callback
     */
    private Callback<CookInfo> callback_find=new Callback<CookInfo>() {
        @Override
        public void onResponse(Call<CookInfo> call, Response<CookInfo> response) {
            if (!response.body().getResultcode().equals("200")){
                Toast.makeText(getContext(), "数据异常", Toast.LENGTH_SHORT).show();
                return;
            }
            cookItemList=response.body().getResult().getData();
            setListData();

        }

        @Override
        public void onFailure(Call<CookInfo> call, Throwable t) {
            Toast.makeText(getContext(),"没有获取到数据:"+t.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 根据分类获取菜谱的方法
     */
    private void ClassifyCook(){
        Call<CookInfo> call=netApi.getClassify(APIKEY,cid,RN,PN);
        call.enqueue(callback_classify);
    }
    /**
     * 分类的菜谱的callback
     */
    private Callback<CookInfo> callback_classify=new Callback<CookInfo>() {
        @Override
        public void onResponse(Call<CookInfo> call, Response<CookInfo> response) {
            if (response.body().getResult()==null){
                Toast.makeText(getContext(), "数据异常", Toast.LENGTH_SHORT).show();
                return;
            }
            cookItemList=response.body().getResult().getData();

            setListData();

        }

        @Override
        public void onFailure(Call<CookInfo> call, Throwable t) {
            Toast.makeText(getContext(),"没有获取到数据:"+t.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };

    //设置集合数据，填充Item的方法
    private void setListData(){
        if (cookItemList.size()<=0){
            Toast.makeText(getContext(),"数据异常",Toast.LENGTH_SHORT).show();
            return;
        }
//            cookItemList.add(response.body().getResult().getData().get(0));
        Log.d("debug","Fragment_all集合的长度是"+cookItemList.size());
        Log.d("debug","Fragment_all预览图的URL是："+cookItemList.get(0).getAlbums().get(0));

        adapter.addData(cookItemList);
        recyclerView_all.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_all.setAdapter(adapter);

        adapter.setOnItemClickListener(new CookItemAdapter.itemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent=new Intent(getContext(), DetailActivity.class);
                Bundle bundle=new Bundle();
                intent.putExtra("cookName",cookItemList.get(position).getTitle());//菜名
                int id= Integer.parseInt(cookItemList.get(position).getId());
                bundle.putInt("id",id);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

    }
    /**刷新数据的方法*/
    private void refreshData(){
//        if (cookItemList.size()>0){
//            adapter.clear();
//        }
        //判断cid，为0就使用搜索菜谱名的方法进行联网，不为0就是用分类的方法进行联网
        if (cid==0){
            findCook();//根据搜索菜谱名获取网络数据的方法
        }else {
            ClassifyCook();//根据分类获取菜谱的方法
        }
    }
    int time=3;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time--;
            if (time>0){
                handler.sendEmptyMessageDelayed(1,1000);
            }
            if (time<=0){
                refreshData();
                refreshLayout.setRefreshing(false);
            }
        }
    };
    @Override
    public void onRefresh() {
        time=3;
        handler.sendEmptyMessage(1);

    }

    @Override
    public void onDestroyView() {
        handler.removeMessages(1);
        super.onDestroyView();
    }
}
