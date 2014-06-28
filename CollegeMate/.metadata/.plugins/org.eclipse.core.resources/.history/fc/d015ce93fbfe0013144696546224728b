package com.fyshadows.collegemate;

import java.io.File;
import static com.fyshadows.collegemate.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.fyshadows.collegemate.CommonUtilities.EXTRA_MESSAGE;
import static com.fyshadows.collegemate.CommonUtilities.SENDER_ID;

import com.google.android.gcm.GCMRegistrar;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Common_Entry extends ActionBarActivity {
	AsyncTask<Void, Void, Void> mRegisterTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common__entry);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();

			final String PREFS_NAME = "MyPrefsFile";

			SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

			if (settings.getBoolean("my_first_time", true)) {
				File folder = new File(
						Environment.getExternalStorageDirectory()
								+ "/collegemate");
				boolean success = true;
				if (!folder.exists()) {
					success = folder.mkdir();
				}
				if (success) {
					Log.i("a", "folder created");
				} else {
					Log.i("a", "folder creation failed");
				}
				File folder1 = new File(
						Environment.getExternalStorageDirectory()
								+ "/collegemate/Profilepic");
				boolean success1 = true;
				if (!folder1.exists()) {
					success1 = folder1.mkdir();
				}
				if (success1) {
					Log.i("a", "folder2 created");
				} else {
					Log.i("a", "folder2 creation failed");
				}
				File folder3 = new File(
						Environment.getExternalStorageDirectory()
								+ "/collegemate/Profilepic/FriendsProfPic");
				boolean success3 = true;
				if (!folder3.exists()) {
					success3 = folder3.mkdir();
				}
				if (success3) {
					Log.i("a", "folder3 created");
				} else {
					Log.i("a", "folder3 creation failed");
				}
				settings.edit().putBoolean("my_first_time", false).commit();
			} else {
				Intent i = new Intent(this, BasicHomeScreen.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.common__entry, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_common__entry,
					container, false);
			return rootView;
		}
	}

	public void Admin_selected(View view) {

		Intent i = new Intent(this, RegistrationAdmin.class);
		// Create the bundle
		Bundle bundle = new Bundle();
		// Add your data to bundle
		bundle.putString("role", "admin");
		// Add the bundle to the intent
		i.putExtras(bundle);

		// Fire that second activity
		startActivity(i);

		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);

		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		Log.i("a", "into checking manifest");
		// GCMRegistrar.checkManifest(this);

		Log.i("a", "going to regiter receiver");

		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				DISPLAY_MESSAGE_ACTION));
		Log.i("a", "going to register AND GET REgistration id");
		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);

		// Check if regid already presents
		if (regId.equals("")) {
			// Registration is not present, register now with GCM
			GCMRegistrar.register(this, SENDER_ID);
		} else {
			// Device is already registered on GCM
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.
				Toast.makeText(getApplicationContext(),
						"Already registered with GCM", Toast.LENGTH_LONG)
						.show();
			} else {
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						// Register on our server
						// On server creates a new user
						ServerUtilities.register(context, "nothing",
								"nothing", regId);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
			}
		}
	}

	/**
	 * Receiving push messages
	 * */
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			// WakeLocker.acquire(getApplicationContext());

			/**
			 * Take appropriate action on this message depending upon your app
			 * requirement For now i am just displaying it on the screen
			 * */

			// Showing received message

			Toast.makeText(getApplicationContext(),
					"New Message: " + newMessage, Toast.LENGTH_LONG).show();

			// Releasing wake lock
			// WakeLocker.release();
		}
	};

	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			// mRegisterTask.cancel(true);
		}
		try {
			// unregisterReceiver(mHandleMessageReceiver);
			// GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			// Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}


	
	public void Student_selected(View view) {

		Intent i = new Intent(this, RegistrationStudent.class);
		// Create the bundle
		Bundle bundle = new Bundle();
		// Add your data to bundle
		bundle.putString("role", "student");
		// Add the bundle to the intent
		i.putExtras(bundle);

		// Fire that second activity
		startActivity(i);

		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);

		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		Log.i("a", "into checking manifest");
		// GCMRegistrar.checkManifest(this);

		Log.i("a", "going to regiter receiver");

		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				DISPLAY_MESSAGE_ACTION));
		Log.i("a", "going to register AND GET REgistration id");
		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);

		// Check if regid already presents
		if (regId.equals("")) {
			// Registration is not present, register now with GCM
			GCMRegistrar.register(this, SENDER_ID);
		} else {
			// Device is already registered on GCM
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.
				Toast.makeText(getApplicationContext(),
						"Already registered with GCM", Toast.LENGTH_LONG)
						.show();
			} else {
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						// Register on our server
						// On server creates a new user
						ServerUtilities.register(context, "nothing",
								"nothing", regId);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
			}
		}
	}


	public void dummy_selected(View view) {

		Intent i = new Intent(this, StudentCollegeregistration.class);
		// Fire that second activity
		startActivity(i);
	}

}
