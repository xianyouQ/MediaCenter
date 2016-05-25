package com.youxianqin.mediacenter.bean;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import android.graphics.Bitmap;
import android.os.AsyncTask;


public class NetWorkPictureUpLoad extends AsyncTask<Bitmap, Integer, String>{
	private Video mVideo;
	
	public NetWorkPictureUpLoad(Video mVideo)
	{
		this.mVideo = mVideo;
	}
	@Override
	protected String doInBackground(Bitmap... arg0) {
		// TODO Auto-generated method stub
		HttpClient httpclient = null;  
        httpclient = new DefaultHttpClient();  

        HttpPost httppost = new HttpPost(constant.SERVER_HOST+"myhome/uploadpic/");  
        MultipartEntityBuilder multipartEntityBuilder =  MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		multipartEntityBuilder.setCharset(Charset.forName(HTTP.UTF_8));
		
          if (arg0[0] == null) {  
              return null;  
          }
         
             
             ByteArrayOutputStream baos = new ByteArrayOutputStream();  
             arg0[0].compress(Bitmap.CompressFormat.JPEG, 100, baos);  
             
              multipartEntityBuilder.addPart("video_scriptPic", new ByteArrayBody(baos.toByteArray(),"default"));
		      ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);            
	          StringBody StrItemBody;
	          StrItemBody = new StringBody(mVideo.GetVideoScriptPicUrl(), contentType);
		      multipartEntityBuilder.addPart("file_name", StrItemBody);			
	
          
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