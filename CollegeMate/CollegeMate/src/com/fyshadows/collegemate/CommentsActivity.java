package com.fyshadows.collegemate;

import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

public class CommentsActivity extends ListActivity implements FetchCommentsListener{

	
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_forum_full_view);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
	}
	
	public void initCommentsFetch(String valDid) {
		Log.i("I'm Here", "Inside comments fetch");
		//dialog = ProgressDialog.show(this, "", "Fetching Comments...");
		String getCommentUrl = "http://fyshadows.com/CollegeMate/Collegemate_listDiscussionComments.php?currentDiscId="
				+ valDid;
		FetchComments task = new FetchComments(this);
		task.execute(getCommentUrl);
	}
	
	@Override
	public void onFetchCommentsComplete(List<CommentList> data) {
		// dismiss the progress dialog
//		if (dialog != null)
//			dialog.dismiss();
		// create new adapter
		CommentsAdapter adapter = new CommentsAdapter(this, data);
		// set the adapter to list
		setListAdapter(adapter);
	}

	@Override
	public void onFetchCommentsFailure(String msg) {
		// dismiss the progress dialog
//		if (dialog != null)
//			dialog.dismiss();
		// show failure message
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

}
