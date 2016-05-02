package edu.umd.cs.tabby;

import android.content.Intent;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ToDoItem implements Serializable {

	public static final String ITEM_SEP = System.getProperty("line.separator");

	public enum Status {
		NOTDONE, DONE
	};

	public final static String TITLE = "title";
	public final static String DATE = "date";
	public final static String FILENAME = "filename";

	public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.US);

	private String mTitle = new String();
	private Date mDate = new Date();

	ToDoItem(String title, Date date) {
		this.mTitle = title;
		this.mDate = date;
	}

	// Create a new ToDoItem from data packaged in an Intent

	ToDoItem(Intent intent) {

		mTitle = intent.getStringExtra(ToDoItem.TITLE);

		try {
			mDate = ToDoItem.FORMAT.parse(intent.getStringExtra(ToDoItem.DATE));
		} catch (ParseException e) {
			mDate = new Date();
		}
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}


	public Date getDate() {
		return mDate;
	}

	public void setDate(Date date) {
		mDate = date;
	}

	// Take a set of String data values and 
	// package them for transport in an Intent

	public static void packageIntent(Intent intent, String title, String date) {

		intent.putExtra(ToDoItem.TITLE, title);
		intent.putExtra(ToDoItem.DATE, date);
	
	}

	public String toString() {
		return mTitle + ITEM_SEP + ITEM_SEP + ITEM_SEP
				+ FORMAT.format(mDate);
	}

	public String toLog() {
		return "Title:" + mTitle + ITEM_SEP + ITEM_SEP + ITEM_SEP + "Date:"
				+ FORMAT.format(mDate) + "\n";
	}

}
