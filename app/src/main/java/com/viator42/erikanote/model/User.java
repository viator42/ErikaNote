package com.viator42.erikanote.model;

/**
 * Created by Administrator on 2016/8/2.
 */
public class User extends BaseModel{
    //------offline------
    public String name; //姓名
    public double balance;  //余额
    public double totalIncome;  //总收入
    public double totalSpend;   //总支出
    public long appLastOpenTime;   //上次应用打开时间
    public int defaultAlarmHour;    //默认提醒时间
    public int defaultAlarmMinute;
}
