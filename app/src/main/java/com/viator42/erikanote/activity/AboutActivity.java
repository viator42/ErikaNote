package com.viator42.erikanote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.viator42.erikanote.AppContext;
import com.viator42.erikanote.R;

public class AboutActivity extends AppCompatActivity {
    private TextView blogBtn;
    private TextView githubBtn;
    private AppContext appContext;

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

    }

}
