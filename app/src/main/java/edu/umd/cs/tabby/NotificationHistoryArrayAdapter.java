package edu.umd.cs.tabby;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jayzhang on 5/1/16.
 */
public class NotificationHistoryArrayAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private ArrayList<String> mValues;
    private ArrayList<String> mDates;

    public NotificationHistoryArrayAdapter (Context context, ArrayList<String> values, ArrayList<String> dates) {
        super(context, R.layout.notification_history_element, values);

        mContext = context;
        mValues = values;
        mDates = dates;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = layoutInflater.inflate(R.layout.notification_history_element, parent, false);

        TextView title = (TextView) rootView.findViewById(R.id.notificationHistoryTitle);
        TextView date = (TextView) rootView.findViewById(R.id.notificationHistoryDate);

        title.setText(mValues.get(position));
        date.setText(mDates.get(position));

        return rootView;
    }
}
