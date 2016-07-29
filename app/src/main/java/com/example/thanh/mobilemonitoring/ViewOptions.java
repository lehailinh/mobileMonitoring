package com.example.thanh.mobilemonitoring;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewOptions extends AppCompatActivity {

    ListView listViewLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_options);
        final Bundle receive = this.getIntent().getExtras();

        final ArrayList<String> logTitle = new ArrayList<String>();

        logTitle.add("Contacts Log");
        logTitle.add("Calls Log");
        logTitle.add("SMS Log");
        logTitle.add("Bookmarks Log");
        logTitle.add("Photos Log");


        listViewLog = (ListView)findViewById(R.id.listViewLog);

        ArrayAdapter adapter = new ArrayAdapter(
            ViewOptions.this,
            android.R.layout.simple_list_item_1,
            logTitle
        );

        listViewLog.setAdapter(adapter);

        listViewLog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Intent intent = new Intent(ViewOptions.this, LogViews.class);
            switch (position) {
                case(0): {
                    intent.putExtra("log", receive.getString("sContactLog"));
                    break;
                }
                case(1): {
                    intent.putExtra("log", receive.getString("sCallLog"));
                    break;
                }
                case(2): {
                    intent.putExtra("log", receive.getString("sSMSLog"));
                    break;
                }
                case(3): {
                    intent.putExtra("log", receive.getString("sBookmarkLog"));
                    break;
                }
                case(4): {
                    intent.putExtra("log", receive.getString("sPhotoLog"));
                    break;
                }
            }
            startActivity(intent);
            }
        });
    }
}
