package com.viator42.erikanote;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.viator42.erikanote.model.User;
import com.viator42.erikanote.utils.EDbHelper;

/**
 * Created by Administrator on 2016/8/2.
 */
public class AppContext extends Application {
    public EDbHelper eDbHelper;
    public AlarmManager alarmManager;
    public User user;

    @Override
    public void onCreate() {
        super.onCreate();

        //数据库对象
        eDbHelper = new EDbHelper(getApplicationContext(), "erikaNote");
        //alarm对象
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

    }

    //浏览器打开特定url
    public void openUrlinBrowser(Context context, String url)
    {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }
}
