package com.viator42.erikanote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.viator42.erikanote.utils.CommonUtils;

/**
 * Created by Administrator on 2016/8/2.
 */
public class Schedule extends BaseModel implements Parcelable
{
    public long id;
    public double money;    //金额
    public String name;     //名称
    public String comment;  //备注(选填
    public long createTime; //创建时间
    public int incomeSpend; //收入/支出
    public int type;        //类型  单次/多次
    public int feq;         //多次的频率 每天/每周/每月
    public int feqValue;   //频率值 每天几点/每周周几/每月几号
    public long alarmTime;  //单次提醒时间

    public Schedule() {
    }

    protected Schedule(Parcel in) {
        id = in.readLong();
        money = in.readDouble();
        name = in.readString();
        comment = in.readString();
        createTime = in.readLong();
        incomeSpend = in.readInt();
        type = in.readInt();
        feq = in.readInt();
        feqValue = in.readInt();
        alarmTime = in.readLong();
    }

    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeDouble(money);
        dest.writeString(name);
        dest.writeString(comment);
        dest.writeLong(createTime);
        dest.writeInt(incomeSpend);
        dest.writeInt(type);
        dest.writeInt(feq);
        dest.writeInt(feqValue);
        dest.writeLong(alarmTime);
    }
}
