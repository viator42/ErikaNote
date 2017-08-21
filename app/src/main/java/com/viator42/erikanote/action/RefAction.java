package com.viator42.erikanote.action;

import android.content.Context;
import android.content.SharedPreferences;

import com.viator42.erikanote.model.User;
import com.viator42.erikanote.utils.CommonUtils;
import com.viator42.erikanote.utils.StaticValues;

/**
 * Created by Administrator on 2016/8/2.
 */
public class RefAction {
    /**
     * 用户信息写入ref
     * @param context
     * @param user
     */
    public void setUser(Context context, User user)
    {
        SharedPreferences ref = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putBoolean("exist", true);

        editor.putString("name", user.name);
        editor.putLong("balance", CommonUtils.doubleToLong(user.balance));
        editor.putLong("totalIncome", CommonUtils.doubleToLong(user.totalIncome));
        editor.putLong("totalSpend", CommonUtils.doubleToLong(user.totalSpend));
        editor.putInt("defaultAlarmHour", user.defaultAlarmHour);
        editor.putInt("defaultAlarmMinute", user.defaultAlarmMinute);
        editor.putLong("appLastOpenTime", user.appLastOpenTime);

        editor.commit();
    }

    /**
     * 更新上一次开启的时间
     * @param context
     * @param appLastOpenTime
     */
    public void updateAppLastOpenTime(Context context, long appLastOpenTime) {
        SharedPreferences ref = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putLong("appLastOpenTime", appLastOpenTime);

        editor.commit();
    }

    /**
     * 保存设置
     * @param context
     * @param user
     */
    public void saveSetings(Context context, User user) {
        SharedPreferences ref = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putBoolean("exist", true);
        editor.putString("name", user.name);
        editor.putLong("balance", CommonUtils.doubleToLong(user.balance));
        editor.putInt("defaultAlarmHour", user.defaultAlarmHour);
        editor.putInt("defaultAlarmMinute", user.defaultAlarmMinute);

        editor.commit();
    }

    /**
     * ref获取用户信息
     * @param context
     * @return
     */
    public User getUser(Context context)
    {
        User user = null;
        SharedPreferences ref = context.getSharedPreferences("user", Context.MODE_PRIVATE);

        if(ref != null)
        {
            if(ref.getBoolean("exist", false)) {
                user = new User();
                user.name = ref.getString("name", "");
                user.balance = CommonUtils.longToDouble(ref.getLong("balance", 0));
                user.totalIncome = CommonUtils.longToDouble(ref.getLong("totalIncome", 0));
                user.totalSpend = CommonUtils.longToDouble(ref.getLong("totalSpend",0));
                user.appLastOpenTime = ref.getLong("appLastOpenTime", 0);
                user.defaultAlarmHour = ref.getInt("defaultAlarmHour", StaticValues.defaultAlarmHour);
                user.defaultAlarmMinute = ref.getInt("defaultAlarmMinute", StaticValues.defaultAlarmMinute);
            }
        }

        return user;
    }

    /**
     * 增加收入/支出
     * @param context
     * @param incomeSpend
     * @param money
     */
    public void balanceChange(Context context, int incomeSpend, double money)
    {
        SharedPreferences ref = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        if(ref != null)
        {
            double balance = CommonUtils.longToDouble(ref.getLong("balance", 0));
            double totalIncome = CommonUtils.longToDouble(ref.getLong("totalIncome", 0));
            double totalSpend = CommonUtils.longToDouble(ref.getLong("totalSpend", 0));

            switch (incomeSpend)
            {
                case StaticValues.INCOME:
                    balance += money;
                    totalIncome += money;
                    break;
                case StaticValues.SPEND:
                    balance -= money;
                    totalSpend += money;
                    break;
            }

            SharedPreferences.Editor editor = ref.edit();
            editor.putLong("balance", CommonUtils.doubleToLong(balance));
            editor.putLong("totalIncome", CommonUtils.doubleToLong(totalIncome));
            editor.putLong("totalSpend", CommonUtils.doubleToLong(totalSpend));
            editor.commit();

        }
    }

    /**
     * 初始化
     */
    public void resetAll() {

    }

}
