package edu.umd.cs.tabby;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class NotificationSetting extends AppCompatActivity {

    public final String PREFS_NAME = "SharedPrefs";
    public final static String NOTI_NAME = "Notifications";
    public final static int PREFS_MODE = 0;
    public final static int NOTI_MODE = 1;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences mNotifications;
    private String mCurrentDate;

    // instantiating strings for different topics
    private final static String REGISTER_EMAIL = "registerEmail";
    private final static String REGISTER_TEXT = "registerText";
    private final static String POLLING_EMAIL = "pollingEmail";
    private final static String POLLING_TEXT = "pollingText";
    private final static String CANDIDATE_EMAIL = "candidateEmail";
    private final static String CANDIDATE_TEXT = "candidateText";
    private final static String BALLOT_EMAIL = "ballotEmail";
    private final static String BALLOT_TEXT = "ballotText";
    private final static String NEWS_EMAIL = "newsEmail";
    private final static String NEWS_TEXT = "newsText";
    private final static String DEADLINES_EMAIL = "deadlinesEmail";
    private final static String DEADLINES_TEXT = "deadlinesText";
    private final static String EVENTS_EMAIL = "eventsEmail";
    private final static String EVENTS_TEXT = "eventsText";
    private final static String REGISTERING = "Registering to vote";
    private final static String POLLING = "Check your polling place";
    private final static String CANDIDATE = "Pick a candidate";
    private final static String BALLOT = "Read sample ballot";
    private final static String NEWS = "Donald Trump dropped out";
    private final static String DEADLINES = "Deadlines approaching";
    private final static String EVENTS = "Hillary Clinton rally tonight";

    private CheckBox mRegisterEmail;
    private CheckBox mRegisterText;
    private ImageButton mRegisterCalendar;

    private CheckBox mPollingEmail;
    private CheckBox mPollingText;
    private ImageButton mPollingCalendar;

    private CheckBox mCandidateEmail;
    private CheckBox mCandidateText;
    private ImageButton mCandidateCalendar;

    private CheckBox mBallotEmail;
    private CheckBox mBallotText;
    private ImageButton mBallotCalendar;

    private CheckBox mNewsEmail;
    private CheckBox mNewsText;
    private ImageButton mNewsCalendar;

    private CheckBox mDeadlinesEmail;
    private CheckBox mDeadlinesText;
    private ImageButton mDeadlinesCalendar;

    private CheckBox mEventsEmail;
    private CheckBox mEventsText;
    private ImageButton mEventsCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // set the date format for the current day
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        mCurrentDate = dateFormat.format(date);

        // instantiate the two sharedPreferences
        mSharedPreferences = getSharedPreferences(PREFS_NAME, PREFS_MODE);
        mNotifications = getSharedPreferences(NOTI_NAME, NOTI_MODE);

        // add click listeners for all the check boxes
        buildCheckBoxes();
    }

    // this method adds all click listeners for the check boxes
    private void buildCheckBoxes () {
        mRegisterEmail = (CheckBox) findViewById(R.id.registerEmail);
        setClickListenersForCheckBox(mRegisterEmail, REGISTER_EMAIL);

        mRegisterText = (CheckBox) findViewById(R.id.registerText);
        setClickListenersForCheckBox(mRegisterText, REGISTER_TEXT);

        mRegisterCalendar = (ImageButton) findViewById(R.id.registerCalendar);
        setClickListenersForImageButton(mRegisterCalendar, REGISTERING, "Register");

        mPollingEmail = (CheckBox) findViewById(R.id.pollingEmail);
        setClickListenersForCheckBox(mPollingEmail, POLLING_EMAIL);

        mPollingText = (CheckBox) findViewById(R.id.pollingText);
        setClickListenersForCheckBox(mPollingText, POLLING_TEXT);

        mPollingCalendar = (ImageButton) findViewById(R.id.pollingCalendar);
        setClickListenersForImageButton(mPollingCalendar, POLLING, "Polling");

        mCandidateEmail = (CheckBox) findViewById(R.id.candidateEmail);
        setClickListenersForCheckBox(mCandidateEmail, CANDIDATE_EMAIL);

        mCandidateText = (CheckBox) findViewById(R.id.candidateText);
        setClickListenersForCheckBox(mCandidateText, CANDIDATE_TEXT);

        mCandidateCalendar = (ImageButton) findViewById(R.id.candidateCalendar);
        setClickListenersForImageButton(mCandidateCalendar, CANDIDATE, "Candidate");

        mBallotEmail = (CheckBox) findViewById(R.id.ballotEmail);
        setClickListenersForCheckBox(mBallotEmail, BALLOT_EMAIL);

        mBallotText = (CheckBox) findViewById(R.id.ballotText);
        setClickListenersForCheckBox(mBallotText, BALLOT_TEXT);

        mBallotCalendar = (ImageButton) findViewById(R.id.ballotCalendar);
        setClickListenersForImageButton(mBallotCalendar, BALLOT, "Ballot");

        mNewsEmail = (CheckBox) findViewById(R.id.newsEmail);
        setClickListenersForCheckBox(mNewsEmail, NEWS_EMAIL);

        mNewsText = (CheckBox) findViewById(R.id.newsText);
        setClickListenersForCheckBox(mNewsText, NEWS_TEXT);

        mNewsCalendar = (ImageButton) findViewById(R.id.newsCalendar);
        setClickListenersForImageButton(mNewsCalendar, NEWS, "News");

        mDeadlinesEmail = (CheckBox) findViewById(R.id.deadlinesEmail);
        setClickListenersForCheckBox(mDeadlinesEmail, DEADLINES_EMAIL);

        mDeadlinesText = (CheckBox) findViewById(R.id.deadlinesText);
        setClickListenersForCheckBox(mDeadlinesText, DEADLINES_TEXT);

        mDeadlinesCalendar = (ImageButton) findViewById(R.id.deadlinesCalendar);
        setClickListenersForImageButton(mDeadlinesCalendar, DEADLINES, "Deadlines");

        mEventsEmail = (CheckBox) findViewById(R.id.eventsEmail);
        setClickListenersForCheckBox(mEventsEmail, EVENTS_EMAIL);

        mEventsText = (CheckBox) findViewById(R.id.eventsText);
        setClickListenersForCheckBox(mEventsText, EVENTS_TEXT);

        mEventsCalendar = (ImageButton) findViewById(R.id.eventsCalendar);
        setClickListenersForImageButton(mEventsCalendar, EVENTS, "Events");
    }

    // this method set the click listener for the date setting icon
    private void setClickListenersForImageButton (ImageButton imageButtons, final String title, final String category) {
        imageButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog(title, category);
            }
        });
    }

    // this method set the click listener for the check box
    private void setClickListenersForCheckBox (CheckBox checkBox, final String key) {
        if (mSharedPreferences.contains(key))
            checkBox.setChecked(true);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked())
                    mSharedPreferences.edit().putBoolean(key, true).commit();
                else
                    mSharedPreferences.edit().remove(key).commit();
            }
        });
    }

    // this method starts a dialog
    private void startDialog(final String title, final String category) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.calendar, (ViewGroup) findViewById(R.id.dialogLayout));
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);

        DatePicker datePicker = (DatePicker) layout.findViewById(R.id.datePicker);

        // instantiate the DatePicker
        datePicker.init(year, month, day, null);

        // build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(layout);

        // set up the click listeners for the two buttons
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                buildNotification(title, mCurrentDate, category);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // this function builds the notification pushed in the notification bar
    private void buildNotification (String title, String message, String category) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // instantiate notification builder
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setSmallIcon(R.drawable.ic_bookmark_outline_24dp);
        notification.setContentTitle(title);
        notification.setContentText(message);
        notification.setAutoCancel(true);

        // put notification information to the sharedPreferences
        mNotifications.edit().putString("title", title).commit();
        mNotifications.edit().putString("date", message).commit();
        mNotifications.edit().putString("category", category).commit();

        notificationManager.notify(9999, notification.build());
    }

    // this method populates the textview for candidate description
    public void displayCandidateSettingInfo(View view) {
        TextView title = (TextView) findViewById(R.id.descriptionTitle);
        TextView content = (TextView) findViewById(R.id.descriptionContent);

        title.setText("About Candidate Setting:");
        content.setText("Notify you with candidates' views on new issues");
    }

    // this method populates the textview for event description
    public void displayEventsSettingInfo(View view) {
        TextView title = (TextView) findViewById(R.id.descriptionTitle);
        TextView content = (TextView) findViewById(R.id.descriptionContent);

        title.setText("About Events Setting:");
        content.setText("Notify you with up-to-date news on elections");
    }
}
