package com.fyshadows.collegemate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity implements FetchDataListener {
	public static String strTitle = "0", strCategory = "0",
			strDescription = "0", strTime = "0", strDid = "0";
	Collegemate_DB db = new Collegemate_DB(this);
	int likeStatus = 1;
	private ProgressDialog dialog;
	public static String usId=null;
	ImageButton imgButton;
	List<DiscussionList> items = new ArrayList<DiscussionList>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_forum_topics);
		usId = db.getCurrentuserId();
		initView();

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		/*
		 * On click listener to get values from DiscussionList class and send it
		 * to another activity when clicking on the list item
		 */

		ListView forumList = getListView();
		forumList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Get position of the clicked list item from adapter

				String dTitle, dCategory, dDescription, dTime, dDid;

				DiscussionList accessVar = ForumAdapter
						.getModelPosition(position);
				dTitle = accessVar.getTitle();
				dCategory = accessVar.getCategory();
				dDescription = accessVar.getDescription();
				dTime = accessVar.getTime();
				dDid = accessVar.getDiscId();

				/*
				 * Storing the forum values in string and passing it to another
				 * activity
				 */

				String values[] = { dTitle, dCategory, dDescription, dTime,
						dDid };
				Intent i = new Intent(MainActivity.this, ForumFullView.class);
				i.putExtra("sendData", values);
				startActivity(i);
			}
		});
	}

	private void initView() {
		// show progress dialog
		// getListView().setOnItemClickListener(this);
		dialog = ProgressDialog.show(this, "", "Loading...");

		String url = "http://fyshadows.com/CollegeMate/Collegemate_listForumData.php?currentUser_id="
				+ usId;
		Log.i("Fetch Url : ", url);
		FetchDataTask task = new FetchDataTask(this);
		task.execute(url);
	}

	@Override
	public void onFetchComplete(List<DiscussionList> data) {
		// dismiss the progress dialog
		if (dialog != null)
			dialog.dismiss();
		// create new adapter
		ListView forumList = getListView();
		// set the adapter to list
		if (forumList.getAdapter() == null) {
			final ForumAdapter adapter = new ForumAdapter(this, data);
			forumList.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		} else {
			((ForumAdapter) forumList.getAdapter()).refill(items);
		}
		// setListAdapter(adapter);
	}

	@Override
	public void onFetchFailure(String msg) {
		// dismiss the progress dialog
		if (dialog != null)
			dialog.dismiss();
		// show failure message
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	public void updateLikeTable(String dId) {
		try {
			String likeUrl = "http://fyshadows.com/CollegeMate/Collegemate_creatediscussionlike.php?discId="
					+ dId + "&userId=" + usId + "&like_status=" + likeStatus;
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(likeUrl));
			client.execute(request);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
