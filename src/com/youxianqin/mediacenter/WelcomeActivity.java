package com.youxianqin.mediacenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.youxianqin.mediacenter.bean.MySqliteDbHelper;
import com.youxianqin.mediacenter.bean.NetWorkStringDownLoad;
import com.youxianqin.mediacenter.bean.VideoType;
import com.youxianqin.mediacenter.bean.constant;


public class WelcomeActivity extends Activity{
	
	
	VideoTypeDownLoad VideotypeDownLoad;
	//ArrayList<String> Titles= new ArrayList<String>();
	private ImageView WelcomeImage;
	
	MediaCenterApp MyMeidaCenterApp;
	MySqliteDbHelper MySqliteDbHelper;
	
	void initView(){
	    WelcomeImage = (ImageView) findViewById(R.id.welcomeImage);
	    MyMeidaCenterApp = (MediaCenterApp)getApplication();
	    MyMeidaCenterApp.Titles.clear();
	    MySqliteDbHelper = new MySqliteDbHelper(getApplication());
	    ArrayList<VideoType> VideoTypeFromSqlite = MySqliteDbHelper.queryVideoType();
 	    MyMeidaCenterApp.Titles.addAll(VideoTypeFromSqlite);
		VideotypeDownLoad = new VideoTypeDownLoad();
		VideotypeDownLoad.execute(constant.SERVER_HOST+"/myhome/getvideotype/");
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  
		setContentView(R.layout.welcome_activity);
		initView();
	}
	
	
	class VideoTypeDownLoad extends NetWorkStringDownLoad{

		

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if(result != null )
			{
				 JSONArray VideotypeJsonArray;
				try {
					VideotypeJsonArray = new JSONArray(result);
					MyMeidaCenterApp.Titles.clear();
	              for (int i =0 ;i < VideotypeJsonArray.length() ; i++)
	              {
	            	  JSONObject VideotypeItemJson=(JSONObject) VideotypeJsonArray.get(i);
	            	  System.out.println("zifuchuan " +VideotypeItemJson.toString());
	            	  VideoType mVideoType = new VideoType();
	            	  mVideoType.SetTypeName(VideotypeItemJson.getJSONObject("fields").getString("type_name"));
	            	  MyMeidaCenterApp.Titles.add(mVideoType); 
	                 
	              }
	              MySqliteDbHelper.insertToTableType( MyMeidaCenterApp.Titles);
	             
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			 else
		       {
		    	   Toast.makeText(WelcomeActivity.this,constant.NETWORK_ERROR_STRING, Toast.LENGTH_LONG).show();
		    	  
		       }
			/*
		          try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
		          Intent welcomeIntentToMain = new Intent();
		          welcomeIntentToMain.setClass(WelcomeActivity.this, MainActivity.class);
		          WelcomeActivity.this.startActivity(welcomeIntentToMain);
		          WelcomeActivity.this.finish();
		       
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			
			super.onProgressUpdate(values);
		}
			
		
	}
	
	
}