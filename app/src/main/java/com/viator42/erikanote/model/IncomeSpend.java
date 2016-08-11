package com.viator42.erikanote.model;

import com.viator42.erikanote.utils.StaticValues;

/**
 * Created by Administrator on 2016/8/2.
 */
public class IncomeSpend extends BaseModel{
    public long id;
    public long createTime;
    public String name;
    public String comment;
    public double money;
    public int incomeSpend;
    public int type;
    public int min;
    public int max;

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
}
