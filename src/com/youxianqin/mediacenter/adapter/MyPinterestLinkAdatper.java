package com.youxianqin.mediacenter.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.youxianqin.mediacenter.R;
import com.youxianqin.mediacenter.bean.LruCacheForMediaCenter;
import com.youxianqin.mediacenter.bean.VideoParent;
import com.youxianqin.mediacenter.bean.constant;




public class MyPinterestLinkAdatper extends BaseAdapter{
    private ArrayList<VideoParent> VideoParentList;
    private Context ctx;
    private LruCacheForMediaCenter LruCache;
    
    public MyPinterestLinkAdatper(ArrayList<VideoParent> VideoParentList,Context ctx,LruCacheForMediaCenter LruCache)
    {
    	this.VideoParentList=VideoParentList;
    	this.ctx=ctx;
    	this.LruCache=LruCache;
    	
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return VideoParentList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return VideoParentList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(ctx)
					.inflate(R.layout.refreshlist_item_activity, null);
			holder = new ViewHolder();
			holder.refreshImageView = (ImageView) convertView
					.findViewById(R.id.refresh_ImageView);
			holder.refreshTextView = (TextView) convertView.findViewById(R.id.refresh_TextView);
	
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.refreshTextView.setText(VideoParentList.get(arg0).GetName());
		if(VideoParentList.get(arg0).GetScriptPicUrl() !=null)
		loadBitmap(constant.SERVER_HOST+"/"+VideoParentList.get(arg0).GetScriptPicUrl(),holder.refreshImageView);
		//holder.refreshImageView.setImageResource(R.drawable.ic_launcher_chrome);
		
		return convertView;
	}
	
	public void loadBitmap(String url,ImageView imageView)
	{
		final Bitmap bitmap = LruCache.getBitmapFromMemCache(url);
		if(url !=null && bitmap != null)
		{
			System.out.println("find  Pic"+ url);
			imageView.setImageBitmap(bitmap);
		}
		else{
			System.out.println("can`t find Pic"+ url);
			imageView.setImageResource(R.drawable.ic_launcher);
			PictureDownload DownLoad = new PictureDownload(imageView,url);
			DownLoad.execute(url);
		}
	}
	
	class ViewHolder{
		ImageView refreshImageView;
		TextView refreshTextView;
		
		
	}
	
	class PictureDownload extends AsyncTask<String, Integer, Bitmap>{
		    private ImageView imageView;
	        private String url;
	        public PictureDownload(ImageView imageView,String url)
	        {
	        	this.imageView = imageView;
	        	this.url=url;
	        }
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
				if (result != null)
				{
					LruCache.addBitmapToMemoryCache(url, result);
					imageView.setImageBitmap(result);
				}
			}

				
			
		}
	
	
}