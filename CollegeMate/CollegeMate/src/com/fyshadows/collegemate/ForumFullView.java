package com.fyshadows.collegemate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ForumFullView extends Activity  {

	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forum_full_view);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		String gotData[] = getIntent().getStringArrayExtra("sendData");
		String valTitle = gotData[0];
		String valCategory = gotData[1];
		String valDescription = gotData[2];
		String valTime = gotData[3];
		final String valDid = gotData[4];

		TextView discTitle = (TextView) findViewById(R.id.discTitle);
		TextView discCategory = (TextView) findViewById(R.id.discCategory);
		TextView discDescription = (TextView) findViewById(R.id.discDescription);
		TextView discTime = (TextView) findViewById(R.id.discTime);

		discTitle.setText(valTitle);
		discCategory.setText(valCategory);
		discDescription.setText(valDescription);
		discTime.setText(valTime);

		
		TextView myview = (TextView) findViewById(R.id.comSwipeText);
		
		myview.setOnTouchListener(new OnSwipeTouchListener(this) {
		    public void onSwipeLeft() {
		        String values[] = { valDid };
		        Intent ffv = new Intent(ForumFullView.this, ForumCommentsView.class);
		        ffv.putExtra("sendData", values);
				startActivity(ffv);
		    }

		public boolean onTouch(View v, MotionEvent event) {
		    return gestureDetector.onTouchEvent(event);
		}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forum_full_view, menu);
		return true;
	}

}
