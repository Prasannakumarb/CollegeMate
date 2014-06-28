package com.fyshadows.collegemate;

import android.util.Log;

public class DiscussionList {
	
	private String title;
    private String category;
    private String description;
    private String time;
    private String discId;
    private String like_Status;
     
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getDiscId() {
        return discId;
    }
	public void setDiscId(String discId) {
        this.discId = discId;	
	}
	public String getLikeStatus() {
		return like_Status;
	}
	public void setLikeStatus(String like_status){
		this.like_Status = like_status;
	}

}
