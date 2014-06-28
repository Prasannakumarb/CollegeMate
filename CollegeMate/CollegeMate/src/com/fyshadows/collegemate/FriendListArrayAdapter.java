package com.fyshadows.collegemate;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fyshadows.collegemate.FriendListArrayAdapter.ViewHolder;

public class FriendListArrayAdapter  extends ArrayAdapter<FriendInfoTable> {

	 private final Activity context;
	    private  static List<FriendInfoTable> list=null;
	 
	    public FriendListArrayAdapter
	    (Activity context, List<FriendInfoTable> list) {
	        super(context, R.layout.listlayout_friendlist, list);
	        this.context = context;
	        this.list = list;
	    }
	    @Override
	    public int getCount() {
	    	Log.i("a","a"+list.size());
	        return list.size();
	    }
	    
	    public static  FriendInfoTable getFriendInfoTablePosition(int position)
	    {
	    	
	        return list.get(position);
	    }
	    
	   
	 
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View view = null;
	      
	        if (convertView == null) {
	        	
	            LayoutInflater inflator = context.getLayoutInflater();
	            view = inflator.inflate(R.layout.listlayout_friendlist, null);
	            final ViewHolder viewHolder = new ViewHolder();
	            viewHolder.text = (TextView) view.findViewById(R.id.label);
	            viewHolder.text.setTextColor(Color.BLACK);
	            viewHolder.text1 = (TextView) view.findViewById(R.id.msgcount);
	            viewHolder.text1.setTextColor(Color.BLACK);
	            viewHolder.image = (ImageView) view.findViewById(R.id.image);
	            viewHolder.image.setVisibility(View.GONE);
	            viewHolder.pb = (ProgressBar) view.findViewById(R.id.progressBar1);
	            view.setTag(viewHolder);
	        } else {
	            view = convertView;
	        }
	        ViewHolder holder = (ViewHolder) view.getTag();
	        Log.i("f", "I am here");
	        Log.i("f", list.get(position).getuserpicpath());
	        
	        holder.text.setText(list.get(position).getusername());
	        holder.image.setTag(list.get(position).getuserpicpath());
	        Log.i("cpunt in ",String.valueOf(list.get(position).getmessagecount()));
	        holder.text1.setText(String.valueOf(list.get(position).getmessagecount()));
	        holder.image.setId(position);
	        //PbAndImage pb_and_image = new PbAndImage();
	       // pb_and_image.setImg(holder.image);
	        //pb_and_image.setPb(holder.pb);
	        //new DownloadImageTask().execute(pb_and_image);
	        Bitmap bitmap = null;
	        File file = new File( 
	                Environment.getExternalStorageDirectory() + "/collegemate/Profilepic/FriendsProfPic/" + list.get(position).getuserpicpath());

	        if(file.exists()){
	        	Log.i("file exist","file exist");
	            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
	            if(bitmap != null)
	            {
	            bitmap = getResizedBitmap(bitmap, 50, 50);
	            }
	        }
              holder.image.setImageBitmap(bitmap);
              holder.pb.setVisibility(View.GONE);  
              holder.image.setVisibility(View.VISIBLE);
              Log.i("Show image","show");
	        return view;

	    }
	    
	    static class ViewHolder {
	    	   protected TextView text;
	    	   protected TextView text1;
		        protected ImageView image;
		        protected ProgressBar pb;
	        
	    }
	    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
	        int width = bm.getWidth();
	        int height = bm.getHeight();
	        float scaleWidth = ((float) newWidth) / width;
	        float scaleHeight = ((float) newHeight) / height;
	        // CREATE A MATRIX FOR THE MANIPULATION
	        Matrix matrix = new Matrix();
	        // RESIZE THE BIT MAP
	        matrix.postScale(scaleWidth, scaleHeight);
	        // RECREATE THE NEW BITMAP
	        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	        return resizedBitmap;
	    }
	    
}
