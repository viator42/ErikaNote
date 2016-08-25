package com.viator42.erikanote.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.viator42.erikanote.AppContext;
import com.viator42.erikanote.R;

import static com.viator42.erikanote.R.id.version_name;

public class AboutActivity extends AppCompatActivity {
    private TextView blogBtn;
    private TextView githubBtn;
    private AppContext appContext;
    private TextView versionNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        appContext = (AppContext) getApplicationContext();

        blogBtn = (TextView) findViewById(R.id.blog);
        blogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appContext.openUrlinBrowser(AboutActivity.this, getResources().getString(R.string.blog));
            }
        });
        githubBtn = (TextView) findViewById(R.id.github);
        githubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appContext.openUrlinBrowser(AboutActivity.this, getResources().getString(R.string.github));
            }
        });
        versionNameTextView = (TextView) findViewById(version_name);
        versionNameTextView.setText("v1.0");

    }

    //获取当前版本号
    private String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo("cn.testgethandsetinfo", 0);
            versionName = packageInfo.versionName;
//            packageInfo.versionCode;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
