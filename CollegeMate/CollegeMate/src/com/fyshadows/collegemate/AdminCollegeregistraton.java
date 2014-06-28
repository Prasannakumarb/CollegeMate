package com.fyshadows.collegemate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class AdminCollegeregistraton extends ActionBarActivity {

	String user_id=null;
	String collegename=null;
	String collegecity = null;
	String collegeemail = null;
	String collegewebsite= null;
	String collegephonenumber=null;
	String college_id=null;
	 
	 Collegemate_DB db = new Collegemate_DB(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_collegeregistraton);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		 Bundle bundle = getIntent().getExtras();
		 user_id = bundle.getString("user_id"); 
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_collegeregistraton, menu);
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
					R.layout.fragment_admin_collegeregistraton, container,false);
			return rootView;
		}
	}
	
	 public void registercollege(View view) {
		//Getting the user input and storing into variables
			collegename   = ((EditText)findViewById(R.id.editText_collname)).getText().toString();
			collegecity = ((EditText)findViewById(R.id.editText_collcity)).getText().toString();
			collegephonenumber   = ((EditText)findViewById(R.id.editText_collegephonenumber)).getText().toString();
			collegeemail = ((EditText)findViewById(R.id.editText_collegeemail)).getText().toString();
			collegewebsite=((EditText)findViewById(R.id.editText_collegewebsite)).getText().toString();
			
			try {
		        String link =  "http://fyshadows.com/CollegeMate/Collegemate_collegeregistration.php?collegename="+collegename+"&collegecity="+collegecity+"&collegephonenumber="+collegephonenumber+"&collegeemail="+collegeemail+"&collegewebsite="+collegewebsite+"&user_id="+user_id;
		        Log.i("aaa",link);
		        HttpClient client = new DefaultHttpClient();
		        HttpGet request = new HttpGet();
		        request.setURI(new URI(link)); 
		        HttpResponse response = client.execute(request); 
		        BufferedReader in = new BufferedReader
		                (new InputStreamReader(response.getEntity().getContent()));

		                StringBuffer sb = new StringBuffer("");
		                String line="";
		                while ((line = in.readLine()) != null) {
		                   sb.append(line);
		                   break;
		                 }
		                 in.close();
		                 college_id=sb.toString();
		               
		                 db.updatecollegedetails(new Masterusertable(college_id,collegename,user_id,""));
		                 Log.d("a",Masterusertable._college_id);
		     			Log.d("a",Masterusertable._college_name);
		        Toast.makeText(AdminCollegeregistraton.this, "Registration successfull" + sb.toString(),Toast.LENGTH_LONG).show();
		        Intent i = new Intent(this, BasicHomeScreen.class);
		        startActivity(i); 
		    }catch(Exception e){
		        e.printStackTrace();
		    	Toast.makeText(AdminCollegeregistraton.this, "Registration Unsuccessfull",Toast.LENGTH_LONG).show();
		     }
			
			}
}