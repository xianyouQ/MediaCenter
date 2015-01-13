package com.youxianqin.mediacenter.bean;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class NetWorkPictureDownLoad extends AsyncTask<String, Integer, Bitmap>{

			@Override
			protected Bitmap doInBackground(String... arg0) {
				// TODO Auto-generated method stub
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(arg0[0]);
				System.out.println("HOST   "+arg0[0]);
				try {
					HttpResponse response = client.execute(get);
					
					if(response.getStatusLine().getStatusCode() != 200)
					{
						System.out.println("error return code "+response.getStatusLine().getStatusCode());
						return null;
					}
					InputStream in = response.getEntity().getContent();
					Bitmap resultBitmap = BitmapFactory.decodeStream(in);  
					return resultBitmap;
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
			protected void onPostExecute(Bitmap result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
			}
			
			
}