package com.viator42.erikanote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.viator42.erikanote.action.RefAction;
import com.viator42.erikanote.model.User;
import com.viator42.erikanote.utils.StaticValues;

public class StartActivity extends Activity {
    private long currentTimeMil;
    private AppContext appContext;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        appContext = (AppContext) getApplicationContext();
        currentTimeMil = System.currentTimeMillis();

        //获取用户信息
        user = new RefAction().getUser(StartActivity.this);
        if(user != null)
        {
            appContext.user = user;
            new RefAction().updateAppLastOpenTime(StartActivity.this, currentTimeMil);
        }
        else {
            //创建用户信息
            User user = new User();
            user.name = getResources().getString(R.string.new_user_name);
            user.balance = 0;
            user.defaultAlarmHour = StaticValues.defaultAlarmHour;
            user.defaultAlarmMinute = StaticValues.defaultAlarmMinute;
            user.totalIncome = 0;
            user.totalSpend = 0;
            user.appLastOpenTime = currentTimeMil;
            new RefAction().setUser(StartActivity.this, user);
            appContext.user = user;

            appContext.firstOpen = true;
        }

        new LoadTask().start();
    }

    public class LoadTask extends Thread
    {
        @Override
        public void run() {

            long timeInterval = System.currentTimeMillis() - currentTimeMil;
            if(timeInterval < StaticValues.splashTime)
            {
                try {
                    sleep(StaticValues.splashTime - timeInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
