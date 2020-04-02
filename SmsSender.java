package org.secure.sms;



import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SmsSender extends Activity
 {
	String message = "";

	
	//@Override
	//public SmsSender ob1 ;
	//public SmsSender() {
		// TODO Auto-generated constructor stub
//		ob1 = this;
	//
}
	
		StringBuilder finalmsgbody = new StringBuilder();
	
		//String message="Vaibhav";
		//StringBuilder finalmsgbody = new StringBuilder();
		protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.i("im", "oncreate");
	}
		
	
	public void sendMsg(String messages , String address , Context context)
{
		Log.i("---IN---", "sendMsg");
		// Bundle extras=getIntent().getExtras();
		// String message=extras.getString("message");
	    	// String address=extras.getString("address");
	     	//Log.i("---IN---", "sendMsg");
		 message = messages;
		 ContentResolver cr = context.getContentResolver();
		 Log.i("---IN---", "sendMsg---cr");
	       	 Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
		 Log.i("---IN---", "sendMsg---cur--msgs"+message);
	             if (cur.getCount() > 0) 
{
	        	Log.i("---IN---", "sendMsg--if");
		    while (cur.moveToNext()) 
{
		    	Log.i("---IN---", "sendMsg---while");
		        String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
		        Stringname=cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		        Log.i("above","if comparision--name:"+name);
		        if(message.equalsIgnoreCase(name))
	            	{
		        	Log.i("inside","if comparision");
	              //  Toast.makeText(SmsSender.this, "CONTACT FOUND Name: " + name + ", id: " + id, Toast.LENGTH_SHORT).show();
			//Toast.makeText(MainActivity.this, "Name: " + name + ", id: " + id, Toast.LENGTH_SHORT).show();
	                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) 
{
	 		    //Query phone here.  Covered next
	                	Log.i("inside","if comparision---1st parseint");
	                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) 
{
	                	Log.i("inside","if comparision---2nd parseint");
	                 CursorpCur=cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",	 new String[]{id}, null);
	                 while (pCur.moveToNext()) 
{
	                	 Log.i("inside","while move2 next");
	  	        	
	  	        	 StringphoneNo=pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	  	        	 Log.i("phone no: "+phoneNo, "Name: "+name);
                	// Toast.makeText(SmsSender.this, "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
	  	   
                	 //finalName = name;
                	 Log.i("after ", "finalName");
                	finalmsgbody.append(name);
                    finalmsgbody.append(" : ");
                    finalmsgbody.append(phoneNo);
                    finalmsgbody.append("   ");     
} 
	  	        pCur.close();
}
}
}
} 
   cur.close();
		    Log.i("-----", "finalmsgbody"+finalmsgbody);
		    //Toast.makeText(SmsSender.this,"FINALMSG..."Toast.LENGTH_SHORT).show();
		    
		    sendTextMessageIfPossible(address);
		    Log.i("inside","if msgposs");
		    
		    }
		
	}
	
	public void sendTextMessageIfPossible(String destnaddress) {
		//String number = "5554";
		String msgbody = finalmsgbody.toString();
		Log.i("in", "Msgifposs"+msgbody);
		SmsManager smsMgr = SmsManager.getDefault();
		Log.i("in", "Msgifposs");
		smsMgr.sendTextMessage(destnaddress, null,"Heeloo Every1 gm :) "+msgbody, null, null);
			
}

}
