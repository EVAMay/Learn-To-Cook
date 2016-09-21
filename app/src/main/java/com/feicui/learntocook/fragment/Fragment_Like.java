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
 */
public class Fragment_Like extends Fragment {
    private List<CookInfo.ResultBean.DataBean> cookDataList;
    private CookItemAdapter adapter;
    private RecyclerView recyclerView_like;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_like,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView_like= (RecyclerView) view.findViewById(R.id.recyclerView_like);
        cookDataList= CookDbUtil.getsIntence(getContext()).findLikeCookData();
        adapter=new CookItemAdapter(getContext());
        setCookDataList();
    }
    /**
     * 获取到数据后设置填充item的方法
     */
    private void setCookDataList(){

        if(cookDataList.size()<=0){
            return;
        }
        recyclerView_like.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.addData(cookDataList);
        recyclerView_like.setAdapter(adapter);
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
