package com.feicui.learntocook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicui.learntocook.R;
import com.feicui.learntocook.entity.CookInfo;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by lenovo on 2016/9/12.
 */
public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder> {
    List<CookInfo.ResultBean.DataBean.StepsBean> stepsList;
    Context context;

    public DetailAdapter(List<CookInfo.ResultBean.DataBean.StepsBean> stepsList, Context context) {
        this.stepsList = stepsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.numBer_item_detail.setText((position+1)+"");
        holder.step_item_detail.setText(stepsList.get(position).getStep());
        Picasso.with(context).load(stepsList.get(position).getImg()).into(holder.image_item_detail);
    }



    @Override
    public int getItemCount() {
        return stepsList.size();
    }
    public interface itemClickListener{
        void onItemClick(int position);
    }
    private itemClickListener listener;
    public void setOnItemClickListener(itemClickListener listener){
        this.listener=listener;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView numBer_item_detail,step_item_detail;
        ImageView image_item_detail;
        public MyViewHolder(View itemView) {
            super(itemView);
            numBer_item_detail= (TextView) itemView.findViewById(R.id.numBer_item_detail);
            step_item_detail= (TextView) itemView.findViewById(R.id.step_item_detail);
            image_item_detail= (ImageView) itemView.findViewById(R.id.image_item_detail);
        }
    }
}
