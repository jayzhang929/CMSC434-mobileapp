package edu.umd.cs.tabby;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A tab fragment containing a simple view.
 */
public class SimpleTabFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String TAB_NUMBER = "TAB_NUMBER";
    private static final String WHERE_PAGE = "OPEN_MAPS";
    private static final String WHAT_PAGE = "WHAT_PAGE";
    private static final String WHO_PAGE = "CANDIDATE_INFO";
    private static final String WHEN_PAGE = "WHEN PAGE";
    private static final String HOW_PAGE = "HOW PAGE";

    private static int lastExpandedPosition = -1;

    public SimpleTabFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SimpleTabFragment newInstance(int tabNumber) {
        SimpleTabFragment fragment = new SimpleTabFragment();
        Bundle args = new Bundle();
        args.putInt(TAB_NUMBER, tabNumber);
        if (tabNumber == 1)
          args.putInt(WHO_PAGE, 1);
        else if (tabNumber == 2)
            args.putInt(WHAT_PAGE, 2);
        else if (tabNumber == 3)
            args.putInt(WHERE_PAGE, 3);
        else if (tabNumber == 4)
            args.putInt(WHEN_PAGE, 4);
        else if (tabNumber == 5)
            args.putInt(HOW_PAGE, 4);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        if (getArguments().containsKey(WHO_PAGE)) {
            rootView = inflater.inflate(R.layout.who, container, false);
            final ArrayList<String> candidates = prepareCandidateData();
            HashMap<String, List<String>> childText = prepareChildTextData(candidates);

            ExpandableListAdapter adapter = new ExpandableListAdapter(getActivity(), candidates, childText);
            ExpandableListView expandableListView = (ExpandableListView) rootView.findViewById(R.id.expLstView);
            expandableListView.setAdapter(adapter);
            expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    if (lastExpandedPosition != -1)
                        parent.collapseGroup(lastExpandedPosition);

                    if (lastExpandedPosition != groupPosition) {
                        parent.expandGroup(groupPosition);
                        lastExpandedPosition = groupPosition;
                    } else
                        lastExpandedPosition = -1;

                    return true;
                }
            });

        } else if (getArguments().containsKey(WHAT_PAGE)) {
            rootView = inflater.inflate(R.layout.what, container, false);
            // ((TextView)rootView.findViewById(R.id.whatContent)).setMovementMethod(new ScrollingMovementMethod());
        } else if (getArguments().containsKey(WHERE_PAGE)) {
            rootView = inflater.inflate(R.layout.where, container, false);
            Button button = (Button) rootView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent navigation = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr="
                            + 38.990786 + ","
                            + -76.9388159 + "&daddr="
                            + 39.0051128 + "," + -76.9656447));
                    navigation.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                    startActivity(navigation);
                }
            });

        } else if (getArguments().containsKey(WHEN_PAGE)) {
            rootView = inflater.inflate(R.layout.when, container, false);
        }  else if (getArguments().containsKey(HOW_PAGE)) {
            rootView = inflater.inflate(R.layout.how, container, false);
        } else {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.tab_label);
            String num = Integer.toString((getArguments().getInt(TAB_NUMBER)));
            textView.setText("Text for Tab #" + num);
        }

        return rootView;
    }

    private ArrayList<String> prepareCandidateData(){
        ArrayList<String> candidates = new ArrayList<String>();
        candidates.add("Hillary Clinton");
        candidates.add("Bernie Sanders");
        candidates.add("Ted Cruz");
        candidates.add("Trump");

        return candidates;
    }

    private HashMap<String, List<String>> prepareChildTextData(ArrayList<String> candidates){
        HashMap<String, List<String>> childText = new HashMap<String, List<String>>();
        for (String candidate : candidates) {
            ArrayList<String> description = new ArrayList<String>();
            switch (candidate) {
                case "Hillary Clinton":
                    description.add(getResources().getString(R.string.clinton_description));
                    break;
                case "Bernie Sanders":
                    description.add(getResources().getString(R.string.sanders_description));
                    break;
                case "Ted Cruz":
                    description.add(getResources().getString(R.string.cruz_description));
                    break;
                case "Trump":
                    description.add(getResources().getString(R.string.trump_description));
                    break;
            }
            childText.put(candidate, description);
        }

        return childText;
    }
}



