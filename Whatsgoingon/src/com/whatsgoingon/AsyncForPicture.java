package com.whatsgoingon;

import android.os.AsyncTask;

public class AsyncForPicture extends AsyncTask<String, String, String>{

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		TakePicture tp= new TakePicture();
		tp.do_everything();
		return null;
	}

}
