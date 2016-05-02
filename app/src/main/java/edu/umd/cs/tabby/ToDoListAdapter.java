package edu.umd.cs.tabby;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ToDoListAdapter extends BaseAdapter {

	private static final int ADD_TODO_ITEM_REQUEST = 0;

	private final List<ToDoItem> mItems = new ArrayList<ToDoItem>();
	private final Context mContext;

	private static final String TAG = "Lab-UserInterface";

	public ToDoListAdapter(Context context) {

		mContext = context;

	}

	// Add a ToDoItem to the adapter
	// Notify observers that the data set has changed

	public void add(ToDoItem item) {

		mItems.add(item);
		notifyDataSetChanged();

	}

	// Clears the list adapter of all items.

	public void clear() {

		mItems.clear();
		notifyDataSetChanged();

	}

	// Returns the number of ToDoItems

	@Override
	public int getCount() {

		return mItems.size();

	}

	// Retrieve the number of ToDoItems

	@Override
	public Object getItem(int pos) {

		return mItems.get(pos);

	}

	// Get the ID for the ToDoItem
	// In this case it's just the position

	@Override
	public long getItemId(int pos) {

		return pos;

	}

	// Create a View for the ToDoItem at specified position
	// Remember to check whether convertView holds an already allocated View
	// before created a new View.
	// Consider using the ViewHolder pattern to make scrolling more efficient
	// See: http://developer.android.com/training/improving-layouts/smooth-scrolling.html

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {

		final ToDoItem toDoItem = (ToDoItem) getItem(position);;


		// from notification_listion_list.xml
		final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemLayout = inflater.inflate(R.layout.notification_list, parent, false);
		// Fill in specific ToDoItem data
		// Remember that the data that goes in this View
		// corresponds to the user interface elements defined
		// in the layout file

		final TextView titleView = (TextView) itemLayout.findViewById(R.id.titleView);
		titleView.setText(toDoItem.getTitle());

		Button delete_button = (Button)itemLayout.findViewById(R.id.delete_button);
		delete_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mItems.remove(position); //or some other task
				notifyDataSetChanged();
			}
		});

		Button edit_button = (Button)itemLayout.findViewById(R.id.edit_button);
		edit_button.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {


				Intent intent = new Intent(mContext.getApplicationContext(), AddToDoActivity.class);
				intent.putExtra("Item", mItems.get(position));
				//mContext.startActivityForResult(intent, ToDoManagerActivity.EDIT_TODO_ITEM_REQUEST);

			}
		});




		final TextView dateView = (TextView) itemLayout.findViewById(R.id.dateView);
		dateView.setText(ToDoItem.FORMAT.format(toDoItem.getDate()));

		return itemLayout;

	}
}
