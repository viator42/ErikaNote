package com.viator42.erikanote.model;

import com.viator42.erikanote.utils.CommonUtils;

/**
 * Created by Administrator on 2016/8/2.
 */
public class Schedule extends BaseModel
{
    public long id;
    public double money;    //金额
    public String name;     //名称
    public String comment;  //备注(选填
    public long createTime; //创建时间
    public int type;        //类型 收入/支出 单次/多次
    public int feq;         //多次的频率 每天/每周/每月
    public int feqValue;   //频率值 每天几点/每周周几/每月几号
    public long alarmTime;  //单次提醒时间
    public String msg;

    public boolean insertValidation()
    {
        boolean result = true;
        if(CommonUtils.isValueEmpty(name))
        {
            result = false;
            msg = "名称不能为空";
        }
        if(money == 0)
        {
            result = false;
            msg = "请填写金额";
        }

        return result;
    }
}
