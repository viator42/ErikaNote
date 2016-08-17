package com.viator42.erikanote.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.viator42.erikanote.AppContext;
import com.viator42.erikanote.action.IncomeSpendAction;
import com.viator42.erikanote.action.ScheduleAction;
import com.viator42.erikanote.model.IncomeSpend;
import com.viator42.erikanote.model.Schedule;
import com.viator42.erikanote.utils.CommonUtils;

/**
 * Created by Administrator on 2016/8/3.
 */
public class ScheduleReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AppContext appContext = (AppContext) context.getApplicationContext();
        Bundle bundle = intent.getExtras();
        Schedule schedule = bundle.getParcelable("obj");

        //弹窗提醒


        //删除schedule
        new ScheduleAction().remove(appContext.eDbHelper, schedule.id);

        //添加incomeSpend
        IncomeSpend incomeSpend = new IncomeSpend();
        incomeSpend.name = schedule.name;
        incomeSpend.comment = schedule.comment;
        incomeSpend.incomeSpend = schedule.incomeSpend;
        incomeSpend.money = schedule.money;
        incomeSpend.createTime = CommonUtils.getCurrentTimestamp();

        new IncomeSpendAction().insert(appContext.eDbHelper, incomeSpend);



    }
}
