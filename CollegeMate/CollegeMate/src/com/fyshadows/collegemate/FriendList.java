package com.fyshadows.collegemate;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class FriendList extends ListActivity {

	 List<FriendInfoTable> list = new ArrayList<FriendInfoTable>();
	 Collegemate_DB db = new Collegemate_DB(this);
	 int listPosition ;
	@SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        
        registerForContextMenu(this.getListView());
        list.clear();
        getActionBar().setDisplayHomeAsUpEnabled(true);
       
         
        final ProgressDialog dialog = ProgressDialog.show(FriendList.this,"", "Gettting values from DB", true);
        //new    Thread   (new Runnable() {
          //  public void run() {
        List<FriendInfoTable> list;
        list = db.getFriendDetails();
                //list.add(sData);
                 
                dismissDialog(dialog);
                
        /*************************** GOT data from Server ********************************************/
        Log.i("c","into adapter");
        if(!list.isEmpty())
        {
        FriendListArrayAdapter adapter = new FriendListArrayAdapter(this,list);
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
        }
        
                ListView lv = getListView();
                
                // listening to single list item on click
                lv.setOnItemClickListener(new OnItemClickListener() {
                  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                	 
                	  FriendInfoTable FriendInfo = FriendListArrayAdapter.getFriendInfoTablePosition(position);
                	   String user_id;
                	   String user_name;
                	   String PicPath;
                	    user_id=FriendInfo.getuserid();
                	    user_name=FriendInfo.getusername();
                	    PicPath=FriendInfo.getuserpicpath();
                	   
              		  //Create the bundle
              		  Bundle bundle = new Bundle(); 
              		  Log.i("bundle",user_name);
              		 bundle.putString("username", user_name); 
              		 bundle.putString("userid", user_id); 
              		 bundle.putString("PicPath", PicPath);
              		Log.i("userid",user_id);
              		Log.i("PicPath",PicPath);
              		
              		Intent i = new Intent(FriendList.this, MessageActivity.class);
              		i.putExtras(bundle);
          		  //Fire that second activity
          		  startActivity(i);

                      }
                 	
                  
                });
                
	}
	
	@Override 
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
	     listPosition = info.position;      
	     Log.i("position,",String.valueOf(listPosition));
	    super.onCreateContextMenu(menu, v, menuInfo);
	    menu.setHeaderTitle("Choose Action");   // Context-menu title
	    menu.add(0, v.getId(), 0, "Delete All Chat");  // Add element "Edit"
	    menu.add(0, v.getId(), 1, "Remove Friend");        // Add element "Delete"
	}

	@Override  
	 public boolean onContextItemSelected(MenuItem item)
	 {
	      if(item.getTitle() == "Delete All Chat") 
	      {
	    	  FriendInfoTable FriendInfo1 = FriendListArrayAdapter.getFriendInfoTablePosition(listPosition);
	        db.deleteChat(FriendInfo1.getuserid().trim());
	        Toast.makeText(FriendList.this,
                    "Deleted Chat ",
                    Toast.LENGTH_SHORT).show();
	        
	      }
	      else if(item.getTitle() == "Remove Friend")  
	      {
	    	  FriendInfoTable FriendInfo1 = FriendListArrayAdapter.getFriendInfoTablePosition(listPosition);
		        db.removefriend(FriendInfo1.getuserid().trim());
		        Toast.makeText(FriendList.this, "Removed Friend",Toast.LENGTH_SHORT).show();
		        onRestart();
	         
	      }
	      else 
	      {
	         return false;
	      }

	      return true;  
	 } 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	            Intent intent = new Intent(this, BasicHomeScreen.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
         // Inflate the menu items for use in the action bar
         MenuInflater inflater = getMenuInflater();
         inflater.inflate(R.menu.friend_list, menu);
         return super.onCreateOptionsMenu(menu);
     } 
	
	

	@Override
	protected void onRestart() {

	    
	    super.onRestart();
	    Intent i = new Intent(this, FriendList.class); 
	    startActivity(i);
	    finish();

	}
	
    public void dismissDialog(final ProgressDialog dialog){
        runOnUiThread(new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        });
    }
    
    public void GotoFriendlist(View view)
	{
		Intent i = new Intent(this, ChatHomeScreen.class);
		  startActivity(i);
	}
  
}

