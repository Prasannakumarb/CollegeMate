package com.fyshadows.collegemate;

import java.net.URI;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ForumCommentsView extends ListActivity implements FetchCommentsListener{
	Collegemate_DB db = new Collegemate_DB(this);
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forum_comments_view);
		
		String gotData[] = getIntent().getStringArrayExtra("sendData");
		final String valDid = gotData[0];
		initCommentsFetch(valDid);
		
		Button commentButton = (Button) findViewById(R.id.commentButton);
		commentButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				pushDataComment(valDid);
			}

		});
		
		ListView listViewFriends = getListView();
		listViewFriends.setEmptyView(findViewById(R.id.empty));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forum_comments_view, menu);
		return true;
	}
	
	private void pushDataComment(String valDid) {
		String usID1 = db.getCurrentuserId();
		String commentDesc;
		commentDesc = ((EditText) findViewById(R.id.commentEditText)).getText()
				.toString();
		try {
			commentDesc = URLEncoder.encode(commentDesc, "UTF-8");
			String commentUrl = "http://fyshadows.com/CollegeMate/Collegemate_creatediscussioncomment.php?discId="
					+ valDid
					+ "&userId="
					+ usID1
					+ "&comment_desc="
					+ commentDesc;

			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(commentUrl));
			client.execute(request);
			Toast.makeText(getApplicationContext(), "Comment Submitted",
					Toast.LENGTH_LONG).show();
			((EditText) findViewById(R.id.commentEditText)).setText("");
			initCommentsFetch(valDid);

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "Error Submitting Comment",
					Toast.LENGTH_LONG).show();
		}

	}

	public void initCommentsFetch(String valDid) {
		dialog = ProgressDialog.show(this, "", "");
		
		String getCommentUrl = "http://fyshadows.com/CollegeMate/Collegemate_listDiscussionComments.php?currentDiscId="
				+ valDid;
		FetchComments task = new FetchComments(this);
		task.execute(getCommentUrl);
	}

	@Override
	public void onFetchCommentsComplete(List<CommentList> data) {
		// dismiss the progress dialog
		if (dialog != null)
			dialog.dismiss();
		// create new adapter
		CommentsAdapter adapter = new CommentsAdapter(this, data);
		// set the adapter to list
		setListAdapter(adapter);
	}

	@Override
	public void onFetchCommentsFailure(String msg) {
		// dismiss the progress dialog
		 if (dialog != null)
		 dialog.dismiss();
		// show failure message
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

}
