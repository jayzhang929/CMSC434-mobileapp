package edu.umd.cs.tabby;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.tab_label);
        //TextView textView = (TextView) rootView;
        String num = Integer.toString((getArguments().getInt(TAB_NUMBER)));
        textView.setText("Text for Tab #" + num);
        return rootView;
    }
}



