package edu.umd.cs.tabby;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A tab fragment containing a simple view.
 */
public class SimpleTabFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String TAB_NUMBER = "TAB_NUMBER";
    private static final String OPEN_MAPS = "OPEN_MAPS";

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
        if (tabNumber == 3)
            args.putInt(OPEN_MAPS, 1);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        if (getArguments().containsKey(OPEN_MAPS)) {
            rootView = inflater.inflate(R.layout.polling_location, container, false);
            Button button = (Button) rootView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent navigation = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr="
                            + 38.9841374 + ","
                            + -76.9447599 + "&daddr="
                            + 39.4405023 + "," + -77.4542221));
                    navigation.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(navigation);
                }
            });

        } else {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.tab_label);
            String num = Integer.toString((getArguments().getInt(TAB_NUMBER)));
            textView.setText("Text for Tab #" + num);
        }

        return rootView;
    }
}



