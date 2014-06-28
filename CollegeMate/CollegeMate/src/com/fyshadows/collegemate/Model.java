package com.fyshadows.collegemate;

public class Model {
	private String name;
    private String url;
    private String user_id;
     
    public Model(String name, String url,String user_id) {
        this.name = name;
        this.url = url;
        this.user_id=user_id;
    }
    
    public Model(String name, String url) {
        this.name = name;
        this.url = url;
        
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getURL() {
        return url;
    }
 
    public void setURL(String url) {
        this.url = url;
    }
    
    public String getUser_id() {
        return user_id;
    }
 
    public void setUser_id(String User_id) {
        this.url = user_id;
    }
    
}
