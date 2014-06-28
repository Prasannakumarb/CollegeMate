package com.fyshadows.collegemate;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CommentsAdapter extends ArrayAdapter<CommentList> {
	private static List<CommentList> comitems = null;

	public CommentsAdapter(Context context, List<CommentList> comitems) {
		super(context, R.layout.comment_list, comitems);
		this.comitems = comitems;
	}

	@Override
	public int getCount() {
		return comitems.size();
	}

	public static CommentList getModelPosition(int position) {
		return comitems.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater li = LayoutInflater.from(getContext());
			v = li.inflate(R.layout.comment_list, null);
//			ListView mylistview = (ListView) v.findViewById(android.R.id.list);
//			ViewGroup header = (ViewGroup) li.inflate(R.layout.commentheader, null);
//			mylistview.addHeaderView(header, null, false);
		}

		CommentList getObj = comitems.get(position);

		if (getObj != null) {
			TextView commentUser = (TextView) v.findViewById(R.id.commentUser);
			TextView commentTime = (TextView) v.findViewById(R.id.commentTime);
			TextView commentText = (TextView) v.findViewById(R.id.commentDesc);
			commentUser.setText(getObj.getName());
			commentTime.setText(getObj.getComTime());
			commentText.setText(getObj.getComment());
		}

		return v;
	}
}