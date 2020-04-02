package org.secure.sms;
//import net.swierczynski.autoresponder.TxtMsgSender;
import android.content.*;
import android.util.Log;
public class IncomingMsgsService 
{
	private Context mCtx;
	//private TxtMsgReceiver txtReceiver;
	private SmsReceiver txtReceiver;
	public static boolean isActive = false;
	public IncomingMsgsService(Context mCtx)
	{
		this.mCtx = mCtx;
	}
	public void register() 
	{
		Log.i("----inside---", "register method");
		//TxtMsgSender msgSender = TxtMsgSender.createAndSetUp(mCtx);
IntentFilterincomingTxtFilter=newIntentFilter("android.provider.Telephony.SMS_RECEIVED");
			mCtx.registerReceiver(new SmsReceiver(),incomingTxtFilter);
		isActive = true;
	}
public void unregister()
{
		try
		{
			mCtx.unregisterReceiver(txtReceiver);
		} 
		catch (IllegalArgumentException e)
		{
			// Do nothing
		}
		finally
		{
 			isActive = false;
		}
	}
}
