package com.fyshadows.collegemate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class notificationcustomadapter extends ArrayAdapter<notificationtable> {

	private final Activity context;
	private static List<notificationtable> list = null;
	

	public notificationcustomadapter(Activity context,
			List<notificationtable> list) {
		super(context, R.layout.notification_row, list);
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		Log.i("a", "a" + list.size());
		return list.size();
	}

	public static notificationtable getnotificationtablePosition(int position) {
		return list.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		Log.i("a", "into get view");
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.notification_row, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) view.findViewById(R.id.notitification_text);
			viewHolder.text.setTextColor(Color.BLACK);
			viewHolder.text1 = (TextView) view.findViewById(R.id.notitification_time);
			viewHolder.text1.setTextColor(Color.BLACK);
			view.setTag(viewHolder);
		} else {
			view = convertView;
		}

		ViewHolder holder = (ViewHolder) view.getTag();
		Log.i("listadpater",list.get(position).getMessage());
		Log.i("listadpater",list.get(position).gettimestamp());
		holder.text.setText(list.get(position).getMessage());
		holder.text1.setText(list.get(position).gettimestamp());
		
		return view;

	}

	static class ViewHolder {

		protected TextView text;
		protected TextView text1;

	}

	public static String formatDate(Date date, String format) {
		SimpleDateFormat form = new SimpleDateFormat(format);
		return form.format(date);
	}

	public static String formatTime(Date time, String format) {
		SimpleDateFormat form = new SimpleDateFormat(format);
		return form.format(time);
	}
}
