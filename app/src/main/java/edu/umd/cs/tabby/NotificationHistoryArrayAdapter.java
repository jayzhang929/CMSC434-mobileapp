package edu.umd.cs.tabby;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jayzhang on 5/1/16.
 */
public class NotificationHistoryArrayAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private ArrayList<String> mValues;
    private ArrayList<String> mDates;
    private ArrayList<String> mCategories;

    public NotificationHistoryArrayAdapter (Context context, ArrayList<String> values, ArrayList<String> dates, ArrayList<String> categories) {
        super(context, R.layout.notification_history_element, values);

        mContext = context;
        mValues = values;
        mDates = dates;
        mCategories = categories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = layoutInflater.inflate(R.layout.notification_history_element, parent, false);

        TextView title = (TextView) rootView.findViewById(R.id.notificationHistoryTitle);
        TextView date = (TextView) rootView.findViewById(R.id.notificationHistoryDate);
        TextView category = (TextView) rootView.findViewById(R.id.category);

        // set text and color
        title.setText(mValues.get(position));
        date.setText(mDates.get(position));
        category.setText(mCategories.get(position));
        setCategoryColor(category);

        return rootView;
    }

    // this method set the color of category text
    private void setCategoryColor(TextView category) {
        int color;
        switch ((String) category.getText()) {
            case "Polling":
                color = mContext.getResources().getColor(R.color.yellow, null);
                break;
            case "Deadlines":
                color = mContext.getResources().getColor(R.color.red, null);
                break;
            case "News":
                color = mContext.getResources().getColor(R.color.dark_blue, null);
                break;
            case "Events":
                color = mContext.getResources().getColor(R.color.green, null);
                break;
            default:
                color = mContext.getResources().getColor(R.color.blue, null);
                break;
        }
        category.setTextColor(color);
    }
}
