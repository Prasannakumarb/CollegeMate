package com.fyshadows.collegemate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import static com.fyshadows.collegemate.CommonUtilities.SENDER_ID;
import static com.fyshadows.collegemate.CommonUtilities.displayMessage;

public class GCMIntentService extends GCMBaseIntentService {
	static String username;
	static String picpath;
	static String userid;
	static String tempuserid;
	static int Unique_Integer_Number = 0;
	public static int messagecount = 0;
	static int messagecount2 = 0;
	private static final String TAG = "GCMIntentService";
	Collegemate_DB db = new Collegemate_DB(this);
	public static String identify;

	public GCMIntentService() {
		super(SENDER_ID);
	}

	/**
	 * Method called on device registered
	 **/
	@Override
	protected void onRegistered(Context context, String registrationId) {
		Log.i(TAG, "Device registered: regId = " + registrationId);

		Log.d("NAME", "prasanna");
		ServerUtilities.register(context, "prasanna", "prasanna",
				registrationId);
	}

	/**
	 * Method called on device un registred
	 * */
	@Override
	protected void onUnregistered(Context context, String registrationId) {
		Log.i(TAG, "Device unregistered");
		displayMessage(context, getString(R.string.gcm_unregistered));
		ServerUtilities.unregister(context, registrationId);
	}

	/**
	 * Method called on Receiving a new message
	 * */
	@Override
	protected void onMessage(Context context, Intent intent) {

		identify = intent.getExtras().getString("identify");
		if (identify != null) {
			if (identify.trim().equals("chat")) {
				Log.i("got this", intent.getExtras().getString("userid"));
				String message = intent.getExtras().getString("message");
				userid = intent.getExtras().getString("userid");
				Log.i("uid", userid);
				username = intent.getExtras().getString("username");
				picpath = intent.getExtras().getString("picpath");
				Log.i("path", picpath);
				picpath = "http://fyshadows.com/CollegeMate/" + picpath;
				String condition = db.CheckId(userid.trim());
				Log.i("abc", condition);
				if (condition.trim().equalsIgnoreCase("True")) {
					int pathlength = picpath.length();
					String picname = picpath.substring(46, pathlength);
					;
					Log.i("picname baby", picname);
					URL url = null;
					try {
						url = new URL(picpath);
					} catch (MalformedURLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					InputStream input = null;
					try {
						input = url.openStream();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try {
						// The sdcard directory e.g. '/sdcard' can be used
						// directly, or
						// more safely abstracted with
						// getExternalStorageDirectory()
						File storagePath = Environment
								.getExternalStorageDirectory();
						OutputStream output = new FileOutputStream(
								new File(
										storagePath
												+ "/collegemate/Profilepic/FriendsProfPic"
												+ File.separator, picname));
						try {
							int aReasonableSize = 50;
							byte[] buffer = new byte[aReasonableSize];
							int bytesRead = 0;
							while ((bytesRead = input.read(buffer, 0,
									buffer.length)) >= 0) {
								output.write(buffer, 0, bytesRead);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							try {
								output.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						try {
							input.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					Log.i("a", "saved");
					// --------------------------------

					db.addfriend(new FriendInfoTable(userid, username, picname));
				}

				db.addChat(new MessageTable(userid, message, 1));
				db.storenewmsgcount(userid, "add");
				displayMessage(context, message);
				generateNotification(context, message);

			} else if (identify.trim().equals("notification")) {
				String message = intent.getExtras().getString("message");
				String notificationid = intent.getExtras().getString("notificationid");
				db.addnotification(new notificationtable( notificationid,message));
				displayMessage(context, message);
				generateNotification(context, message);
			}
		}
	}

	/**
	 * Method called on receiving a deleted message
	 * */
	@Override
	protected void onDeletedMessages(Context context, int total) {
		Log.i(TAG, "Received deleted messages notification");
		String message = getString(R.string.gcm_deleted, total);
		displayMessage(context, message);
		// notifies user
		generateNotification(context, message);

	}

	/**
	 * Method called on Error
	 * */
	@Override
	public void onError(Context context, String errorId) {
		Log.i(TAG, "Received error: " + errorId);
		displayMessage(context, getString(R.string.gcm_error, errorId));
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		// log message
		Log.i(TAG, "Received recoverable error: " + errorId);
		displayMessage(context,
				getString(R.string.gcm_recoverable_error, errorId));
		return super.onRecoverableError(context, errorId);
	}

	/**
	 * Issues a notification to inform the user that server has sent a message.
	 */
	@SuppressWarnings("deprecation")
	private static void generateNotification(Context context, String message) {
		if (identify.trim().equals("chat")) {
			int icon = R.drawable.ic_launcher;
			long when = System.currentTimeMillis();

			NotificationManager notificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notification;
			Log.i("msgcount", String.valueOf(messagecount));
			if (messagecount == 0) {
				Log.i("i am in ", "i am in");
				notification = new Notification(icon, username + ":" + message,
						when);
				messagecount = messagecount + 1;
				tempuserid = userid;
			} else {
				messagecount = messagecount + 1;
				if (tempuserid.trim().equalsIgnoreCase(userid)) {
					tempuserid = userid;
					notification = new Notification(icon, messagecount
							+ " Message Received", when);
				} else {
					tempuserid = "toomany";
					notification = new Notification(icon, messagecount
							+ " Message Received", when);
				}
			}
			String title = context.getString(R.string.app_name);
			Intent notificationIntent;
			if (tempuserid.trim().equalsIgnoreCase("toomany")) {
				notificationIntent = new Intent(context, FriendList.class);
			} else {
				notificationIntent = new Intent(context, MessageActivity.class);
				// set intent so it does not start a new activity
				Bundle bundle = new Bundle();
				Log.i("bundle", username);
				bundle.putString("username", username);
				bundle.putString("userid", userid);
				bundle.putString("PicPath", picpath);
				notificationIntent.putExtras(bundle);
			}
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent intent = PendingIntent.getActivity(context, 0,
					notificationIntent, 0);
			if (messagecount2 == 0) {
				notification
						.setLatestEventInfo(context, title, message, intent);
				messagecount2 = messagecount2 + 1;
			} else {
				notification.setLatestEventInfo(context, title, messagecount2
						+ " Message Received", intent);
				messagecount2 = messagecount2 + 1;
			}

			notification.flags |= Notification.FLAG_AUTO_CANCEL;

			// Play default notification sound
			notification.defaults |= Notification.DEFAULT_SOUND;

			// Vibrate if vibrate is enabled
			notification.defaults |= Notification.DEFAULT_VIBRATE;

			notificationManager.notify(Unique_Integer_Number, notification);
		} else if (identify.trim().equals("notification")) {
			int icon = R.drawable.ic_launcher;
			long when = System.currentTimeMillis();
			NotificationManager notificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notification = new Notification(icon, message, when);

			String title = context.getString(R.string.app_name);

			Intent notificationIntent = new Intent(context, ViewNotification.class);
			// set intent so it does not start a new activity
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent intent = PendingIntent.getActivity(context, 0,
					notificationIntent, 0);
			notification.setLatestEventInfo(context, title, message, intent);
			notification.flags |= Notification.FLAG_AUTO_CANCEL;

			// Play default notification sound
			notification.defaults |= Notification.DEFAULT_SOUND;

			// Vibrate if vibrate is enabled
			notification.defaults |= Notification.DEFAULT_VIBRATE;
			notificationManager.notify(0, notification);
		}
	}

}
