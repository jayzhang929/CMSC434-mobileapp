package edu.umd.cs.tabby;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

public class NotificationHistory extends AppCompatActivity {

    static final String[] titles = {"Lookup Polling Location", "7 Days until Election Day"};
    static final String[] dates = {"04-18-2016", "04-12-2016"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new NotificationHistoryArrayAdapter(this, titles, dates));

    }

}
