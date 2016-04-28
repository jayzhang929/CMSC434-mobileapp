package edu.umd.cs.tabby;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A tab fragment containing a simple view.
 */
public class SimpleTabFragment extends Fragment implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {
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
    public static final int LOCATION_PERMISSION = 1;

    private static int lastExpandedPosition = -1;

    private LocationManager mLocationManager;
    private GoogleApiClient mGoogleApiClient;

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
            ExpandableListView expandableListView = (ExpandableListView) rootView.findViewById(R.id.expLstView);

            buildExpandableList(expandableListView, candidates, childText);

        } else if (getArguments().containsKey(WHAT_PAGE)) {
            rootView = inflater.inflate(R.layout.what, container, false);
            ArrayList<String> topics = prepareTopicsWhatPage();
            HashMap<String, List<String>> childText = prepareWhatPageChildTextData(topics);
            ExpandableListView expandableListView = (ExpandableListView) rootView.findViewById(R.id.whatPageExpLstView);

            buildExpandableList(expandableListView, topics, childText);

        } else if (getArguments().containsKey(WHERE_PAGE)) {
            rootView = inflater.inflate(R.layout.where, container, false);

            if (mGoogleApiClient == null) {
                mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                                    .addConnectionCallbacks(this)
                                    .addOnConnectionFailedListener(this)
                                    .addApi(LocationServices.API)
                                    .build();
            }

            Button button = (Button) rootView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
                    } else {
                        getCurrentLocation();
                    }
                }
            });

        } else if (getArguments().containsKey(WHEN_PAGE)) {
            rootView = inflater.inflate(R.layout.when, container, false);
        } else if (getArguments().containsKey(HOW_PAGE)) {
            rootView = inflater.inflate(R.layout.how, container, false);
            ArrayList<String> topics = prepareTopicsHowPage();
            HashMap<String, List<String>> childText = prepareHowPageChildTextData(topics);
            ExpandableListView expandableListView = (ExpandableListView) rootView.findViewById(R.id.HowPageExpLstView);

            buildExpandableList(expandableListView, topics, childText);

        } else {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.tab_label);
            String num = Integer.toString((getArguments().getInt(TAB_NUMBER)));
            textView.setText("Text for Tab #" + num);
        }

        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }
    }

    private void getCurrentLocation () {
        mGoogleApiClient.connect();
        if (mLocationManager == null)
            mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Log.d("getCurrentLocation: ", "getting ...");
        mLocationManager.requestLocationUpdates(mLocationManager.GPS_PROVIDER, 0, 0, this);
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

    private void buildExpandableList(ExpandableListView expandableListView, ArrayList<String> group, HashMap<String, List<String>> children) {
        ExpandableListAdapter adapter = new ExpandableListAdapter(getActivity(), group, children);
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
    }

    private ArrayList<String> prepareTopicsWhatPage() {
        ArrayList<String> topics = new ArrayList<String>();
        topics.add("Definition");
        topics.add("Presidential System");
        topics.add("Summary");
        return topics;
    }

    private HashMap<String, List<String>> prepareWhatPageChildTextData(ArrayList<String> topics) {
        HashMap<String, List<String>> childTextData = new HashMap<>();
        for (String topic : topics) {
            ArrayList<String> contents = new ArrayList<>();
            switch (topic) {
                case "Definition":
                    contents.add(getResources().getString(R.string.what_definition));
                    break;
                case "Presidential System":
                    contents.add(getResources().getString(R.string.what_presidential_system));
                    break;
                case "Summary":
                    contents.add(getResources().getString(R.string.what_information));
                    break;
            }
            childTextData.put(topic, contents);
        }
        return childTextData;
    }

    private ArrayList<String> prepareTopicsHowPage() {
        ArrayList<String> topics = new ArrayList<>();
        topics.add("Definition");
        topics.add("Candidate Process");
        topics.add("Election Process");
        return topics;
    }

    private HashMap<String, List<String>> prepareHowPageChildTextData(ArrayList<String> topics) {
        HashMap<String, List<String>> childTextData = new HashMap<>();
        for (String topic : topics) {
            ArrayList<String> contents = new ArrayList<>();
            switch (topic) {
                case "Definition":
                    contents.add(getResources().getString(R.string.how_definition));
                    break;
                case "Candidate Process":
                    contents.add(getResources().getString(R.string.how_candidate_process));
                    break;
                case "Election Process":
                    contents.add(getResources().getString(R.string.how_information));
                    break;
            }
            childTextData.put(topic, contents);
        }
        return childTextData;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("current loc: ", String.valueOf(location.getLatitude()) + " " + String.valueOf(location.getLongitude()));
        Intent navigation = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr="
                + location.getLatitude() + ","
                + location.getLongitude() + "&daddr="
                + 39.0051128 + "," + -76.9656447));
        navigation.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

        startActivity(navigation);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}



