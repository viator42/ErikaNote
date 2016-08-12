package com.viator42.erikanote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.viator42.erikanote.utils.StaticValues;

/**
 * Created by Administrator on 2016/8/2.
 */
public class IncomeSpend extends BaseModel implements Parcelable{
    public long id;
    public long createTime;
    public String name;
    public String comment;
    public double money;
    public int incomeSpend;
    public int type;
    public int min;
    public int max;

    public IncomeSpend(){
    }

    protected IncomeSpend(Parcel in) {
        id = in.readLong();
        createTime = in.readLong();
        name = in.readString();
        comment = in.readString();
        money = in.readDouble();
        incomeSpend = in.readInt();
        type = in.readInt();
        min = in.readInt();
        max = in.readInt();
    }

    public static final Creator<IncomeSpend> CREATOR = new Creator<IncomeSpend>() {
        @Override
        public IncomeSpend createFromParcel(Parcel in) {
            return new IncomeSpend(in);
        }

        @Override
        public IncomeSpend[] newArray(int size) {
            return new IncomeSpend[size];
        }
    };

    public String getIncomeSpendName()
    {
        switch (incomeSpend)
        {
            case StaticValues.INCOME:
                return "收入";
            case StaticValues.SPEND:
                return "支出";
            default:
                return  "";
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(createTime);
        dest.writeString(name);
        dest.writeString(comment);
        dest.writeDouble(money);
        dest.writeInt(incomeSpend);
        dest.writeInt(type);
        dest.writeInt(min);
        dest.writeInt(max);
    }
}
