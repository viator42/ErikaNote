package com.viator42.erikanote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viator42.erikanote.R;
import com.viator42.erikanote.model.Schedule;
import com.viator42.erikanote.utils.CommonUtils;
import com.viator42.erikanote.utils.StaticValues;

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
            holder.name=(TextView)convertView.findViewById(R.id.name);
            holder.alarmTime=(TextView)convertView.findViewById(R.id.alarm_time);
            holder.type=(TextView)convertView.findViewById(R.id.type);
            holder.comment=(TextView)convertView.findViewById(R.id.comment);
            holder.money=(TextView)convertView.findViewById(R.id.money);
            holder.interval=(TextView)convertView.findViewById(R.id.interval);

            //为view设置标签
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        Schedule schedule = (Schedule) list.get(position).get("schedule");
        holder.incomeSpend.setText(schedule.getIncomeSpendText());
        holder.name.setText(schedule.name);
        if(!CommonUtils.isValueEmpty(schedule.comment))
        {
            holder.comment.setText(schedule.comment);
        }
        holder.money.setText(String.valueOf(schedule.money));
        holder.type.setText(schedule.getTypeText());
        switch (schedule.type)
        {
            case StaticValues.TYPE_ONCE:
                holder.alarmTime.setText(CommonUtils.timestampToDatetime(schedule.alarmTime));

                break;
            case StaticValues.TYPE_REPEAT:
                switch (schedule.feq)
                {
                    case StaticValues.FEQ_DAILY:
                        holder.alarmTime.setText("每天的" + schedule.feqValue + "点");
                        break;
                    case StaticValues.FEQ_WEEKLY:
                        holder.alarmTime.setText("每周的周" + (schedule.feqValue + 1));
                        break;
                    case StaticValues.FEQ_MONTHLY:
                        holder.alarmTime.setText("每月的" + schedule.feqValue + "号");
                        break;

                }

                break;

        }
        holder.interval.setText(CommonUtils.timeInterval(CommonUtils.getCurrentTimestamp(), schedule.alarmTime));
        /*
        holder.alarmTime.setText(list.get(position).get("alarmTime").toString());
        holder.feq.setText(list.get(position).get("feq").toString());
        */

        width =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        height =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);

        convertView.measure(width,height);

        height=convertView.getMeasuredHeight();
        width=convertView.getMeasuredWidth();

        return convertView;
    }

    static class ViewHolder {
        TextView incomeSpend;
        TextView name;
        TextView alarmTime;
        TextView type;
        TextView comment;
        TextView money;
        TextView interval;
    }

}
