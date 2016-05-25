package com.youxianqin.mediacenter.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.youxianqin.mediacenter.MediaCenterApp;
import com.youxianqin.mediacenter.R;
import com.youxianqin.mediacenter.bean.NetWorkPictureDownLoad;
import com.youxianqin.mediacenter.bean.VideoParent;
import com.youxianqin.mediacenter.bean.constant;




public class MyPinterestLinkAdatper extends BaseAdapter{
    private ArrayList<VideoParent> VideoParentList;
    private MediaCenterApp myMediaCenterApp;
    
    
    public MyPinterestLinkAdatper(ArrayList<VideoParent> VideoParentList,MediaCenterApp myMediaCenterApp)
    {
    	this.VideoParentList=VideoParentList;
    	this.myMediaCenterApp=myMediaCenterApp;
    	
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

	@SuppressLint("InflateParams")
	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(myMediaCenterApp)
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
		loadBitmap(VideoParentList.get(arg0),holder.refreshImageView);
		return convertView;
	}
	
	public void loadBitmap(VideoParent mVideoParent,ImageView imageView)
	{   
		String url = constant.SERVER_HOST+constant.PICTURE_URL+mVideoParent.GetScriptPicUrl();
		final Bitmap bitmap = myMediaCenterApp.LruCache.getBitmapFromMemCache(mVideoParent.GetScriptPicUrl());
		if(mVideoParent.GetScriptPicUrl() !=null && bitmap != null)
		{
			imageView.setImageBitmap(bitmap);
		}
		else{
			imageView.setImageResource(R.drawable.nofoundpic);
			PictureDownload DownLoad = new PictureDownload(imageView,mVideoParent);
			DownLoad.execute(url);
		}
	}
	
	class ViewHolder{
		ImageView refreshImageView;
		TextView refreshTextView;
		
		
	}
	
	class PictureDownload extends NetWorkPictureDownLoad{
		    private ImageView imageView;
	        private VideoParent mVideoParent;
	        public PictureDownload(ImageView imageView,VideoParent mVideoParent)
	        {
	        	this.imageView = imageView;
	        	this.mVideoParent=mVideoParent;
	        }
			
			@Override
			protected void onPostExecute(Bitmap result) {
				// TODO Auto-generated method stub
				if (result != null)
				{
					myMediaCenterApp.LruCache.addBitmapToMemoryCache(mVideoParent.GetScriptPicUrl(), result);
					imageView.setImageBitmap(result);
				}
			}

				
			
		}
	
	
}