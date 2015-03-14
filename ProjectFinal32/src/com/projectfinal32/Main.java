package com.projectfinal32;

import android.os.Bundle;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Main extends Activity implements
OnCheckedChangeListener {
static final String TAG = "Main";
static final int ACTIVATION_REQUEST = 47; // identifies our request id
DevicePolicyManager devicePolicyManager;
ComponentName demoDeviceAdmin;
ToggleButton toggleButton;


SharedPreferences sp;
SharedPreferences.Editor editor;
String fromid,toid,pass;
String s="";


/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.main);

sp = getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
SharedPreferences.Editor editor = sp.edit(); 

fromid = sp.getString("Fromid", null);
toid = sp.getString("Toid", null);
pass = sp.getString("Password",null);



if(fromid==null||toid==null||pass==null)
{
  startActivity(new Intent(Main.this,SaveUserId.class));	
}


toggleButton = (ToggleButton) super
                .findViewById(R.id.toggle_device_admin);
toggleButton.setOnCheckedChangeListener(this);


// Initialize Device Policy Manager service and our receiver class
devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
demoDeviceAdmin = new ComponentName(this, AdminReceiver.class);



findViewById(R.id.Go_to_second).setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	
		//	Toast.makeText(Main.this, "Go_to_second", Toast.LENGTH_LONG).show();
			startActivity(new Intent(Main.this,SaveUserId.class));
			
		
	}
});


}

/**
* Called when a button is clicked on. We have Lock Device and Reset Device
* buttons that could invoke this method.
*/
public void onClick(View v) {
switch (v.getId()) {
case R.id.button_lock_device:
        // We lock the screen
        Toast.makeText(this, "Locking device...", Toast.LENGTH_LONG).show();
        Log.d(TAG, "Locking device now");
        devicePolicyManager.lockNow();
        break;
case R.id.button_reset_device:
        // We reset the device - this will erase entire /data partition!
        Log.d(TAG,
                        "RESETing device now - all user data will be ERASED to factory settings");
      //  
        Intent it = new Intent(Main.this,TakePicture.class);
    	startActivity(it);
        
        break;
        

        
default:
	Toast.makeText(this, "Wrong", Toast.LENGTH_LONG).show();
    break;

}
}

/**
* Called when the state of toggle button changes. In this case, we send an
* intent to activate the device policy administration.
*/
public void onCheckedChanged(CompoundButton button, boolean isChecked) {
if (isChecked) {
	
	fromid = sp.getString("Fromid", null);
	toid = sp.getString("Toid", null);
	pass = sp.getString("Password",null);
	
	if(fromid==null||toid==null||pass==null)
	{
	  startActivity(new Intent(Main.this,SaveUserId.class));	
	}
	
	else{
		// Activate device administration
        Intent intent = new Intent(
                        DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                        demoDeviceAdmin);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                        "Your boss told you to do this");
        startActivityForResult(intent, ACTIVATION_REQUEST);
		
	}
        
}
Log.d(TAG, "onCheckedChanged to: " + isChecked);
}


/**
* Called when startActivityForResult() call is completed. The result of
* activation could be success of failure, mostly depending on user okaying
* this app's request to administer the device.
*/
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
switch (requestCode) {
case ACTIVATION_REQUEST:
        if (resultCode == Activity.RESULT_OK) {
                Log.i(TAG, "Administration enabled!");
                toggleButton.setChecked(true);
        } else {
                Log.i(TAG, "Administration enable FAILED!");
                toggleButton.setChecked(false);
        }
        return;
}
super.onActivityResult(requestCode, resultCode, data);
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
