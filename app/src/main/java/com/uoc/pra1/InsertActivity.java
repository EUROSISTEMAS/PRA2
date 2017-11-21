package com.uoc.pra1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InsertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("PR2 :: Insert");
    }
}
