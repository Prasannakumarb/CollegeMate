package com.fyshadows.collegemate;

import static com.fyshadows.collegemate.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.fyshadows.collegemate.CommonUtilities.EXTRA_MESSAGE;
import static com.fyshadows.collegemate.CommonUtilities.SENDER_ID;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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

import com.google.android.gcm.GCMRegistrar;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class StudentCollegeregistration extends ActionBarActivity implements OnItemSelectedListener{
	 private String URL_getColleges = "http://fyshadows.com/CollegeMate/getcollegedetails.php";
	 private String URL_getCourses = "http://fyshadows.com/CollegeMate/getcoursedetails.php";
	 Collegemate_DB db = new Collegemate_DB(this);
	 private String URL_getdepartments = "http://fyshadows.com/CollegeMate/getdepartmentdetails.php";
		AsyncTask<Void, Void, Void> mRegisterTask;
	 public String user_id=null;
	 public String doj=null;
	 String courseid= null; // this will contain "Fruit"
     String departmentid=null;
     String collegeid=null;
     private int serverResponseCode = 0;
     private ProgressDialog dialog = null;
	 
	 private Spinner spinnerCollege;
	 private ArrayList<Collegedetails> collegeList;
	 
	 private Spinner spinnerCourse;
	 private ArrayList<Coursedetails> courseList;
	 
	 private Spinner spinnerDepart;
	 private ArrayList<Departmentdetails> departList;
	    ProgressDialog pDialog;
	    
	 // registration Student
		String name = null;
		String phone = null;
		String email = null;
		String dob = null;
		String Selected_sex = null;
		String role = null;
		String Cityin = null;
		String regId=null;
		private String imagepath = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_collegeregistration);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		spinnerCollege= (Spinner) findViewById(R.id.spincollege);
		collegeList = new ArrayList<Collegedetails>();
		new GetColleges().execute(); 
		
		spinnerCourse= (Spinner) findViewById(R.id.spincourse);
		courseList = new ArrayList<Coursedetails>();
		new Getcourses().execute(); 
		
		spinnerCourse.setOnItemSelectedListener(this);
		
		
		spinnerDepart= (Spinner) findViewById(R.id.spindepart);
		departList = new ArrayList<Departmentdetails>();
	/*	
		// /GCM registration start

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
		 regId = GCMRegistrar.getRegistrationId(this);

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
						ServerUtilities.register(context, "nothing", "nothing",
								regId);
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
		// /GCM registration end
		*/
		
		Bundle bundle = getIntent().getExtras();
		name = bundle.getString("name");
		phone = bundle.getString("phone");
		email = bundle.getString("email");
		dob = bundle.getString("dob");
		Selected_sex = bundle.getString("Selected_sex");
		role = bundle.getString("role");
		Cityin = bundle.getString("Cityin");
		imagepath = bundle.getString("imagepath");
	
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.student_collegeregistration, menu);
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
					R.layout.fragment_student_collegeregistration, container,
					false);
			return rootView;
		}
	}
		/**
	     * Adding spinner data
	     * */
	    private void populateSpinner() {
	        List<String> lables = new ArrayList<String>();
	         
	      
	 
	        for (int i = 0; i < collegeList.size(); i++) {
	            lables.add(collegeList.get(i).getName());
	        }
	 
	        // Creating adapter for spinner
	        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
	                android.R.layout.simple_spinner_item, lables);
	 
	        // Drop down layout style - list view with radio button
	        spinnerAdapter
	                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	 
	        // attaching data adapter to spinner
	        spinnerCollege.setAdapter(spinnerAdapter);
	    }
	    
	    
	    
	    private void populateCourseSpinner() {
	        List<String> lables = new ArrayList<String>();

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
	    /**
	     * Async task to get all food categories
	     * */
	    private class GetColleges extends AsyncTask<Void, Void, Void> {
	 
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(StudentCollegeregistration.this);
	            pDialog.setMessage("Fetching Colleges..");
	            pDialog.setCancelable(false);
	            pDialog.show();
	 
	        }
	 
	        @Override
	        protected Void doInBackground(Void... arg0) {
	            ServiceHandler jsonParser = new ServiceHandler();
	            String json = jsonParser.makeServiceCall(URL_getColleges, ServiceHandler.GET);
	 
	            Log.e("Response: ", "> " + json);
	 
	            if (json != null) {
	                try {
	                    JSONObject jsonObj = new JSONObject(json);
	                    if (jsonObj != null) {
	                        JSONArray categories = jsonObj
	                                .getJSONArray("collegeName");                        
	 
	                        for (int i = 0; i < categories.length(); i++) {
	                            JSONObject catObj = (JSONObject) categories.get(i);
	                            Collegedetails cat = new Collegedetails(catObj.getInt("id"),
	                                    catObj.getString("name"));
	                            collegeList.add(cat);
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
	            //if (pDialog.isShowing())
	              //  pDialog.dismiss();
	            populateSpinner();
	        }
	 
	    }
	    
	    //To get courses details
	    
	    private class Getcourses extends AsyncTask<Void, Void, Void> {
		   	 
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	           // pDialog = new ProgressDialog(StudentCollegeregistration.this);
	          /// pDialog.setMessage("Fetching Courses..");
	           // pDialog.setCancelable(false);
	           // pDialog.show();
	 
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
	            //if (pDialog.isShowing())
	              //  pDialog.dismiss();
	            populateCourseSpinner();
	        }
	 
	    }

	    //To get department details
	    private class GetDepartment extends AsyncTask<Void, Void, Void> {
	   	 
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	           /// pDialog = new ProgressDialog(StudentCollegeregistration.this);
	            //pDialog.setMessage("Fetching Colleges..");
	            //pDialog.setCancelable(false);
	           // pDialog.show();
	 
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
	        URL_getdepartments = "http://fyshadows.com/CollegeMate/getdepartmentdetails.php";
	        URL_getdepartments=URL_getdepartments + "?course=" +parent.getItemAtPosition(position).toString().trim();
	        Log.i("URL",URL_getdepartments);
	        departList.clear();
	    	new GetDepartment().execute(); 
	    }

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
		// /GCM registration start

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

		// end gcm registration
	    
		
		public void register(View view) {
			
			pDialog = new ProgressDialog(StudentCollegeregistration.this);
            pDialog.setMessage("Registering..");
            pDialog.setCancelable(false);
            pDialog.show();
			// setting the file to disable back
			SharedPreferences settings = getSharedPreferences("MyPrefsFile", 0);
			settings.edit().putBoolean("my_first_time", false).commit();
			// end settings
			

			// admin registration starts
			try {
				regId = GCMRegistrar.getRegistrationId(this);
				String link = "http://fyshadows.com/CollegeMate/collegemate_adminregistration.php?name="
						+ name
						+ "&phone="
						+ phone
						+ "&email="
						+ email
						+ "&dob="
						+ dob
						+ "&sex="
						+ Selected_sex
						+ "&role="
						+ role
						+ "&Cityin=" + Cityin + "&regid=" + regId;
				Log.i("a", link);
				HttpClient client = new DefaultHttpClient();
				HttpGet request = new HttpGet();
				request.setURI(new URI(link));
				HttpResponse response = client.execute(request);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));

				StringBuffer sb = new StringBuffer("");
				String line = "";
				while ((line = in.readLine()) != null) {
					sb.append(line);
					break;
				}
				in.close();
				user_id = sb.toString();

			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(StudentCollegeregistration.this,
						"Registration Unsuccessfull", Toast.LENGTH_LONG).show();
			}

			// TO execute image upload in back ground
			new AsyncTaskEx().execute();
			// admin registration ends
			
			
			Spinner spinnercol = (Spinner)findViewById(R.id.spincollege);
			String college = spinnercol.getSelectedItem().toString();
			Spinner spinnercou = (Spinner)findViewById(R.id.spincourse);
			String course = spinnercou.getSelectedItem().toString();
			Spinner spinnerdepar = (Spinner)findViewById(R.id.spindepart);
			String depart = spinnerdepar.getSelectedItem().toString();
			
			doj   = ((EditText)findViewById(R.id.editText_doj)).getText().toString();
			String link="http://fyshadows.com/CollegeMate/Collegemate_Studentcollege_registration.php?course="+course.trim()+"&depart="+depart.trim()+"&col="+college.trim()+"&userid="+user_id.trim();
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
	        BufferedReader in = null;
			try {
				in = new BufferedReader
				        (new InputStreamReader(response.getEntity().getContent()));
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	                StringBuffer sb = new StringBuffer("");
	                String line="";
	                try {
						while ((line = in.readLine()) != null) {
						   sb.append(line);
						   break;
						 }
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                Log.i("aaa",line);
	                String[] separated = line.split(",");
	                
	                
	                 courseid= separated[0]; // this will contain "Fruit"
	                 departmentid=separated[1];
	                 collegeid=separated[2];
	                 db.updateStudentcollegedetails(new Masterusertable(collegeid,college,courseid,course,departmentid,depart));
	                 Intent i = new Intent(this, BasicHomeScreen.class);
	 		        startActivity(i); 
		}
		private class AsyncTaskEx extends AsyncTask<Void, Void, Void> {

			protected void onPreExecute() {

				Log.d("a", "into async pre execute");
				db.registeradmin(new Masterusertable(user_id, name, dob,
						Selected_sex, email, phone, Cityin, role));

			}

			
			 
			protected Void doInBackground(Void... arg0) {
				Log.d("a", "into background");
				Log.i("c", "into background");
				uploadFile(imagepath);
				return null;
			}
			
			protected void onPostExecute() {
				if (pDialog.isShowing())
	                pDialog.dismiss();
			}
		
		}

		public int uploadFile(String sourceFileUri) {

			String fileName = sourceFileUri;

			HttpURLConnection conn = null;
			DataOutputStream dos = null;
			String lineEnd = "\r\n";
			String twoHyphens = "--";
			String boundary = "*****";
			int bytesRead, bytesAvailable, bufferSize;
			byte[] buffer;
			int maxBufferSize = 1 * 1024 * 1024;
			File sourceFile = new File(sourceFileUri);

			if (!sourceFile.isFile()) {

				dialog.dismiss();

				Log.e("uploadFile", "Source File not exist :" + imagepath);

				runOnUiThread(new Runnable() {
					public void run() {

					}
				});

				return 0;

			} else {
				try {

					// open a URL connection to the
					FileInputStream fileInputStream = new FileInputStream(
							sourceFile);
					// URl which is used to upload the image and store the path in
					// DB
					String upLoadServerUri = "http://fyshadows.com/CollegeMate/Collegemate_Imageupload.php?title="
							+ user_id;
					URL url = new URL(upLoadServerUri);
					Log.i("a", upLoadServerUri);

					// Open a HTTP connection to the URL
					conn = (HttpURLConnection) url.openConnection();
					conn.setDoInput(true); // Allow Inputs
					conn.setDoOutput(true); // Allow Outputs
					conn.setUseCaches(false); // Don't use a Cached Copy
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Connection", "Keep-Alive");
					// conn.setChunkedStreamingMode(maxBufferSize);
					conn.setRequestProperty("ENCTYPE", "multipart/form-data");
					conn.setRequestProperty("userid", user_id);
					conn.setRequestProperty("Content-Type",
							"multipart/form-data;boundary=" + boundary);
					conn.setRequestProperty("uploaded_file", fileName);
					// conn.setRequestProperty("uploadname", user_id);
					Log.i("s", fileName);
					dos = new DataOutputStream(conn.getOutputStream());

					Log.i("c", "1");

					// to send image
					dos.writeBytes(twoHyphens + boundary + lineEnd);
					dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
							+ fileName + "\"" + lineEnd);
					dos.writeBytes(lineEnd);

					// sending text with image
					// dos.writeBytes(twoHyphens + boundary + lineEnd);
					// dos.writeBytes("Content-Disposition: form-data; name=\"title\""
					// + lineEnd);
					// dos.writeBytes(lineEnd);
					// dos.writeBytes("h");
					// dos.writeBytes(lineEnd);
					// dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

					Log.i("c", "2");
					// create a buffer of maximum size
					bytesAvailable = fileInputStream.available();

					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					buffer = new byte[bufferSize];
					Log.i("c", "3");
					// read file and write it into form...
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);

					while (bytesRead > 0) {

						dos.write(buffer, 0, bufferSize);
						bytesAvailable = fileInputStream.available();
						bufferSize = Math.min(bytesAvailable, maxBufferSize);
						bytesRead = fileInputStream.read(buffer, 0, bufferSize);

					}

					// send multipart form data necesssary after file data...
					dos.writeBytes(lineEnd);
					dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
					fileInputStream.close();

					// to send text
					Log.i("a", user_id);

					// Responses from the server (code and message)
					serverResponseCode = conn.getResponseCode();
					String serverResponseMessage = conn.getResponseMessage();

					Log.i("uploadFile", "HTTP Response is : "
							+ serverResponseMessage + ": " + serverResponseCode);

					if (serverResponseCode == 200) {

						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(StudentCollegeregistration.this,
										"File Upload Complete.", Toast.LENGTH_SHORT)
										.show();
							}
						});
					}

					// close the streams //

					dos.flush();
					dos.close();

				} catch (MalformedURLException ex) {

					dialog.dismiss();
					ex.printStackTrace();

					runOnUiThread(new Runnable() {
						public void run() {

							Toast.makeText(StudentCollegeregistration.this,
									"MalformedURLException", Toast.LENGTH_SHORT)
									.show();
						}
					});

					Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
				} catch (Exception e) {

					// dialog.dismiss();
					e.printStackTrace();

					runOnUiThread(new Runnable() {
						public void run() {

							Toast.makeText(StudentCollegeregistration.this,
									"Got Exception : see logcat ",
									Toast.LENGTH_SHORT).show();
						}
					});
					Log.e("Upload file to server Exception",
							"Exception : " + e.getMessage(), e);
				}
				// dialog.dismiss();
				return serverResponseCode;

			} // End else block
		}


}
