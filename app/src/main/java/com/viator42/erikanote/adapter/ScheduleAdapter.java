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
 * Created by Administrator on 2016/8/9.
 */
public class ScheduleAdapter extends BaseAdapter {
    //填充数据的List
    List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();

    //用来导入布局
    private LayoutInflater inflater =null;

    //构造器
    public ScheduleAdapter(List<Map<String,Object>> list,Context context){
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
            convertView =inflater.inflate(R.layout.schedule_item, null);

            holder.incomeSpend=(TextView)convertView.findViewById(R.id.income_spend);
            holder.type=(TextView)convertView.findViewById(R.id.type);
            holder.name=(TextView)convertView.findViewById(R.id.name);
            holder.alarmTime=(TextView)convertView.findViewById(R.id.alarm_time);
            holder.feq=(TextView)convertView.findViewById(R.id.feq);
            holder.comment=(TextView)convertView.findViewById(R.id.comment);
            holder.money=(TextView)convertView.findViewById(R.id.money);

            //为view设置标签
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        holder.incomeSpend.setText(list.get(position).get("incomeSpend").toString());
        holder.type.setText(list.get(position).get("type").toString());
        holder.name.setText(list.get(position).get("name").toString());
        holder.alarmTime.setText(list.get(position).get("alarmTime").toString());
        holder.feq.setText(list.get(position).get("feq").toString());
        holder.comment.setText(list.get(position).get("comment").toString());
        holder.money.setText(list.get(position).get("money").toString());

        width =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        height =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);

        convertView.measure(width,height);

        height=convertView.getMeasuredHeight();
        width=convertView.getMeasuredWidth();

        return convertView;
    }

    static class ViewHolder {
        TextView incomeSpend;
        TextView type;
        TextView name;
        TextView alarmTime;
        TextView feq;
        TextView comment;
        TextView money;

    }

}
