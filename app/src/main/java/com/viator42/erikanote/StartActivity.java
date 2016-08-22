package com.viator42.erikanote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_start);

        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
