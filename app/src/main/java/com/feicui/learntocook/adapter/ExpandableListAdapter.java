package com.feicui.learntocook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.feicui.learntocook.R;
import com.feicui.learntocook.entity.CookClassify;

import java.util.List;

/**
 * Created by lenovo on 2016/9/13.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter{
    private List<CookClassify.ResultBean> groupList;
    private List<List<CookClassify.ResultBean.ListBean>> itemList;
    private Context context;

    public ExpandableListAdapter(List<CookClassify.ResultBean> groupList, List<List<CookClassify.ResultBean.ListBean>> itemList, Context context) {
        this.groupList = groupList;
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public int getGroupCount() {

        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return itemList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return itemList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 取得用于显示给定分组的视图. 这个方法仅返回分组的视图对象
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderGroup holderGroup;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_classify_group,parent,false);
            holderGroup=new ViewHolderGroup();
            holderGroup.classify_group= (TextView) convertView.findViewById(R.id.classify_group);
            convertView.setTag(holderGroup);
        }else {
            holderGroup= (ViewHolderGroup) convertView.getTag();
        }
        holderGroup.classify_group.setText(groupList.get(groupPosition).getName());

        return convertView;
    }

    /**
     * 取得显示给定分组给定子位置的数据用的视图
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderItem holderItem;
        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.item_classify_item,parent,false);
            holderItem=new ViewHolderItem();
            holderItem.classify_item= (TextView) convertView.findViewById(R.id.classify_item);
            convertView.setTag(holderItem);
        }else {
            holderItem= (ViewHolderItem) convertView.getTag();
        }
        holderItem.classify_item.setText(itemList.get(groupPosition).get(childPosition).getName());
        return convertView;
    }

    /**
     * 设置是否可选中，返回true
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    static  class ViewHolderGroup{
        TextView classify_group;
    }
    static  class ViewHolderItem{
        TextView classify_item;
    }
}
