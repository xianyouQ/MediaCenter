package com.youxianqin.mediacenter.bean;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import android.os.AsyncTask;


public class NetWorkStringUpLoad extends AsyncTask<String, Integer, String>{
	private HashMap<String, ArrayList<String>> StringHashMap;
	
	public NetWorkStringUpLoad(HashMap<String, ArrayList<String>> StringHashMap)
	{
		this.StringHashMap = StringHashMap;
	}
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		if (arg0[0] == null) {  
            return null;  
        }
		HttpClient httpclient = null;  
        httpclient = new DefaultHttpClient();  

         HttpPost httppost = new HttpPost(constant.SERVER_HOST+"myhome/"+arg0[0]);  
        MultipartEntityBuilder multipartEntityBuilder =  MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		multipartEntityBuilder.setCharset(Charset.forName(HTTP.UTF_8));
		
		Iterator iter = StringHashMap.entrySet().iterator();
		while (iter.hasNext()) {
		Map.Entry entry = (Map.Entry) iter.next();
		  String key = (String)entry.getKey();
		  ArrayList<String> val = (ArrayList<String>)entry.getValue();
		  for(String StrItem:val){
		        ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);            
	            StringBody StrItemBody;
	            StrItemBody = new StringBody(StrItem, contentType);
		        multipartEntityBuilder.addPart(key, StrItemBody);
		  }
		}	    
          HttpEntity entity = multipartEntityBuilder.build();
          httppost.setEntity(entity);
          HttpResponse httpResponse;  
          try {  
              httpResponse = httpclient.execute(httppost);  

              final int statusCode = httpResponse.getStatusLine()  
                      .getStatusCode();  


              if (statusCode == HttpStatus.SC_OK) {  
                  return "success";  
              }  
              InputStream in = httpResponse.getEntity().getContent();
				  BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
	               StringBuilder videotypeStrBuild = new StringBuilder();
	               String line = null;
	               while ((line = reader.readLine()) != null) {
	            	   videotypeStrBuild.append(line + "\n");
	               }
	               System.out.print("shangchuanjieguo "+videotypeStrBuild.toString());
          } catch (ClientProtocolException e) {  
              e.printStackTrace();  
          } catch (IOException e) {  
              e.printStackTrace();  
          } finally {  
              if (httpclient != null) {  
                  httpclient.getConnectionManager().shutdown();  
                  httpclient = null;  
              }  
          }  
          return null;  

	}
}