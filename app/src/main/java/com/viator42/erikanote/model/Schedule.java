package com.viator42.erikanote.model;

import com.viator42.erikanote.utils.CommonUtils;

/**
 * Created by Administrator on 2016/8/2.
 */
public class Schedule extends BaseModel
{
    public long id;
    public double money;
    public String name;
    public String comment;
    public long createTime;
    public int type;
    public int feq;
    public long feqTime;
    public long alarmTime;
    public String msg;

    public boolean insertValidation()
    {
        boolean result = true;
        if(CommonUtils.isValueEmpty(name))
        {
            result = false;
            msg = "名称不能为空";
        }

        return result;
    }
}
