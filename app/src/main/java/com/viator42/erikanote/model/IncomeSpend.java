package com.viator42.erikanote.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.viator42.erikanote.R;
import com.viator42.erikanote.utils.CommonUtils;
import com.viator42.erikanote.utils.StaticValues;

/**
 * Created by Administrator on 2016/8/2.
 */
public class IncomeSpend extends BaseModel implements Parcelable{
    public long id;
    public long createTime;
    public int category;
    public String comment;
    public double money;
    public int incomeSpend;
    public int type;
    public int min;
    public int max;
    public String msg;

    public IncomeSpend(){
    }

    protected IncomeSpend(Parcel in) {
        id = in.readLong();
        createTime = in.readLong();
        category = in.readInt();
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
        dest.writeInt(category);
        dest.writeString(comment);
        dest.writeDouble(money);
        dest.writeInt(incomeSpend);
        dest.writeInt(type);
        dest.writeInt(min);
        dest.writeInt(max);
    }

    public boolean insertValidation(Context context)
    {
        boolean result = true;

        if(category == 0)
        {
            msg = context.getResources().getString(R.string.category_not_null);
            return false;
        }
        if(money == 0)
        {
            msg = context.getResources().getString(R.string.money_not_null);
            return false;
        }

        return result;
    }

}
