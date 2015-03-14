package com.projectfinal32;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TakePicture extends Activity{
	
	private Camera mCamera;
	private CameraPreview mPreview;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	
	
	SharedPreferences sp;
	String fromid,toid,pass;

	
	

    
	  private void releaseCamera(){
	        if (mCamera != null){
	            mCamera.release();        // release the camera for other applications
	            mCamera = null;
	        }
	    }

	private PictureCallback mPicture= new PictureCallback() {
		
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			
			
			// TODO Auto-generated method stub
			File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
			if(pictureFile==null){
				Log.d("PictureFile","Error creating file, check storage permissions: ");
			   return;
			}
			try{
				FileOutputStream fos = new FileOutputStream(pictureFile);
				fos.write(data);
				fos.close();
				//tv.append(pictureFile.toString());
			}catch(FileNotFoundException e){
				Log.d("FileOutputstream","File not found " + e.getMessage());
				//tv.append("NOT");
			}
			catch(IOException e)
			{
				Log.d("IO Exception","Error accessing file " + e.getMessage());
				//tv.append("NOT");
			}
			releaseCamera();
		}
		
	};
	private static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "MyCameraApp");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+".jpg");
	    } else if(type == MEDIA_TYPE_VIDEO) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "VID_"+ timeStamp + ".mp4");
	    } else {
	        return null;
	    }

	    return mediaFile;
	}
	private boolean checkCameraHardware(Context context)
	{
		if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	@SuppressLint("NewApi")
	public static Camera getCInstance()
	{
		Camera c=null;
		try{
			c=Camera.open(1);
			
		}
		catch(Exception e){
			c=null;
		}
		return c;
	}
	
	
	
	public boolean CheckInternet(Context ctx) {
	    ConnectivityManager connec = (ConnectivityManager) ctx
	            .getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	    // Check if wifi or mobile network is available or not. If any of them is
	    // available or connected then it will return true, otherwise false;
	    return wifi.isConnected() || mobile.isConnected();
	}
	
	
	
	
	private void setMobileDataEnabled(Context context, boolean enabled) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
	    final ConnectivityManager conman = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    final Class conmanClass = Class.forName(conman.getClass().getName());
	    final Field connectivityManagerField = conmanClass.getDeclaredField("mService");
	    connectivityManagerField.setAccessible(true);
	    final Object connectivityManager = connectivityManagerField.get(conman);
	    final Class connectivityManagerClass =  Class.forName(connectivityManager.getClass().getName());
	    final Method setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
	    setMobileDataEnabledMethod.setAccessible(true);

	    setMobileDataEnabledMethod.invoke(connectivityManager, enabled);
	}
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.takepicture);
		  Window wind;
	      wind = this.getWindow();
	      wind.addFlags(LayoutParams.FLAG_DISMISS_KEYGUARD);
	      wind.addFlags(LayoutParams.FLAG_SHOW_WHEN_LOCKED);
	      wind.addFlags(LayoutParams.FLAG_TURN_SCREEN_ON);
	      
	      if(!CheckInternet(getApplicationContext())){
	    	  
	    	  try {
					setMobileDataEnabled(getApplicationContext(), true);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	  
	      }
	      
	      sp = getSharedPreferences("MyPrefs",Context.MODE_PRIVATE); 
	        fromid = sp.getString("Fromid", null);
			toid = sp.getString("Toid", null);
			pass = sp.getString("Password",null);
	      
	  
		
		mCamera = getCInstance();
		mPreview = new CameraPreview(this, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);
		ImageButton captureButton = (ImageButton) findViewById(R.id.button_capture);
		captureButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(mCamera!=null)
				{
					 mCamera.takePicture(null, null, mPicture);	
					 new MailAsyctask().execute(fromid,toid,pass);
					 startService(new Intent(TakePicture.this,GPS.class));
					
				}
			    
			    // onBackPressed();
			}
		});
	
	
	}
	@Override
	public void onBackPressed()
	{
		if(mCamera!=null)
		{
			 mCamera.takePicture(null, null, mPicture);	
			 new MailAsyctask().execute(fromid,toid,pass);
			
		}
	     releaseCamera();
	     super.onBackPressed();  // optional depending on your needs
	}

}
