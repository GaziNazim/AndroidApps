package com.hiddencamera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;


public class TakePicture {
	private Camera mCamera;
	private CameraPreview mPreview;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	String add;
	File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
    
	  private void releaseCamera(){
	        if (mCamera != null){
	            mCamera.release();        // release the camera for other applications
	            mCamera = null;
	        }
	    }
	  public TakePicture() {
		// TODO Auto-generated constructor stub
		  mCamera=getCInstance();
		  mCamera.takePicture(null, null, mPicture);	
	}
	  
	//  TextView tv = (TextView) findViewById(R.id.textView1);
	private PictureCallback mPicture= new PictureCallback() {
		
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			//TextView tv = (TextView) findViewById(R.id.textView1);
			
			// TODO Auto-generated method stub
			//File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
			if(pictureFile==null){
				Log.d("PictureFile","Error creating file, check storage permissions: ");
			   return;
			}
			try{
				FileOutputStream fos = new FileOutputStream(pictureFile);
				fos.write(data);
				fos.close();
				add=pictureFile.toString();
				//tv.append(add);
			}catch(FileNotFoundException e){
				Log.d("FileOutputstream","File not found " + e.getMessage());
				//tv.append("NOT");
			}
			catch(IOException e)
			{
				Log.d("IO Exception","Error accessing file " + e.getMessage());
				//tv.append("NOT");
			}
			//releaseCamera();
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
	        "IMG_"+ timeStamp + ".jpg");
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
	public static Camera getCInstance()
	{
		Camera c=null;
		try{
			c=Camera.open();
			
		}
		catch(Exception e){
			c=null;
		}
		return c;
	}
	
}
