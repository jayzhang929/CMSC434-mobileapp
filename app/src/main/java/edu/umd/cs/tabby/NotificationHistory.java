package edu.umd.cs.tabby;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;

public class NotificationHistory extends AppCompatActivity {

    // those are the data structures needed for the notification history page
    ArrayList<String> mTitles;
    ArrayList<String> mDates;
    ArrayList<String> mCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // instantiate and populate the instance variables/structures
        mTitles = populateTitles();
        mDates = populateDates();
        mCategories = populateCategories();

        // set the adapter for the ListView
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new NotificationHistoryArrayAdapter(this, mTitles, mDates, mCategories));

    }

    // this method instantiate and populate titles
    private ArrayList<String> populateTitles() {
        ArrayList<String> titles = new ArrayList<>();
        // check whether new notification pushed
        if (getSharedPreferences(NotificationSetting.NOTI_NAME, NotificationSetting.NOTI_MODE).contains("title"))
            titles.add(getSharedPreferences(NotificationSetting.NOTI_NAME, NotificationSetting.NOTI_MODE).getString("title", "NA"));
        titles.add("Lookup Polling Location");
        titles.add("167 Days until Election Day");
        titles.add("174 Days until Election Day");
        titles.add("Check new Voter ID Law");
        titles.add("Hillary's Book came out");

        return titles;
    }

    // this method instantiate and populate dates
    private ArrayList<String> populateDates() {
        ArrayList<String> dates = new ArrayList<>();
        // check whether new notification pushed
        if (getSharedPreferences(NotificationSetting.NOTI_NAME, NotificationSetting.NOTI_MODE).contains("date"))
            dates.add(getSharedPreferences(NotificationSetting.NOTI_NAME, NotificationSetting.NOTI_MODE).getString("date", "NA"));
        dates.add("04-18-2016");
        dates.add("04-12-2016");
        dates.add("04-05-16");
        dates.add("03-21-16");
        dates.add("03-09-16");

        return dates;
    }

    // this method instantiate and populate categories
    private ArrayList<String> populateCategories() {
        ArrayList<String> categories = new ArrayList<>();
        // check whether new notification pushed
        if (getSharedPreferences(NotificationSetting.NOTI_NAME, NotificationSetting.NOTI_MODE).contains("category"))
            categories.add(getSharedPreferences(NotificationSetting.NOTI_NAME, NotificationSetting.NOTI_MODE).getString("category", "NA"));
        categories.add("Polling");
        categories.add("Deadlines");
        categories.add("Deadlines");
        categories.add("News");
        categories.add("Events");

        return categories;
    }

}
