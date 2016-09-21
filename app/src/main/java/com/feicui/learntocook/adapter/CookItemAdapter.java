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

import java.util.ArrayList;
import java.util.Collection;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by lenovo on 2016/9/8.
 */
public class CookItemAdapter extends RecyclerView.Adapter<CookItemAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<CookInfo.ResultBean.DataBean> cookItemList;

    public CookItemAdapter( Context context) {
        this.context=context;
        cookItemList=new ArrayList<>();
    }
    public void addData(Collection<CookInfo.ResultBean.DataBean> list){
        cookItemList.addAll(list);
        notifyDataSetChanged();
    }
    public void clear(){
        cookItemList.clear();
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.title_item.setText(cookItemList.get(position).getTitle());
        holder.tags_item.setText(cookItemList.get(position).getTags());
        holder.ingredients_item.setText(cookItemList.get(position).getIngredients());
        holder.burden_item.setText(cookItemList.get(position).getBurden());
        if (cookItemList.get(position).getAlbums().get(0)!=null){
            Picasso.with(context).load(cookItemList.get(position).getAlbums().get(0))
                  .into(holder.imageView_item);
        }else {
            Picasso.with(context).load(R.mipmap.pic_09).into(holder.imageView_item);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //回调接口，设置item点击事件
                if (listener!=null){
                    listener.onItemClick(position);
                }
            }
        });
    }
    public interface itemClickListener{
        void onItemClick(int position);
    }
    private itemClickListener listener;
    public void setOnItemClickListener(itemClickListener listener){
        this.listener=listener;
    }
    @Override
    public int getItemCount() {
        return cookItemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView_item;
        TextView title_item,tags_item,ingredients_item,burden_item;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView_item= (ImageView) itemView.findViewById(R.id.imageView_item);
            title_item= (TextView) itemView.findViewById(R.id.title_item);
            tags_item= (TextView) itemView.findViewById(R.id.tags_item);
            ingredients_item= (TextView) itemView.findViewById(R.id.ingredients_item);
            burden_item= (TextView) itemView.findViewById(R.id.burden_item);
        }
    }
}
