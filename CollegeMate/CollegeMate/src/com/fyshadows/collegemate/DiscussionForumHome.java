package com.fyshadows.collegemate;

import java.net.URI;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class DiscussionForumHome extends ActionBarActivity {
	
	String discTitle = null;
	String discCategory = null;
	String discDescription = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discussion_forum_home);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.discussion_forum_home, menu);
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
					R.layout.fragment_discussion_forum_home, container, false);
			return rootView;
		}
	}
	
	public void createDiscussion(View view) {
		discTitle = ((EditText) findViewById(R.id.discussion_titleeditText))
				.getText().toString();
		discCategory = ((EditText) findViewById(R.id.discussion_categoryeditText))
				.getText().toString();
		discDescription = ((EditText) findViewById(R.id.discussion_descriptioneditText))
				.getText().toString();
		try {
			discTitle = URLEncoder.encode(discTitle, "UTF-8");
			discCategory = URLEncoder.encode(discCategory, "UTF_8");
			discDescription = URLEncoder.encode(discDescription, "UTF-8");
			String link = "http://fyshadows.com/CollegeMate/Collegemate_creatediscussion.php?discTitle="
					+ discTitle
					+ "&discCategory="
					+ discCategory
					+ "&discDescription="
					+ discDescription;

			Log.i("a", link);
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(link));
			HttpResponse response = client.execute(request);
			Toast.makeText(DiscussionForumHome.this,
					"Discussion Successfully Created", Toast.LENGTH_LONG).show();
			//Redirecting to discussions listing page
			Intent taketolist = new Intent(DiscussionForumHome.this, MainActivity.class);
			startActivity(taketolist);
			
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(DiscussionForumHome.this,
					"Error creating discussion", Toast.LENGTH_LONG).show();
		}
	}

}
