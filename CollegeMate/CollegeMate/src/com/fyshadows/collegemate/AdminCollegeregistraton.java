package com.fyshadows.collegemate;

import static com.fyshadows.collegemate.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.fyshadows.collegemate.CommonUtilities.EXTRA_MESSAGE;
import static com.fyshadows.collegemate.CommonUtilities.SENDER_ID;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class AdminCollegeregistraton extends ActionBarActivity {
	AsyncTask<Void, Void, Void> mRegisterTask;
	String user_id = null;
	String collegename = null;
	String collegecity = null;
	String collegeemail = null;
	String collegewebsite = null;
	String collegephonenumber = null;
	String college_id = null;
	  ProgressDialog pDialog;
	 

	private int serverResponseCode = 0;
	private ProgressDialog dialog = null;
	private String imagepath = null;
	private static final int SELECT_PHOTO = 100;

	// registration admin
	String name = null;
	String phone = null;
	String email = null;
	String dob = null;
	String Selected_sex = null;
	String role = null;
	String Cityin = null;
	String regId=null;

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
		name = bundle.getString("name");
		phone = bundle.getString("phone");
		email = bundle.getString("email");
		dob = bundle.getString("dob");
		Selected_sex = bundle.getString("Selected_sex");
		role = bundle.getString("role");
		Cityin = bundle.getString("Cityin");
		imagepath = bundle.getString("imagepath");
		
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
				 * */
				
				getActionBar().setDisplayHomeAsUpEnabled(true);				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_collegeregistraton, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, RegistrationAdmin.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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
					R.layout.fragment_admin_collegeregistraton, container,
					false);
			return rootView;
		}
	}

	public void registercollege(View view) {

		
		pDialog = new ProgressDialog(AdminCollegeregistraton.this);
        pDialog.setMessage("Registering..");
        pDialog.setCancelable(false);
        pDialog.show();
        
		// Getting the user input and storing into variables
		collegename = ((EditText) findViewById(R.id.editText_collname))
				.getText().toString();
		collegecity = ((EditText) findViewById(R.id.editText_collcity))
				.getText().toString();
		collegephonenumber = ((EditText) findViewById(R.id.editText_collegephonenumber))
				.getText().toString();
		collegeemail = ((EditText) findViewById(R.id.editText_collegeemail))
				.getText().toString();
		collegewebsite = ((EditText) findViewById(R.id.editText_collegewebsite))
				.getText().toString();
		
		
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
			Toast.makeText(AdminCollegeregistraton.this,
					"Registration Unsuccessfull", Toast.LENGTH_LONG).show();
		}

		// TO execute image upload in back ground
		new AsyncTaskEx().execute();
		// admin registration ends

		try {
			String link = "http://fyshadows.com/CollegeMate/Collegemate_collegeregistration.php?collegename="
					+ collegename
					+ "&collegecity="
					+ collegecity
					+ "&collegephonenumber="
					+ collegephonenumber
					+ "&collegeemail="
					+ collegeemail
					+ "&collegewebsite="
					+ collegewebsite + "&user_id=" + user_id;
			Log.i("aaa", link);
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
			college_id = sb.toString();

			db.updatecollegedetails(new Masterusertable(college_id,
					collegename, user_id, ""));
			Log.d("a", Masterusertable._college_id);
			Log.d("a", Masterusertable._college_name);
			Toast.makeText(AdminCollegeregistraton.this,
					"Registration successfull" + sb.toString(),
					Toast.LENGTH_LONG).show();
			Intent i = new Intent(this, BasicHomeScreen.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(AdminCollegeregistraton.this,
					"Registration Unsuccessfull", Toast.LENGTH_LONG).show();
		}

	}
/*
	// /GCM registration start

	
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			// WakeLocker.acquire(getApplicationContext());

			/**
			 * Take appropriate action on this message depending upon your app
			 * requirement For now i am just displaying it on the screen
			

			// Showing received message

			Toast.makeText(getApplicationContext(),
					"New Message: " + newMessage, Toast.LENGTH_LONG).show();

			// Releasing wake lock
			// WakeLocker.release();
		}
	};
	
	*/

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

	// start admin registration
	// This task will be called to store the images in back ground
	private class AsyncTaskEx extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {

			Log.d("a", "into async pre execute");
			db.registeradmin(new Masterusertable(user_id, name, dob,
					Selected_sex, email, phone, Cityin, role));

		}
	
		/**
		 * The system calls this to perform work in a worker thread and delivers
		 * it the parameters given to AsyncTask.execute()
		 */
		protected Void doInBackground(Void... arg0) {
			Log.d("a", "into background");
			Log.i("c", "into background");
			uploadFile(imagepath);
			if (pDialog.isShowing())
                pDialog.dismiss();
			return null;
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
			

				// Responses from the server (code and message)
				serverResponseCode = conn.getResponseCode();
				String serverResponseMessage = conn.getResponseMessage();

				Log.i("uploadFile", "HTTP Response is : "
						+ serverResponseMessage + ": " + serverResponseCode);

				if (serverResponseCode == 200) {

					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(AdminCollegeregistraton.this,
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

						Toast.makeText(AdminCollegeregistraton.this,
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

						Toast.makeText(AdminCollegeregistraton.this,
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