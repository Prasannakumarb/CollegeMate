package com.fyshadows.collegemate;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ForumAdapter extends ArrayAdapter<DiscussionList> {
	private static List<DiscussionList> items = null;

	public ForumAdapter(Context context, List<DiscussionList> items) {
		super(context, R.layout.custom_list, items);
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	public static DiscussionList getModelPosition(int position) {
		return items.get(position);
	}

	public void refill(List<DiscussionList> items) {
		items.clear();
		items.addAll(items);
		notifyDataSetChanged();
	}

	public static class ViewHolder {
		WebView mywebviewholder;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater li = LayoutInflater.from(getContext());
			v = li.inflate(R.layout.custom_list, null);
		}

		DiscussionList app = items.get(position);

		if (app != null) {
			TextView titleText = (TextView) v.findViewById(R.id.dscTitle);
			TextView categoryText = (TextView) v.findViewById(R.id.dscCategory);
			TextView descriptionText = (TextView) v
					.findViewById(R.id.dscDescription);
			TextView timeText = (TextView) v.findViewById(R.id.dscTime);
			TextView idText = (TextView) v.findViewById(R.id.dscDiscId);
			final TextView likeText = (TextView) v.findViewById(R.id.likeText1);
			TextView comm_Count = (TextView) v.findViewById(R.id.commentsCount);
			TextView like_Count = (TextView) v.findViewById(R.id.likesCount);

			String like_Status = app.getLikeStatus();
			String commCount = app.getCommCount();
			String likeCount = app.getLikeCount();

			titleText.setText(app.getTitle());
			categoryText.setText(app.getCategory());
			descriptionText.setText(app.getDescription());
			timeText.setText(app.getTime());
			idText.setText(app.getDiscId());
			if (like_Status == "null") {
				likeText.setText("Like");
				int non_like_text_color = v.getResources().getColor(
						R.color.nonlikeTextColor);
				((TextView) v.findViewById(R.id.likeText1))
						.setTextColor(non_like_text_color);
			} else {
				likeText.setText("Liked");
				final int like_text_color = v.getResources().getColor(
						R.color.likeTextColor);
				((TextView) v.findViewById(R.id.likeText1))
						.setTextColor(like_text_color);
			}
			if (commCount == "null") {
				comm_Count.setText("0 C");
			} else {
				comm_Count.setText(commCount + " C");
			}
			if (likeCount == "null") {
				like_Count.setText("0 L");
			} else {
				like_Count.setText(likeCount + " L");
			}

			final String dId = app.getDiscId();

			// onClick for image button inside list view

			likeText.setTag(new Integer(position));
			likeText.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					// final Integer myposition = (Integer) view.getTag();
					likeText.setText("Liked");
					final int like_text_color = view.getResources().getColor(
							R.color.likeTextColor);
					((TextView) view.findViewById(R.id.likeText1))
							.setTextColor(like_text_color);
					MainActivity val = new MainActivity();
					val.updateLikeTable(dId);
				}

			});

		}

		return v;
	}
}
