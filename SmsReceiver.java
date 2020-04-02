package org.secure.sms;
import android.app.Application;
import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import org.secure.sms.MainActivity1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.IntentSender.SendIntentException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import org.secure.sms.SmsSender;

public class SmsReceiver extends BroadcastReceiver
{
	String finalcontactvalue;
	// All available column names in SMS table
// [_id, thread_id, address, 
	// person, date, protocol, read, 
	// status, type, reply_path_present, 
	// subject, body, service_center, 
	// locked, error_code, seen]
	
	public static final String SMS_EXTRA_NAME = "pdus";
	public static final String SMS_URI = "content://sms";
	public String messages = "";
	public static final String ADDRESS = "address";
    	public static final String PERSON = "person";
    	public static final String DATE = "date";
    	public static final String READ = "read";
    	public static final String STATUS = "status";
    	public static final String TYPE = "type";
    	public static final String BODY = "body";
    	public static final String SEEN = "seen";
    
   	 public static final int MESSAGE_TYPE_INBOX = 1;
    	public static final int MESSAGE_TYPE_SENT = 2;
    
    	public static final int MESSAGE_IS_NOT_READ = 0;
    	public static final int MESSAGE_IS_READ = 1;
    	
    	public static final int MESSAGE_IS_NOT_SEEN = 0;
    	public static final int MESSAGE_IS_SEEN = 1;
    	StringBuilder finalmsgbody = new StringBuilder();
    	// Change the password here or give a user possibility to change it
    	public static final byte[] PASSWORD = new byte[]{ 0x20, 0x32, 0x34, 0x47, (byte) 0x84, 0x33, 0x58 };
    	String address="";
    	String pass = "";
  	//  Context con;
	public void onReceive( Context context, Intent intent ) 
	{
		// Get SMS map from Intent
       /* Bundle extras = intent.getExtras();
     		  // con = context;
        		if ( extras != null )
        		{
            		Object[] smsExtra = (Object[]) extras.get( SMS_EXTRA_NAME );
            		ContentResolver contentResolver = context.getContentResolver();
            		for ( int i = 0; i < smsExtra.length; ++i )
            		{
            			SmsMessage sms = SmsMessage.createFromPdu((byte[])smsExtra[i]);
            			String body = sms.getMessageBody().toString();
            			address = sms.getOriginatingAddress();
               		 	messages += body;
             		}*/
		
		 Bundle extras = intent.getExtras();
		 if ( extras != null )
	        	{
	            	// Get received SMS array
	            	Object[] smsExtra = (Object[]) extras.get( SMS_EXTRA_NAME );
	            	final SmsMessage[] incomingSms = new SmsMessage[smsExtra.length];
	            
	           		 ContentResolver contentResolver = context.getContentResolver();
	            
	            	for( int i = 0; i< smsExtra.length;i++ )
	            	{
	            		 incomingSms[i] = SmsMessage.createFromPdu((byte[]) smsExtra[i]);
	            	}
	           	            if (incomingSms.length > -1) 
{
	           			     Log.i("*************",":Messagerecieved:"+incomingSms[0].getMessageBody());
	                		messages = incomingSms[0].getMessageBody().toString();
	               	 	address = incomingSms[0].getOriginatingAddress();
	           			Log.i("Message is :", ""+messages);
	            	}
            
           			 // Display SMS message
          			 Toast.makeText( context, messages, Toast.LENGTH_SHORT ).show();
            
            		Log.i("----------HIEllooooooo-----------","INSIDE RECEIVR");
            																	SharedPreferencespreferences=PreferenceManager.getDefaultSharedPreferences(context);
            		String verify = preferences.getString("value", "");
            
           		 	Toast.makeText( context, verify, Toast.LENGTH_SHORT ).show();
          			  Log.i(""+verify,",,,,,,,,,,,,,,,,"+verify);
           
           			pass = parser(verify,messages);
           			Log.i("----AFTER returning frm parser---", "pass: "+pass);
            		if(pass!=null)
            		{
            			Log.i("inside----", "pass");
            			Intent i1 = new Intent(context, SmsSender.class);
            			Log.i("inside----", "pass");
            			//i1.putExtra("message", pass);
            			//i1.putExtra("address", address);
            			SmsSender sm = new SmsSender();
            			Log.i("-----Aftre---", "Sm obj creation");
            			sm.sendMsg(pass , address , context);
            			//context.startActivity(i1);
            			Log.i("--INSIDE---", "PASSS");
            		}
            
        	}
 }
 public  String parser(String verify,String messages)
 {
	 Log.i("----------HIE-----------","INSIDE PARSER");
	 Log.i("verification key: "+verify,"INSIDE PARSER"+messages);
	 String vc=verify;   // verification code
 String[] myList = messages.split(" ");
	 if( myList[0].equals(vc))
	 {
	    	  Log.i("VERIFICATION MATCHED", "YUPIEEE");
		  finalcontactvalue=myList[1];
	              Log.i("final value inside parser", ""+finalcontactvalue);
	              return finalcontactvalue;
	      }
	    else
	    {
	    	 Log.i("VERIFICATION NOT MATCHED", "booooo");
	    	 return null;
	    }
		    
}
}
