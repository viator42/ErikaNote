package com.viator42.erikanote.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Administrator on 2016/8/3.
 */
public class ScheduleReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d("ErikaNote", bundle.getString("msg"));
        //弹窗提醒

        //删除schedule



    }
}
