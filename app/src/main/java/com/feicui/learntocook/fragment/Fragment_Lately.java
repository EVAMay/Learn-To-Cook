package com.feicui.learntocook.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feicui.learntocook.R;
import com.feicui.learntocook.activity.DetailActivity;
import com.feicui.learntocook.adapter.CookItemAdapter;
import com.feicui.learntocook.db.CookDbUtil;
import com.feicui.learntocook.entity.CookInfo;

import java.util.List;

/**
 * Created by lenovo on 2016/9/7.
 * 需要两个集合存放从数据库取出的数据
 *
 */
public class Fragment_Lately extends Fragment {
    private RecyclerView recyclerView_lately;
    private List<CookInfo.ResultBean.DataBean> cookDataList;//菜谱数据的集合
    private List<CookInfo.ResultBean.DataBean.StepsBean> cookStepsList;//菜谱步骤的集合
    private CookItemAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_lately,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView_lately= (RecyclerView) view.findViewById(R.id.recyclerView_lately);
        adapter=new CookItemAdapter(getContext());
        adapter.notifyDataSetChanged();//进入页面刷新一下数据
        cookDataList= CookDbUtil.getsIntence(getContext()).findCookData();//获取菜谱数据


//        if (cookDataList!=null){
//            cookStepsList=CookDbUtil.getsIntence(getContext()).findStepsList(cookDataList.get(0).getId());//获取步骤
//        }

//        Log.d("debug","最近浏览界面从数据库获取的集合cookStepsList："+cookStepsList.toString());
        setCookDataList();

    }

    /**
     * 获取到数据后设置填充item的方法
     */
    private void setCookDataList(){

        if(cookDataList.size()<=0){
            return;
        }
        recyclerView_lately.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.addData(cookDataList);
        recyclerView_lately.setAdapter(adapter);
        adapter.setOnItemClickListener(new CookItemAdapter.itemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getContext(), DetailActivity.class);
                Bundle bundle=new Bundle();
                intent.putExtra("cookName",cookDataList.get(position).getTitle());//菜名
                int id= Integer.parseInt(cookDataList.get(position).getId());
                bundle.putInt("id",id);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
}
