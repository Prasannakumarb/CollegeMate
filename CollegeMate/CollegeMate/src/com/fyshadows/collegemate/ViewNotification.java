package com.fyshadows.collegemate;

import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

public class ViewNotification extends ListActivity {

	ArrayList<notificationtable> notification;
	notificationcustomadapter adapter;
	EditText text;

	Collegemate_DB db = new Collegemate_DB(this);
	List<notificationtable> list = new ArrayList<notificationtable>();
	final Handler handler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		

		this.setTitle("Notification's");

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		Log.i("a", "calling get chat");
		getnotification();

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				getnotification();

				handler.postDelayed(this, 600 * 50);
			}
		}, 30 * 50);
		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, BasicHomeScreen.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onPause() {
		handler.removeCallbacksAndMessages(null);
		super.onPause();
	}

	@Override
	public void onDestroy() {
		handler.removeCallbacksAndMessages(null);
		super.onDestroy();
	}



	

	public void getnotification() {
		list.clear();

		list = db.Getnotitification();
		if (!list.isEmpty()) {
			Log.i("asdas","listnot empty");
			adapter = new notificationcustomadapter(this, list);
			setListAdapter(adapter);
			adapter.notifyDataSetChanged();
			Log.i("asdas","called");
		}

	}

	/*
	// This task will be called to store the images in back ground
	private class AsyncTaskEx extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {

			Log.i("into async", "into async");
		}

		*
		 * The system calls this to perform work in a worker thread and delivers
		 * it the parameters given to AsyncTask.execute()
		 
		protected Void doInBackground(Void... arg0) {
		
		}
	}
*/
}
