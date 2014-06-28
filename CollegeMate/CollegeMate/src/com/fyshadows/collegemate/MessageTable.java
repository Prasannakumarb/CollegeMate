package com.fyshadows.collegemate;

public  class MessageTable {

		
	private  String _message;
		
	private int _isMine;
	private String  _userid;
	private String  _username;
	private String _timestamp;
	private int _sent;

		static boolean isStatusMessage;

		 public MessageTable(){
		        
		    }
		 
		public MessageTable( String userid,String message,int isMine,String timestamp) {
			super();
			this._userid= userid;
			this._message = message;
			this._isMine = isMine;
			this._timestamp=timestamp;
			
		}
		
		public MessageTable( String userid,String message,int isMine,int sent,String timestamp) {
			super();
			this._userid= userid;
			this._message = message;
			this._isMine = isMine;
			this._sent = sent;
			this._timestamp=timestamp;
			
		}
		
		public MessageTable( String userid,String message,int isMine) {
		
			this._userid= userid;
			this._message = message;
			this._isMine = isMine;
			
			
		}
		
		
		public String getMessage() {
				return this._message;
			}
		
		public  String getuserid() {
			return this._userid;
		}
		public   int isMine() {
			return _isMine;
		}
		public  String gettimestamp() {
			return _timestamp;
		}
		public  int getsent() {
			return _sent;
		}
		
		public void setMessage(String message) {
			this._message = message;
		}
		
		
		public static boolean isStatusMessage() {
			return isStatusMessage;
		}
		


	}
