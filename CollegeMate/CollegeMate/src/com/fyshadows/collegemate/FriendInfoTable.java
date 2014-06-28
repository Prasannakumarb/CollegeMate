package com.fyshadows.collegemate;

import android.R.string;

public class FriendInfoTable {
	private String _user_id;
	private String _user_name;
	private String _PicPath;
	private int _newmsgcount;
    
    public FriendInfoTable(){
        
    }
    
public FriendInfoTable( String userid,String username,String PicPath){
        this._user_id=userid;
        this._user_name=username;
        this._PicPath=PicPath;
    }
public FriendInfoTable( String userid,String username,String PicPath,int msgcount){
    this._user_id=userid;
    this._user_name=username;
    this._PicPath=PicPath;
    this._newmsgcount=msgcount;
}
public FriendInfoTable( String userid,String username){
    this._user_id=userid;
    this._user_name=username;
   
}
    
   public String getuserid()
   {
	   return _user_id; 
    }
   public String getusername()
   {
	   return _user_name; 
    }
   public  String getuserpicpath()
   {
	   return _PicPath; 
    }
   public  int getmessagecount()
   {
	   return _newmsgcount; 
    }

}
