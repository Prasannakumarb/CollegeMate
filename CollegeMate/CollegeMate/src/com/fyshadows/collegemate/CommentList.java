package com.fyshadows.collegemate;


public class CommentList {
	
	private String commentReceived;
	private String comUserName;
	private String commentTime;
     
    public String getComment() {
        return commentReceived;
    }
    public void setComment(String commentReceived) {
        this.commentReceived = commentReceived;
    }
    public String getName(){
    	return comUserName;
    }
    public void setName(String comUserName){
    	this.comUserName = comUserName;
    }
    public String getComTime(){
    	return commentTime;
    }
    public void setComTime(String commentTime){
    	this.commentTime = commentTime;
    }

}
