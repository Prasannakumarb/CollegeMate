package com.fyshadows.collegemate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatCustomAdpter extends ArrayAdapter<MessageTable> {

	private final Activity context;
	private static List<MessageTable> list = null;
	private int Check = 0;

	public ChatCustomAdpter(Activity context, List<MessageTable> list) {
		super(context, R.layout.sms_row, list);
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		Log.i("a", "a" + list.size());
		return list.size();
	}

	public static MessageTable getMessageTablePosition(int position) {
		return list.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		Log.i("a", "into get view");
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.sms_row, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) view.findViewById(R.id.message_text);
			viewHolder.text.setTextColor(Color.BLACK);
			view.setTag(viewHolder);
		} else {
			view = convertView;
		}

		ViewHolder holder = (ViewHolder) view.getTag();
		holder.text.setText(list.get(position).getMessage()
				+ System.getProperty("line.separator")
				+ list.get(position).gettimestamp());

		LinearLayout ll = (LinearLayout) view.findViewById(R.id.messlinear);

		if (list.get(position).isMine() == Check) {
			if (list.get(position).getsent() == 1) {
				holder.text
						.setBackgroundResource(R.drawable.speech_bubble_green);
			} else {
				holder.text
						.setBackgroundResource(R.drawable.speech_bubble_white);
			}

			// ll.setOrientation(LinearLayout.VERTICAL);
			ll.setGravity(Gravity.RIGHT);
			// ll.gravity = Gravity.RIGHT;
			// holder.text.setTextColor(R.color.Mytextcolor);
		}
		// If not mine then it is from sender to show orange background and
		// align to left
		else {
			holder.text.setBackgroundResource(R.drawable.speech_bubble_orange);
			ll.setGravity(Gravity.LEFT);
			// holder.text.setTextColor(R.color.Histextcolor);
		}
		// holder.text.setLayoutParams(lp);
		return view;

	}
	
	static class ViewHolder {

		protected TextView text;

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
