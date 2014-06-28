package com.fyshadows.collegemate;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyCustomArrayAdapter extends ArrayAdapter<Model>  {


	 private final Activity context;
	    private  static List<Model> list=null;
	 
	    public MyCustomArrayAdapter(Activity context, List<Model> list) {
	        super(context, R.layout.list_layout, list);
	        this.context = context;
	        this.list = list;
	    }
	    @Override
	    public int getCount() {
	    	Log.i("a","a"+list.size());
	        return list.size();
	    }
	    
	    public static  Model getModelPosition(int position)
	    {
	        return list.get(position);
	    }
	    
	   
	 
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View view = null;
	        Log.i("a","into get view");
	        if (convertView == null) {
	        	Log.i("a","here baby");
	            LayoutInflater inflator = context.getLayoutInflater();
	            view = inflator.inflate(R.layout.list_layout, null);
	            final ViewHolder viewHolder = new ViewHolder();
	            viewHolder.text = (TextView) view.findViewById(R.id.label);
	            viewHolder.text.setTextColor(Color.BLACK);
	            viewHolder.image = (ImageView) view.findViewById(R.id.image);
	            viewHolder.image.setVisibility(View.GONE);
	            viewHolder.pb = (ProgressBar) view.findViewById(R.id.progressBar1);
	            view.setTag(viewHolder);
	        } else {
	            view = convertView;
	        }
	        ViewHolder holder = (ViewHolder) view.getTag();
	        holder.text.setText(list.get(position).getName());
	        holder.image.setTag(list.get(position).getURL());
	        holder.image.setId(position);
	        PbAndImage pb_and_image = new PbAndImage();
	        pb_and_image.setImg(holder.image);
	        pb_and_image.setPb(holder.pb);
	        new DownloadImageTask().execute(pb_and_image);
	        return view;
	    }
	    
	    static class ViewHolder {
	        protected TextView text;
	        protected ImageView image;
	        protected ProgressBar pb;
	    }
	    
	  
	    
}
