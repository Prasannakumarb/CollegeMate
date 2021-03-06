package com.fyshadows.collegemate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import java.util.List;

import android.view.MenuItem;
import android.view.View;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChatHomeScreen extends ListActivity {
	final List<Model> list = new ArrayList<Model>();
	Collegemate_DB db = new Collegemate_DB(this);
	String userid;
	String Username;
	String Picpath;
	String picname;
	int pathlength;
	private ProgressDialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatlist);
		new getdata().execute();
		ListView lv = getListView();
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Model model = MyCustomArrayAdapter.getModelPosition(position);

				userid = model.getUser_id();
				Username = model.getName();
				Picpath = model.getURL();

				// Storing the image of the friend into Sdcard
				// /to get the pic name
				pathlength = Picpath.length();
				picname = Picpath.substring(46, pathlength);

				// --------------------------------
				int msgcount = 0;
				String check = db.addfriend(new FriendInfoTable(userid,
						Username, picname, msgcount));
				Log.i("check", check);
				if (check.trim().equalsIgnoreCase("True")) {

					Toast.makeText(ChatHomeScreen.this, "Added as Friend",
							Toast.LENGTH_SHORT).show();
					Bundle bundle = new Bundle();
					bundle.putString("username", Username);
					bundle.putString("userid", userid);
					bundle.putString("PicPath", picname);
					new AsyncTaskEx().execute();
					Intent i = new Intent(ChatHomeScreen.this,
							MessageActivity.class);
					i.putExtras(bundle);
					startActivity(i);
				} else {
					Toast.makeText(ChatHomeScreen.this,
							"Something went wrong,Please try again",
							Toast.LENGTH_LONG).show();
				}

			}

		});
		// dismissDialog(progress);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, FriendList.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	getdata getdata;
	@Override
	protected void onResume() {
	    //...

		getdata = new getdata();
		getdata.execute();
		super.onResume();


	}

	@Override
	protected void onPause() {
	    //...
		getdata.cancel(true);
		super.onPause();
		}
	@Override
	public void onDestroy() {
		getdata.cancel(true);
		super.onDestroy();
	}

	private Model get(String s, String url, String user_id) {
		return new Model(s, url, user_id);
	}

	// This task will be called to store the images in back ground
	private class AsyncTaskEx extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
		}

		/**
		 * The system calls this to perform work in a worker thread and delivers
		 * it the parameters given to AsyncTask.execute()
		 */
		protected Void doInBackground(Void... arg0) {
			Log.i("picname baby", picname);
			URL url = null;
			try {
				url = new URL(Picpath);
			} catch (MalformedURLException e2) {

				e2.printStackTrace();
			}
			InputStream input = null;
			try {
				input = url.openStream();
			} catch (IOException e2) {

				e2.printStackTrace();
			}
			try {

				File storagePath = Environment.getExternalStorageDirectory();
				OutputStream output = new FileOutputStream(new File(storagePath
						+ "/collegemate/Profilepic/FriendsProfPic"
						+ File.separator, picname));
				try {
					int aReasonableSize = 50;
					byte[] buffer = new byte[aReasonableSize];
					int bytesRead = 0;
					while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
						output.write(buffer, 0, bytesRead);
					}
				} catch (IOException e) {

					e.printStackTrace();
				} finally {
					try {
						output.close();
					} catch (IOException e) {

						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} finally {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			return null;
		}
	}

	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	private ProgressDialog mProgressDialog;

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_DOWNLOAD_PROGRESS:
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setMessage("Getting Data..");
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			return mProgressDialog;
		default:
			return null;
		}
	}

	private class getdata extends AsyncTask<String, String, String> {
		@SuppressWarnings("deprecation")
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(DIALOG_DOWNLOAD_PROGRESS);
		}

		@Override
		protected String doInBackground(String... urls) {
			// Copy you logic to calculate progress and call
			publishProgress("" + progress);

			list.clear();
			//getActionBar().setDisplayHomeAsUpEnabled(true);
			final GetDataFromDB getvalues = new GetDataFromDB();
			String response = getvalues.getImageURLAndDesciptionFromDB();
			System.out.println("Response : This is  " + response);

			if (!response.equalsIgnoreCase("")) {
				if (!response.equalsIgnoreCase("error")) {
					String all[] = response.split("##");
					for (int k = 0; k < all.length; k++) {
						String urls_and_desc[] = all[k].split(",");
						list.add(ChatHomeScreen.this.get(urls_and_desc[1], urls_and_desc[0],
								urls_and_desc[2]));

					}
				}

			}

			return null;
		}

		protected void onProgressUpdate(String... progress) {
			publishProgress(progress);
			// mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String result) {
			ArrayAdapter<Model> adapter = new MyCustomArrayAdapter(
					ChatHomeScreen.this, list);
			setListAdapter(adapter);
			adapter.notifyDataSetChanged();
			dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
		}
	}

	public void gettingdata() {
		list.clear();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		final GetDataFromDB getvalues = new GetDataFromDB();
		String response = getvalues.getImageURLAndDesciptionFromDB();
		System.out.println("Response : This is  " + response);

		if (!response.equalsIgnoreCase("")) {
			if (!response.equalsIgnoreCase("error")) {
				String all[] = response.split("##");
				for (int k = 0; k < all.length; k++) {
					String urls_and_desc[] = all[k].split(",");
					list.add(get(urls_and_desc[1], urls_and_desc[0],
							urls_and_desc[2]));

				}
			}

		}
	}
}
