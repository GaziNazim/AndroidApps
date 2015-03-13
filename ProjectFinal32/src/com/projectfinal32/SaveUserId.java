package com.projectfinal32;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SaveUserId extends Activity{
	
	
	SharedPreferences sp;
	SharedPreferences.Editor editor;
	String nn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.saveuserid);
		
		
		
		sp = getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit(); 
		
		
		final  EditText to = ( EditText) findViewById(R.id.toemail);
		Button sb = (Button) findViewById(R.id.save);
	sb.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//Toast.makeText(SaveUserId.this, "Please fill all fields", Toast.LENGTH_LONG).show();
			if(to.getText().toString().matches(""))
			{
				Toast.makeText(SaveUserId.this, "Please fill all fields", Toast.LENGTH_LONG).show();
			}
			else{
				
				SharedPreferences.Editor editor = sp.edit();
				editor.putString("Fromid", "nazimawon@gmail.com");
				editor.putString("Password", "N1107036");
				editor.putString("Toid", to.getText().toString());
			    editor.commit();
			    onBackPressed();
				
			}
			
		}
	});
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		super.onBackPressed();
	}
	
	

}
