package com.viator42.erikanote.action;

import android.content.Context;
import android.content.SharedPreferences;

import com.viator42.erikanote.model.User;

/**
 * Created by Administrator on 2016/8/2.
 */
public class RefAction {
    public void setUser(Context context, User user)
    {
        //用户信息写入ref
        SharedPreferences ref = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putString("imie", user.imie);
        editor.putString("name", user.name);
        editor.putString("balance", Double.toString(user.balance));
        editor.putString("totalIncome", Double.toString(user.totalIncome));
        editor.putString("totalSpend", Double.toString(user.totalSpend));
        editor.putLong("openCount", user.openCount);

        editor.putLong("id", user.id);
        editor.putString("username", user.username);
        editor.putString("password", user.password);
        editor.putLong("registerTime", user.registerTime);
        editor.putLong("lastLoginTime", user.lastLoginTime);

        editor.commit();
    }

    public User getUser(Context context)
    {
        User user = null;
        SharedPreferences ref = context.getSharedPreferences("user", Context.MODE_PRIVATE);

        if(ref != null)
        {
            user = new User();
            user.imie = ref.getString("imie", "");
            user.name = ref.getString("name", "");
            user.balance = Double.valueOf(ref.getString("balance", "0"));
            user.totalIncome = Double.valueOf(ref.getString("totalIncome", "0"));
            user.totalSpend = Double.valueOf(ref.getString("totalSpend", "0"));
            user.openCount = ref.getLong("openCount", 0);
            user.lastOpenTime = ref.getLong("lastOpenTime", 0);

            user.id = ref.getLong("id", 0);
            user.username = ref.getString("username", null);
            user.password = ref.getString("password", null);
            user.registerTime = ref.getLong("registerTime", 0);
            user.lastLoginTime = ref.getLong("lastLoginTime", 0);
        }

        return user;
    }
}
