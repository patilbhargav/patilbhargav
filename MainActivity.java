package org.secure.sms;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity1 extends Activity {
	
//	Log.i("---wow---" , "***Almost final copy***");
	String message="";
	String telno = "";
	StringBuilder finalmsgbody = new StringBuilder();
	
	public String value="";
	Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		registerTextsCheckboxListener();
		Button more = (Button) findViewById(R.id.button1);
		more.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View view) 
			{
			AlertDialog.Builderbuilder=newAlertDialog.Builder(MainActivity1.this);
				builder.setTitle("Set Verify Code");
				builder.setIcon(R.drawable.ic_launcher);

				final EditText input = new EditText(MainActivity1.this);
				builder.setView(input);
				
				builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() 
				{
				public void onClick(DialogInterface dialog, int id) 
				{
				dialog.cancel();
							}
						});
				builder.setPositiveButton("OK",
				new DialogInterface.OnClickListener()
	{
		public void onClick(DialogInterface dialog, int id) 
			{
								
				value = input.getText().toString();
				SharedPreferencespreferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString("value", value);
				editor.commit();
				Toast.makeText(MainActivity1.this,value,Toast.LENGTH_SHORT).show();
				dialog.cancel();
							}
						});
				builder.show();
			}
		});
	}
	
	private void registerTextsCheckboxListener()
	{
			setServiceState(true, "texts");
	}
		private void setServiceState(boolean enabled, String modes)
	{
		Intent service = new Intent(this, AutoResponderService.class);
		service.putExtra("isEnabled", enabled);
		service.putExtra("mode", modes);
		startService(service);
	}
}
