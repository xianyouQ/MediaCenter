package com.youxianqin.mediacenter.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.youxianqin.mediacenter.MediaCenterApp;
import com.youxianqin.mediacenter.R;
import com.youxianqin.mediacenter.bean.NetWorkPictureDownLoad;
import com.youxianqin.mediacenter.bean.Video;
import com.youxianqin.mediacenter.bean.constant;


public class VideoGridViewAdapter extends BaseAdapter{
    private ArrayList<Video> VideoList;
    private MediaCenterApp myMediaCenterApp;
    public VideoGridViewAdapter(ArrayList<Video> VideoList,MediaCenterApp myMediaCenterApp)
    {
    	this.VideoList = VideoList;
    	this.myMediaCenterApp = myMediaCenterApp;
    
    			
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return VideoList.size();
	}

	@Override
	public Video getItem(int arg0) {
		// TODO Auto-generated method stub
		return VideoList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null || convertView.getTag() == null) {
			convertView = LayoutInflater.from(myMediaCenterApp)
					.inflate(R.layout.history_list_item_activity, null);
			holder = new ViewHolder();
			holder.VideoItemImageView = (ImageView) convertView
					.findViewById(R.id.HistoryVideoItemImage);
			holder.VideoItemTextView = (TextView) convertView.findViewById(R.id.HistoryVideoItemText);
	        
			holder.HistoryVideoNameText = (TextView) convertView.findViewById(R.id.HistoryVideoNameText);
			holder.HistoryVideoLastText = (TextView) convertView.findViewById(R.id.HistoryVideoLastText);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.VideoItemTextView.setText(Integer.toString(VideoList.get(arg0).GetVideoId()));
		holder.HistoryVideoLastText.setText(" ");
		holder.HistoryVideoNameText.setText(VideoList.get(arg0).GetVideoName());
		
		if(VideoList.get(arg0).GetVideoScriptPicUrl() !="null")
		loadBitmap(VideoList.get(arg0),holder.VideoItemImageView);
		else{
			holder.VideoItemImageView.setImageResource(R.drawable.nofoundpic);
		}
		return convertView;
	}
	
	
	public void loadBitmap(Video mVideo,ImageView imageView)
	{
		final Bitmap bitmap = myMediaCenterApp.LruCache.getBitmapFromMemCache(mVideo.GetVideoScriptPicUrl());
		final String url=constant.SERVER_HOST+constant.PICTURE_URL+mVideo.GetVideoScriptPicUrl();
		if(mVideo.GetVideoScriptPicUrl() !=null && bitmap != null)
		{
		
			imageView.setImageBitmap(bitmap);
		}
		else{
			imageView.setImageResource(R.drawable.nofoundpic);
			PictureDownload DownLoad = new PictureDownload(imageView,mVideo);
			DownLoad.execute(url);
		}
	}
	
	class ViewHolder{
		ImageView VideoItemImageView;
		TextView VideoItemTextView;
		TextView HistoryVideoNameText;
		TextView HistoryVideoLastText;

		
		
	}
	
	class PictureDownload extends NetWorkPictureDownLoad{
	    private ImageView imageView;
        private Video mVideo;
        public PictureDownload(ImageView imageView,Video mVideo)
        {
        	this.imageView = imageView;
        	this.mVideo=mVideo;
        }
		
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			if (result != null)
			{
				myMediaCenterApp.LruCache.addBitmapToMemoryCache(mVideo.GetVideoScriptPicUrl(), result);
				imageView.setImageBitmap(result);
			}
		}

			
		
	}
	
}