package com.viator42.erikanote;

import android.app.Application;

import com.viator42.erikanote.utils.EDbHelper;

/**
 * Created by Administrator on 2016/8/2.
 */
public class AppContext extends Application {
    public EDbHelper eDbHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        //数据库对象
        eDbHelper = new EDbHelper(getApplicationContext(), "erikaNote");
    }
}
