package com.youxianqin.mediacenter.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import android.os.AsyncTask;

public class NetWorkStringDownLoad extends AsyncTask<String, Integer, String>{
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000); 
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
		HttpGet get = new HttpGet(arg0[0]);
		try {
			HttpResponse response = client.execute(get);
			
			if(response.getStatusLine().getStatusCode() != 200)
			{
				return null;
			}
			InputStream in = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
               StringBuilder videotypeStrBuild = new StringBuilder();
               String line = null;
               while ((line = reader.readLine()) != null) {
            	   videotypeStrBuild.append(line + "\n");
               }
               return videotypeStrBuild.toString();
     		
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		
		
	}
	
	
}