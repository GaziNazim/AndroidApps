package com.projectfinal32;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class GPS extends Service implements LocationListener{
	
	
	Float lon,lat;
	boolean isGPSEnable=false,isNetworkEnable=false;
	private Criteria criteria= new Criteria();
	String slon,slat;
	int count;
	SharedPreferences sp;
	String fromid,toid,pass;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		count=0;
		initLocation();
		getPre();
		
		
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void initLocation(){
		LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
	    criteria.setAccuracy(Criteria.ACCURACY_COARSE);
	    criteria.setCostAllowed(true);
	    isGPSEnable=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	    isNetworkEnable=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	    String bestProvider = locationManager.getBestProvider(criteria, false);
	    locationManager.requestLocationUpdates(bestProvider, 60*1000*2, 2, locationListener);
	
	
	}
	
	private void getPre(){
		sp = getSharedPreferences("MyPrefs",Context.MODE_PRIVATE); 
        fromid = sp.getString("Fromid", null);
		toid = sp.getString("Toid", null);
		pass = sp.getString("Password",null);
	}
	
	
	protected  LocationListener locationListener = GPS.this;

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		lon = (float) location.getLongitude();
		lat=(float) location.getLatitude();
		slon=lon.toString();
		slat=lat.toString();
		Toast.makeText(getApplicationContext(),"GPS  "+ slon +slat, Toast.LENGTH_LONG).show();
	   if(count<5){
		   
		   new MailAsycforGPS().execute(fromid,toid,pass,slon,slat);
		   count++;
	   }
	   else{
		   this.stopSelf();
	   }
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	
	

}
