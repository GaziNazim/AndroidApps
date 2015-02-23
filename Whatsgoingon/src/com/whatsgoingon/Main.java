package com.whatsgoingon;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main extends Activity {

	@Override
	public void onCreate(Bundle icicle) { 
		  super.onCreate(icicle); 
		  setContentView(R.layout.main); 
		 
		  Button addImage = (Button) findViewById(R.id.send_email); 
		  addImage.setOnClickListener(new View.OnClickListener() { 
		    public void onClick(View view) { 
		   new  Demo().execute("ff");
		  // new AsyncForPicture().execute("Pic");
		    } 
		  }); 
		} 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
