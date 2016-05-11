package edu.umd.cs.tabby;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jayzhang on 4/18/16.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    // the adapter contains those instance variables
    private Context mContext;
    private List<String> mGroupTitles;
    private HashMap<String, List<String>> mChildText;
    private HashMap<String, List<String>> mLinkText;

    public ExpandableListAdapter (Context context, List<String> groupTitles, HashMap<String, List<String>> childText, HashMap<String, List<String>> linkText) {
        mContext = context;
        mGroupTitles = groupTitles;
        mChildText = childText;
        mLinkText = linkText;
    }

    @Override
    public int getGroupCount() {
        return mGroupTitles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildText.get(mGroupTitles.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupTitles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildText.get(mGroupTitles.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // if the convertView is null, inflate with the xml
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.who_group_title, null);
        }

        // set the text for the group title
        TextView groupTitle = (TextView) convertView.findViewById(R.id.groupTitle);
        groupTitle.setText(mGroupTitles.get(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // if the converView is null, inflate with the xml
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.who_child_text, null);
        }

        // set the text for the child text
        TextView childText = (TextView) convertView.findViewById(R.id.childText);
        childText.setText((String) getChild(groupPosition, childPosition));
        if(mLinkText != null) {

        } else {
            TextView linkText = (TextView) convertView.findViewById(R.id.campaignLink);
            linkText.setText("");
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
