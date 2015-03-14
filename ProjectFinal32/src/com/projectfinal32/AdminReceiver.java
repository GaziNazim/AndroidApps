package com.projectfinal32;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;


public class AdminReceiver extends DeviceAdminReceiver {
	static final String TAG = "DemoDeviceAdminReceiver";

	/** Called when this application is approved to be a device administrator. */
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
//		Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//	    vibrator.vibrate(2000);
	}
	
	@Override
	public void onEnabled(Context context, Intent intent) {
		super.onEnabled(context, intent);
		Toast.makeText(context, R.string.device_admin_enabled,
				Toast.LENGTH_LONG).show();
		Log.d(TAG, "onEnabled");
	}

	/** Called when this application is no longer the device administrator. */
	@Override
	public void onDisabled(Context context, Intent intent) {
		super.onDisabled(context, intent);
		Toast.makeText(context, R.string.device_admin_disabled,
				Toast.LENGTH_LONG).show();
		Log.d(TAG, "onDisabled");
	}

	@Override
	public void onPasswordChanged(Context context, Intent intent) {
		super.onPasswordChanged(context, intent);
		
		Toast.makeText(context,"Passwor Changed",
				Toast.LENGTH_LONG).show(); 
//		 Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//		    vibrator.vibrate(2000);
		Log.d(TAG, "onPasswordChanged");
	}

	@Override
	public void onPasswordFailed(Context context, Intent intent) {
		super.onPasswordFailed(context, intent);
//		Toast.makeText(context,"Not Succed",
//				Toast.LENGTH_LONG).show();
		
		  DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
	        Integer currentFailedPasswordAttempts = devicePolicyManager.getCurrentFailedPasswordAttempts(); 
		    
		if(currentFailedPasswordAttempts==3){
			  Intent i = new Intent();
		        i.setClassName("com.projectfinal32", "com.projectfinal32.TakePicture");
		        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        context.startActivity(i);
		}
		  
	}

	@Override
	public void onPasswordSucceeded(Context context, Intent intent) {
		super.onPasswordSucceeded(context, intent);
		Log.d(TAG, "onPasswordSucceeded");
	}

}
