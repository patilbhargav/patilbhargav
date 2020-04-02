package org.secure.sms;
import org.secure.sms.IncomingMsgsService;
import android.app.Service;
import android.content.*;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.*;
import android.util.Log;
public class AutoResponderService extends Service implements OnSharedPreferenceChangeListener 
{
private final IBinder mBinder = new LocalBinder();
private IncomingMsgsService msgsService;
	private BroadcastReceiver notificationAreaReceiver;
	@Override
	public void onCreate() 
	{
		if(!isAnyRespondingServiceActive()) 
		{
			initializeServiceFirstStart();
			}
	}
	private void initializeServiceFirstStart() 
	{
		 registerNotificationAreaReceiver();
		msgsService = new IncomingMsgsService(this);
		//UserPreferences.registerPreferencesChangeListener(getApplicationContext(),this);
	}
	private void registerNotificationAreaReceiver() 
	{
		notificationAreaReceiver = new NotificationArea(this).new Receiver();
		IntentFilter incrementFilter = new IntentFilter(NotificationArea.INCREMENT);
		registerReceiver(notificationAreaReceiver,incrementFilter);
		IntentFilter resetFilter = new IntentFilter(NotificationArea.RESET);
		registerReceiver(notificationAreaReceiver, resetFilter);
		IntentFilter showIconFilter = new IntentFilter(NotificationArea.SHOW_ICON);
		registerReceiver(notificationAreaReceiver, showIconFilter);
		IntentFilter hideIconFilter = new IntentFilter(NotificationArea.HIDE_ICON);
		registerReceiver(notificationAreaReceiver, hideIconFilter);
	}
	@Override
	public void onDestroy() 
	{
		hideNotificationIcon();
		unregisterReceiver(notificationAreaReceiver);
	}
	private void hideNotificationIcon()
 	{
			Intent intent = new Intent(NotificationArea.HIDE_ICON);
		sendBroadcast(intent);
	}	
	@Override
	public void onStart(Intent intent, int startId)
 	{	
		if(intent != null)
 		{
			Bundle extras = intent.getExtras();
			if(extras != null) 
			{
				Log.i("---IN---", "ONSTART---");									changeServiceModeAndPropagateItsState(true,"texts");
			}
		}
	}
	private void changeServiceModeAndPropagateItsState(boolean isEnabled, String mode) 
	{
		changeServiceMode(mode, isEnabled);
		propagateStateToNotificationArea();
		stopIfNoRespondingServicesAreActive();
	}
	private void changeServiceMode(String mode, boolean isEnabled)
 	{
		Log.i("---IN---", "----CHANGE SERVICE MODE---");
		changeTextsModeState(isEnabled);
	}
	private void changeTextsModeState(boolean isEnabled)
	{
		if(isEnabled)
		{
			Log.i("---IN---", "CHANGE TEXTS MODE STATE---");
			msgsService.register();
		} 
		else
		{
			msgsService.unregister();
		}
	}
	private void propagateStateToNotificationArea()
	{
		String iconState = getNewIconState();
		Intent intent = new Intent(iconState);
		sendBroadcast(intent);
	}
	private String getNewIconState()
	{
		if(UserPreferences.isIconInTaskbarSelected(this)&&isAnyRespondingServiceActive()) 
		{
			return NotificationArea.SHOW_ICON;
		} 
		else 
		{
			return NotificationArea.HIDE_ICON;
		}
	}
	private void stopIfNoRespondingServicesAreActive() 
	{
		if (!isAnyRespondingServiceActive()) 
		{
			stopSelf();
		}
	}
	private static boolean isAnyRespondingServiceActive()
	{
		return IncomingMsgsService.isActive;
	}
	@Override
	public IBinder onBind(Intent intent) 
	{
		return mBinder;
	}	
	public class LocalBinder extends Binder
	{
		AutoResponderService getService()
		{
			return AutoResponderService.this;
		}
	}
	publicvoidonSharedPreferenceChanged(SharedPreferencessharedPreferences,Stringke)
	{
		propagateStateToNotificationArea();
	}
}

