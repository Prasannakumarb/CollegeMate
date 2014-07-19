package com.fyshadows.collegemate;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class BasicHomeScreen extends ActionBarActivity {
	Collegemate_DB db = new Collegemate_DB(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic_home_screen);

	if	(db.getCurrentuserId().isEmpty())
	{
		Intent i = new Intent(this, Common_Entry.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	}
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.basic_home_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_basic_home_screen, container, false);
			return rootView;
		}
	}

	public void Chatwindow(View view) {
		Intent i = new Intent(this, FriendList.class);
		startActivity(i);
	}

	public void discussion_forum_view(View view) {

		Intent i = new Intent(this, DiscussionForumHome.class);
		// Create the bundle
		Bundle bundle = new Bundle();
		// Add your data to bundle
		//bundle.putString("role", "student");
		// Add the bundle to the intent
		i.putExtras(bundle);

		// Fire that second activity
		startActivity(i);

	}
	public void discussion_forum_list(View view) {

		Intent i = new Intent(this, MainActivity.class);
		// Create the bundle
		Bundle bundle = new Bundle();
		// Add your data to bundle
		//bundle.putString("role", "student");
		// Add the bundle to the intent
		i.putExtras(bundle);

		// Fire that second activity
		startActivity(i);

	}
	
	public void sendnotification(View view) {

		Intent i = new Intent(this, NotificationHomeScreen.class);
		// Fire that second activity
		startActivity(i);
	}
	
	
	public void viewnotification(View view) {

		Intent i = new Intent(this, ViewNotification.class);
		// Fire that second activity
		startActivity(i);
	}
}
