package com.fyshadows.collegemate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.util.Log;

public class GetDataFromDB {

	 
	public String getImageURLAndDesciptionFromDB() {
		if (android.os.Build.VERSION.SDK_INT > 9) {
	           StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	           StrictMode.setThreadPolicy(policy);
	         }
        try {
        	// Log.i("a","inot DB");
            //HttpPost httppost;
       	 //Log.i("a","1");
           // HttpClient httpclient;
            //Log.i("a","2");
            //httpclient = new DefaultHttpClient();
            //Log.i("a","3");
           // httppost = new HttpPost(
             //      "http://fyshadows.com/CollegeMate/Chat/getImageUrlsAndDescription.php"); // change this to your URL.....
         
            //ResponseHandler<String> responseHandler = new BasicResponseHandler();
           
           //final String response = httpclient.execute(httppost, responseHandler);
            String link =  "http://fyshadows.com/CollegeMate/Chat/getImageUrlsAndDescription.php";
            HttpClient client = new DefaultHttpClient();
	        HttpGet request = new HttpGet();
	       request.setURI(new URI(link)); 
	        Log.i("a","4");
            HttpResponse response = client.execute(request); 
            Log.i("a","5");
	        BufferedReader in = new BufferedReader
	                (new InputStreamReader(response.getEntity().getContent()));

	                StringBuffer sb = new StringBuffer("");
	                String line="";
	                while ((line = in.readLine()) != null) {
	                   sb.append(line);
	                   break;
	                 }
            Log.i("a","got values from DB");
            Log.i("a",line);
            return line;
            
           
 
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
            return "error";
        }
    }
	
	
	
}
