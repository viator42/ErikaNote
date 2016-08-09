package com.viator42.erikanote.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.viator42.erikanote.AppContext;
import com.viator42.erikanote.R;

public class InsertIncomeSpendActivity extends AppCompatActivity {
    private AppContext appContext;
    private EditText nameEditText;
    private EditText commentEditText;
    private EditText moneyEditText;
    private Button confirmBtn;
    private Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_income_spend);

        appContext = (AppContext) getApplicationContext();
        nameEditText = (EditText) findViewById(R.id.name);
        commentEditText = (EditText) findViewById(R.id.comment);
        moneyEditText = (EditText) findViewById(R.id.money);



    }
}
