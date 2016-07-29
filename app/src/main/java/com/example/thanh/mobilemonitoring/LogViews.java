package com.example.thanh.mobilemonitoring;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class LogViews extends AppCompatActivity {

    TextView txtvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_views);

        String getIntent = getIntent().getStringExtra("log");

        txtvLog = (TextView)findViewById(R.id.textviewLog);

        txtvLog.setText(getIntent);

    }
}
