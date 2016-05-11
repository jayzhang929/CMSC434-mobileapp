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
import android.os.CountDownTimer;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * A tab fragment containing a simple view.
 */
public class SimpleTabFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {
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
    private static final int OCTOBER = 9;
    private static final int NOVEMBER = 10;
    public static final int LOCATION_PERMISSION = 1;

    private static int lastExpandedPosition = -1;

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

            // instantiate and populates data needed for list adapter
            final ArrayList<String> candidates = prepareCandidateData();
            HashMap<String, List<String>> childText = prepareChildTextData(candidates);
            HashMap<String, List<String>> linkText = prepareChildLinkTextData(candidates);

            ExpandableListView expandableListView = (ExpandableListView) rootView.findViewById(R.id.expLstView);

            // build the expandable list for the WHO page
            buildExpandableList(expandableListView, candidates, childText, linkText);

        } else if (getArguments().containsKey(WHAT_PAGE)) {
            rootView = inflater.inflate(R.layout.what, container, false);

            // instantiate and populates data needed for list adapter
            ArrayList<String> topics = prepareTopicsWhatPage();
            HashMap<String, List<String>> childText = prepareWhatPageChildTextData(topics);
            ExpandableListView expandableListView = (ExpandableListView) rootView.findViewById(R.id.whatPageExpLstView);

            // build the expandable list for the WHAT page
            buildExpandableList(expandableListView, topics, childText, null);

        } else if (getArguments().containsKey(WHERE_PAGE)) {
            rootView = inflater.inflate(R.layout.where, container, false);

            // instantiate GoogleApiClient
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
                    // check whether the app has the permission to access the user's current location
                    if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
                    } else {
                        mGoogleApiClient.connect();
                    }
                }
            });

        } else if (getArguments().containsKey(WHEN_PAGE)) {
            rootView = inflater.inflate(R.layout.when, container, false);
            final TextView registrationDeadlineTextView = (TextView) rootView.findViewById(R.id.registrationDeadline);
            final TextView electionDateTextView = (TextView) rootView.findViewById(R.id.electionDate);
            Calendar calendar = Calendar.getInstance();
            Long rightNowInMillis = calendar.getTimeInMillis();
            // register deadline
            calendar.set(2016, OCTOBER, 18);
            final Long registerDeadlineInMillis = calendar.getTimeInMillis();

            // set the CountDownTimer
            CountDownTimer registrationDeadlineCountDownTimer = new CountDownTimer((registerDeadlineInMillis - rightNowInMillis), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    setCountDownText(registrationDeadlineTextView, millisUntilFinished);
                }

                @Override
                public void onFinish() {

                }
            };
            registrationDeadlineCountDownTimer.start();

            // election date
            calendar.set(2016, NOVEMBER, 8);
            final Long electionDateInMillis = calendar.getTimeInMillis();

            // set the CountDownTimer
            CountDownTimer electionDateCountDownTimer = new CountDownTimer((electionDateInMillis - rightNowInMillis), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    setCountDownText(electionDateTextView, millisUntilFinished);
                }

                @Override
                public void onFinish() {

                }
            };
            electionDateCountDownTimer.start();

        } else if (getArguments().containsKey(HOW_PAGE)) {
            rootView = inflater.inflate(R.layout.how, container, false);

            // instantiate and populates data needed for list adapter
            ArrayList<String> topics = prepareTopicsHowPage();
            HashMap<String, List<String>> childText = prepareHowPageChildTextData(topics);
            ExpandableListView expandableListView = (ExpandableListView) rootView.findViewById(R.id.HowPageExpLstView);

            // build the expandable list for the HOW page
            buildExpandableList(expandableListView, topics, childText, null);

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
            // connect to GoogleApiClient once the app gains the user loation for the first time
            mGoogleApiClient.connect();
        }
    }

    // this method instantiates and populates the candidate data
    private ArrayList<String> prepareCandidateData(){
        ArrayList<String> candidates = new ArrayList<String>();
        candidates.add("Hillary Clinton (D)");
        candidates.add("Bernie Sanders (D)");
        candidates.add("Ted Cruz (R)");
        candidates.add("Donald Trump (R)");

        return candidates;
    }

    // this method instantiates and populates the child text data
    private HashMap<String, List<String>> prepareChildTextData(ArrayList<String> candidates){
        HashMap<String, List<String>> childText = new HashMap<String, List<String>>();
        for (String candidate : candidates) {
            ArrayList<String> description = new ArrayList<String>();
            switch (candidate) {
                case "Hillary Clinton (D)":
                    description.add(getResources().getString(R.string.clinton_description));
                    break;
                case "Bernie Sanders (D)":
                    description.add(getResources().getString(R.string.sanders_description));
                    break;
                case "Ted Cruz (R)":
                    description.add(getResources().getString(R.string.cruz_description));
                    break;
                case "Donald Trump (R)":
                    description.add(getResources().getString(R.string.trump_description));
                    break;
            }
            childText.put(candidate, description);
        }

        return childText;
    }

    // this method instantiates and populates the child link data
    private HashMap<String, List<String>> prepareChildLinkTextData(ArrayList<String> candidates){
        HashMap<String, List<String>> childText = new HashMap<String, List<String>>();
        for (String candidate : candidates) {
            ArrayList<String> description = new ArrayList<String>();
            switch (candidate) {
                case "Hillary Clinton (D)":
                    description.add(getResources().getString(R.string.clinton_campaign_link));
                    break;
                case "Bernie Sanders (D)":
                    description.add(getResources().getString(R.string.clinton_campaign_link));
                    break;
                case "Ted Cruz (R)":
                    description.add(getResources().getString(R.string.clinton_campaign_link));
                    break;
                case "Donald Trump (R)":
                    description.add(getResources().getString(R.string.clinton_campaign_link));
                    break;
            }
            childText.put(candidate, description);
        }

        return childText;
    }

    // this method builds the list view
    private void buildExpandableList(ExpandableListView expandableListView, ArrayList<String> group, HashMap<String, List<String>> children, HashMap<String, List<String>> links) {
        // instantiate the adapter
        ExpandableListAdapter adapter = new ExpandableListAdapter(getActivity(), group, children, links);
        expandableListView.setAdapter(adapter);

        // set the click listener for the group title
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (lastExpandedPosition != groupPosition) {
                    parent.expandGroup(groupPosition);
                    lastExpandedPosition = groupPosition;
                } else
                    parent.collapseGroup(lastExpandedPosition);

                return true;
            }
        });
    }

    // this method instantiates and populates the WHAT page data
    private ArrayList<String> prepareTopicsWhatPage() {
        ArrayList<String> topics = new ArrayList<String>();
        topics.add("Definition");
        topics.add("Presidential System");
        topics.add("Summary");
        return topics;
    }

    // this method instantiates and populates the WHAT page child text data
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

    // this method instantiates and populates the topics for HOW page
    private ArrayList<String> prepareTopicsHowPage() {
        ArrayList<String> topics = new ArrayList<>();
        topics.add("Determine Eligibility");
        topics.add("Register");
        topics.add("Absentee");
        topics.add("Go to the Polls");
        return topics;
    }

    // this method instantiates and populates the HOW page child text data
    private HashMap<String, List<String>> prepareHowPageChildTextData(ArrayList<String> topics) {
        HashMap<String, List<String>> childTextData = new HashMap<>();
        for (String topic : topics) {
            ArrayList<String> contents = new ArrayList<>();
            switch (topic) {
                case "Determine Eligibility":
                    contents.add(getResources().getString(R.string.how_eligibility));
                    break;
                case "Register":
                    contents.add(getResources().getString(R.string.how_register));
                    break;
                case "Absentee":
                    contents.add(getResources().getString(R.string.how_absentee));
                    break;
                case "Go to the Polls":
                    contents.add(getResources().getString(R.string.how_polls));
                    break;
            }
            childTextData.put(topic, contents);
        }
        return childTextData;
    }

    @Override
    public void onConnected(Bundle bundle) {
        // get the user's current location once the GoogleApiClient is connected
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (lastLocation != null) {
            Log.d("lat lon: ", String.valueOf(lastLocation.getLatitude()) + " " + String.valueOf(lastLocation.getLongitude()));
            Intent navigation = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr="
                                                                            + lastLocation.getLatitude() + ","
                                                                            + lastLocation.getLongitude() + "&daddr="
                                                                            + 39.0051128 + "," + -76.9656447));

            navigation.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

            startActivity(navigation);
        }

        // disconnect the GoogleApiClient once it finished
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    // this method set the text for the count down timer
    private void setCountDownText(TextView textView, Long millisUntilFinished) {
        millisUntilFinished /= 1000;
        Long seconds = millisUntilFinished % 60;
        millisUntilFinished /= 60;
        Long minutes = millisUntilFinished % 60;
        millisUntilFinished /= 60;
        Long hours = millisUntilFinished % 24;
        millisUntilFinished /= 24;
        Long days = millisUntilFinished;

        textView.setText(String.valueOf(days) + " days "
                + String.valueOf(hours) + " hours "
                + String.valueOf(minutes) + " minutes "
                + String.valueOf(seconds) + " seconds");
    }
}



