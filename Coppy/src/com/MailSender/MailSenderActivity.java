package com.MailSender;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MailSenderActivity extends Activity {

    /** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        final Button send = (Button) this.findViewById(R.id.send);
        final EditText et = (EditText) findViewById(R.id.editText1);
     //   GMailSender sender = new GMailSender("nazimawon@gmail.com", "N1107036");
        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
            	 
                try {   
                	 GMailSender sender = new GMailSender("nazimawon@gmail.com", "N1107036");
                    sender.sendMail("This is Subject",   
                            "This is Body",   
                            "gazinazim1@gmail.com",   
                            "gazinazim1@gmail.com");   
                    et.setText("yes");
                } catch (Exception e) {   
                    Log.e("SendMail", e.getMessage(), e);   
                } 

            }
        });

    }
}