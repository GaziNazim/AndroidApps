package com.projectfinal32;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class MailAsyctask extends AsyncTask<String, String, String>{

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		 Mail m = new Mail(params[0],params[2]); 
		 
	      String[] toArr = {params[1],params[1]}; 
	      m.setTo(toArr); 
	      m.setFrom(params[0]); 
	      m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device."); 
	      m.setBody("Email body."); 
	 
	      try { 
	    	  Log.v("D","d");
	    	  String f=Environment.getExternalStorageDirectory().toString();
	        m.addAttachment(f+"/Pictures/MyCameraApp/IMG_.jpg"); 
	 
	        if(m.send()) { 
	         // Toast.makeText(Demo.this, "Email was sent successfully.", Toast.LENGTH_LONG).show(); 
	        } else { 
	          //Toast.makeText(Demo.this, "Email was not sent.", Toast.LENGTH_LONG).show(); 
	        } 
	      } catch(Exception e) { 
	        //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show(); 
	         Log.v("D","d");
	    	  
	    	  Log.e("MailApp", "Could not send email", e); 
	      } 
		return null;
	}

}
