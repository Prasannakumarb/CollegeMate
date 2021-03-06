package com.fyshadows.collegemate;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class NotificationHomeScreen extends ActionBarActivity  implements OnItemSelectedListener{
	 private String URL_getCourses = "http://fyshadows.com/CollegeMate/getcoursedetails.php";
	 Collegemate_DB db = new Collegemate_DB(this);
	 private String URL_getdepartments = "http://fyshadows.com/CollegeMate/getdepartmentdetails.php";
	
	 public String user_id=null;
	 public String doj=null;
	 String courseid= null; // this will contain "Fruit"
     String departmentid=null;
     String collegeid=null;
	 
     
	
	 private Spinner spinnerCourse;
	 private ArrayList<Coursedetails> courseList;
	 
	 private Spinner spinnerDepart;
	 private ArrayList<Departmentdetails> departList;
	    ProgressDialog pDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_home_screen);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		
		spinnerCourse= (Spinner) findViewById(R.id.spincourse);
		courseList = new ArrayList<Coursedetails>();
		new Getcourses().execute(); 
		
		spinnerCourse.setOnItemSelectedListener(this);
		
		
		spinnerDepart= (Spinner) findViewById(R.id.spindepart);
		departList = new ArrayList<Departmentdetails>();
		
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
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.student_collegeregistration, menu);
		return true;
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
					R.layout.fragment_student_collegeregistration, container,
					false);
			return rootView;
		}
	}
		/**
	     * Adding spinner data
	     * */
	  
	    
	    
	    
	    private void populateCourseSpinner() {
	        List<String> lables = new ArrayList<String>();
	        lables.add("ALL");
	        for (int i = 0; i < courseList.size(); i++) {
	            lables.add(courseList.get(i).getName());
	        }
	 
	        // Creating adapter for spinner
	        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
	                android.R.layout.simple_spinner_item, lables);
	 
	        // Drop down layout style - list view with radio button
	        spinnerAdapter
	                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	 
	        // attaching data adapter to spinner
	        spinnerCourse.setAdapter(spinnerAdapter);
	    }
	 
	    
	    private void populateDepartSpinner() {
	        List<String> lables = new ArrayList<String>();
	        lables.add("ALL");
	        for (int i = 0; i < departList.size(); i++) {
	            lables.add(departList.get(i).getName());
	        }
	 
	        // Creating adapter for spinner
	        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
	                android.R.layout.simple_spinner_item, lables);
	        
	        // Drop down layout style - list view with radio button
	        spinnerAdapter
	                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	 
	        // attaching data adapter to spinner
	        spinnerDepart.setAdapter(spinnerAdapter);
	    }
	    
	    //To get courses details
	    
	    private class Getcourses extends AsyncTask<Void, Void, Void> {
		   	 
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(NotificationHomeScreen.this);
	           pDialog.setMessage("Fetching Courses..");
	            pDialog.setCancelable(false);
	            pDialog.show();
	 
	        }
	 
	        @Override
	        protected Void doInBackground(Void... arg0) {
	            ServiceHandler jsonParser = new ServiceHandler();
	            String json = jsonParser.makeServiceCall(URL_getCourses, ServiceHandler.GET);
	 
	            Log.e("Response: ", "> " + json);
	 
	            if (json != null) {
	                try {
	                    JSONObject jsonObj = new JSONObject(json);
	                    if (jsonObj != null) {
	                        JSONArray categories = jsonObj
	                                .getJSONArray("courseName");                        
	 
	                        for (int i = 0; i < categories.length(); i++) {
	                            JSONObject catObj = (JSONObject) categories.get(i);
	                            Coursedetails cat = new Coursedetails(catObj.getInt("id"),
	                                    catObj.getString("name"));
	                            courseList.add(cat);
	                        }
	                    }
	 
	                } catch (JSONException e) {
	                    e.printStackTrace();
	                }
	 
	            } else {
	                Log.e("JSON Data", "Didn't receive any data from server!");
	            }
	 
	            return null;
	        }
	 
	        @Override
	        protected void onPostExecute(Void result) {
	            super.onPostExecute(result);
	            if (pDialog.isShowing())
	                pDialog.dismiss();
	            populateCourseSpinner();
	        }
	 
	    }

	    //To get department details
	    private class GetDepartment extends AsyncTask<Void, Void, Void> {
	   	 
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(NotificationHomeScreen.this);
	            pDialog.setMessage("Fetching Department..");
	            pDialog.setCancelable(false);
	           pDialog.show();
	 
	        }
	 
	        @Override
	        protected Void doInBackground(Void... arg0) {
	            ServiceHandler jsonParser = new ServiceHandler();
	            String json = jsonParser.makeServiceCall(URL_getdepartments, ServiceHandler.GET);
	 
	            Log.e("Response: ", "> " + json);
	 
	            if (json != null) {
	                try {
	                    JSONObject jsonObj = new JSONObject(json);
	                    if (jsonObj != null) {
	                        JSONArray categories = jsonObj
	                                .getJSONArray("DepartName");                        
	 
	                        for (int i = 0; i < categories.length(); i++) {
	                            JSONObject catObj = (JSONObject) categories.get(i);
	                            Departmentdetails cat = new Departmentdetails(catObj.getInt("id"),
	                                    catObj.getString("name"));
	                          
	                            departList.add(cat);
	                        }
	                    }
	 
	                } catch (JSONException e) {
	                    e.printStackTrace();
	                }
	 
	            } else {
	                Log.e("JSON Data", "Didn't receive any data from server!");
	            }
	 
	            return null;
	        }
	 
	        @Override
	        protected void onPostExecute(Void result) {
	            super.onPostExecute(result);
	            if (pDialog.isShowing())
	                pDialog.dismiss();
	            populateDepartSpinner();
	        }
	 
	    }
	    @Override
	    public void onItemSelected(AdapterView<?> parent, View view, int position,
	            long id) {
	    	if(parent.getItemAtPosition(position).toString().trim().equalsIgnoreCase("ALL"))
	    	{
	    		 departList.clear();
	    		populateDepartSpinner();
	    	}
	    	else
	    	{
	    		  URL_getdepartments = "http://fyshadows.com/CollegeMate/getdepartmentdetails.php";
	  	        URL_getdepartments=URL_getdepartments + "?course=" + parent.getItemAtPosition(position).toString().trim();
	  	        Log.i("URL",URL_getdepartments);
	  	        departList.clear();
	  	    	new GetDepartment().execute();
	    	}
	    }

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	    
		
		public void Getdetails(View view) {
	
			Spinner spinnercou = (Spinner)findViewById(R.id.spincourse);
			String course = spinnercou.getSelectedItem().toString();
			Spinner spinnerdepar = (Spinner)findViewById(R.id.spindepart);
			String depart = spinnerdepar.getSelectedItem().toString();
			Spinner spinneryear = (Spinner)findViewById(R.id.spinyear);
			String year = spinneryear.getSelectedItem().toString().substring(0, 1);
             String colid=db.getCollegeId();
             String message  = ((EditText)findViewById(R.id.editText_msg)).getText().toString();
try {
	message=URLEncoder.encode(message, "UTF-8");
} catch (UnsupportedEncodingException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
String userid=db.getCurrentuserId();
			String link="http://fyshadows.com/CollegeMate/send_notification.php?collegeid="+colid.trim()+"&course="+course.trim()+"&department="+depart.trim()+"&message="+message.trim()+"&userid="+userid.trim();
			Log.i("URL",link);
			HttpClient client = new DefaultHttpClient();
	        HttpGet request = new HttpGet();
	        try {
				request.setURI(new URI(link));
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	        HttpResponse response = null;
			try {
				response = client.execute(request);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
		}
	    

}
