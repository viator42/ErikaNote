package com.viator42.erikanote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viator42.erikanote.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by viator42 on 16/8/9.
 */
public class IncomeSpendAdapter extends BaseAdapter {
    //填充数据的List
    List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();

    //用来导入布局
    private LayoutInflater inflater =null;
    //构造器
    public IncomeSpendAdapter(List<Map<String,Object>> list,Context context){
        this.list=list;
        inflater=LayoutInflater.from(context);
    }

    private int width;
    private int height;
    private ViewHolder holder;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (Long)(list.get(position).get("id"));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.income_spend_item, null);

            holder.name = (TextView)convertView.findViewById(R.id.name);
            holder.type = (TextView) convertView.findViewById(R.id.type);
            holder.money = (TextView) convertView.findViewById(R.id.money);
            holder.createTime = (TextView) convertView.findViewById(R.id.create_time);
            holder.comment = (TextView) convertView.findViewById(R.id.comment);

            //为view设置标签
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        holder.name.setText(list.get(position).get("name").toString());
        holder.type.setText(list.get(position).get("type").toString());
        holder.money.setText(list.get(position).get("money").toString());
        holder.createTime.setText(list.get(position).get("createTime").toString());
        holder.comment.setText(list.get(position).get("comment").toString());

        width =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        height =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);

        convertView.measure(width,height);

        height=convertView.getMeasuredHeight();
        width=convertView.getMeasuredWidth();

        return convertView;
    }

    static class ViewHolder {
        TextView type;
        TextView name;
        TextView money;
        TextView createTime;
        TextView comment;

    }
}
