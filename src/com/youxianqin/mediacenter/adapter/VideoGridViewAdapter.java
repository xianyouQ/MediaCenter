package com.youxianqin.mediacenter.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.youxianqin.mediacenter.R;
import com.youxianqin.mediacenter.bean.LruCacheForMediaCenter;
import com.youxianqin.mediacenter.bean.NetWorkPictureDownLoad;
import com.youxianqin.mediacenter.bean.Video;
import com.youxianqin.mediacenter.bean.constant;


public class VideoGridViewAdapter extends BaseAdapter{
    private ArrayList<Video> VideoList;
    private LruCacheForMediaCenter LruCache;
    private Context ctx;
    public VideoGridViewAdapter(ArrayList<Video> VideoList,LruCacheForMediaCenter LruCache,Context ctx)
    {
    	this.VideoList = VideoList;
    	this.LruCache = LruCache;
    	this.ctx = ctx;
    			
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

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(ctx)
					.inflate(R.layout.video_item_activity, null);
			holder = new ViewHolder();
			holder.VideoItemImageView = (ImageView) convertView
					.findViewById(R.id.VideoItemImage);
			holder.VideoItemTextView = (TextView) convertView.findViewById(R.id.VideoItemText);
	
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.VideoItemTextView.setText(Integer.toString(VideoList.get(arg0).GetVideoId()));
		if(VideoList.get(arg0).GetVideoScriptPicUrl() !="null")
		loadBitmap(constant.SERVER_HOST+"/"+VideoList.get(arg0).GetVideoScriptPicUrl(),holder.VideoItemImageView);
		else{
			holder.VideoItemImageView.setImageResource(R.drawable.ic_launcher_chrome);
		}
		return convertView;
	}
	
	
	public void loadBitmap(String url,ImageView imageView)
	{
		final Bitmap bitmap = LruCache.getBitmapFromMemCache(url);
		if(url !=null && bitmap != null)
		{
		
			imageView.setImageBitmap(bitmap);
		}
		else{
			
			imageView.setImageResource(R.drawable.ic_launcher);
			PictureDownload DownLoad = new PictureDownload(imageView,url);
			DownLoad.execute(url);
		}
	}
	
	class ViewHolder{
		ImageView VideoItemImageView;
		TextView VideoItemTextView;
		
		
	}
	
	class PictureDownload extends NetWorkPictureDownLoad{
	    private ImageView imageView;
        private String url;
        public PictureDownload(ImageView imageView,String url)
        {
        	this.imageView = imageView;
        	this.url=url;
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