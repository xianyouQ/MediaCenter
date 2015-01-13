package com.youxianqin.mediacenter;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.views.PinnedSectionListView;
import com.youxianqin.mediacenter.adapter.MyVideoPlayViewPagerAdapter;
import com.youxianqin.mediacenter.adapter.VideoGridViewAdapter;
import com.youxianqin.mediacenter.bean.MySqliteDbHelper;
import com.youxianqin.mediacenter.bean.NetWorkStringDownLoad;
import com.youxianqin.mediacenter.bean.Video;
import com.youxianqin.mediacenter.bean.VideoParent;
import com.youxianqin.mediacenter.bean.constant;



public class VideoPlayActivity extends Activity implements  OnInfoListener, OnBufferingUpdateListener{
	private ArrayList<Video> VideoList = new ArrayList<Video>();
	private VideoView mVideoView;
	private ProgressBar pb;
	private TextView downloadRateView, loadRateView;
	private MySqliteDbHelper VideoViewBufferDBHelper;
	private MediaController myMediaController;
	private ImageButton screenshotBtn;
	private static String screenshotPicDir="screenshot";
	private MediaCenterApp myMediaCenterApp;
	private String ScreenShot;
	private VideoParent mVideoParent;
	private ViewPager mViewPager;
	private ArrayList<View> ViewList = new ArrayList<View>();
	private ArrayList<String> TitleList = new ArrayList<String>();
	private PinnedSectionListView historyList;
	private GridView VideoGridView;
	private VideoGridViewAdapter VideoGridViewAdapter;
	private RelativeLayout VideoViewRelativeLayout;
	private int VideoPos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.video_play_activity);
		
		 if (!LibsChecker.checkVitamioLibs(this))
		      return;
		 mVideoView = (VideoView) findViewById(R.id.buffer);
		 pb = (ProgressBar) findViewById(R.id.probar);
		 downloadRateView = (TextView) findViewById(R.id.download_rate);
		 myMediaCenterApp=(MediaCenterApp)this.getApplication();
		 VideoViewBufferDBHelper = new MySqliteDbHelper(myMediaCenterApp);
		 loadRateView = (TextView) findViewById(R.id.load_rate);
		 Bundle videoparent = this.getIntent().getBundleExtra("VideoParent");
		 mVideoParent = (VideoParent)videoparent.getSerializable("VideoParent");
		 mViewPager = (ViewPager) findViewById(R.id.videoplaypager);
		 VideoViewRelativeLayout = (RelativeLayout) findViewById(R.id.VideoViewRelativeLayout);
		 LayoutInflater Inflater = getLayoutInflater().from(this);
		 View VideoListView = Inflater.inflate(R.layout.video_list_activity, null);
		 View HistoryListView = Inflater.inflate(R.layout.video_play_history, null);
		 historyList = (PinnedSectionListView) HistoryListView.findViewById(R.id.historyListView);
		 VideoGridView = (GridView) VideoListView.findViewById(R.id.VideoGridView);
		 VideoGridViewAdapter = new VideoGridViewAdapter(VideoList, myMediaCenterApp.LruCache, myMediaCenterApp);
		 if(VideoGridView == null) System.out.println("cao");
		 VideoGridView.setAdapter(VideoGridViewAdapter);
		 VideoGridView.setOnItemClickListener(new VideoGridViewOnItemClickListener());
		 ViewList.add(VideoListView);
		 ViewList.add(HistoryListView);
		 TitleList.add(constant.VIDEO_PLAY_ACTIVITY_VIEWPAGER_TITLE1);
		 TitleList.add(constant.VIEO_PLAY_ACTIVITY_VIEWPAGER_TITLE2);
		 mViewPager.setAdapter(new MyVideoPlayViewPagerAdapter(ViewList, TitleList));
         myMediaController = new MediaController(this);
         VideoDownLoad VideoListDownLoad = new VideoDownLoad();
         VideoListDownLoad.execute(constant.SERVER_HOST+"/myhome/getvideolist/?parent="+mVideoParent.GetName());
         /*
         myMediaController.setOnShownListener(new OnShownListener() {
		
		  @Override
		  public void onShown() {
			// TODO Auto-generated method stub
			
			Log.v("MediaControl", "show");
			if(screenshotBtn.getVisibility() != View.VISIBLE){
				screenshotBtn.setVisibility(View.VISIBLE);
			}
		 }
	 });
     myMediaController.setOnHiddenListener(new OnHiddenListener() {
		
		@Override
		public void onHidden() {
			// TODO Auto-generated method stub
			Log.v("MediaControl", "hidden");
			if(screenshotBtn.getVisibility() != View.GONE){
				screenshotBtn.setVisibility(View.GONE);
			}
		}
	});*/
   

}

	
	void InitVideoView(){
		   mVideoView.setMediaController(myMediaController);
		    mVideoView.requestFocus();
		    mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_ORIGIN, 0);
		    mVideoView.setOnInfoListener(this);
		    mVideoView.setOnBufferingUpdateListener(this);
		    mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
		      @Override
		      public void onPrepared(MediaPlayer mediaPlayer) {
		        // optional need Vitamio 4.0
		        mediaPlayer.setPlaybackSpeed(1.0f);
		        LinearLayout.LayoutParams VideoViewRelativeLayoutParams = (LinearLayout.LayoutParams) VideoViewRelativeLayout.getLayoutParams();
		        VideoViewRelativeLayoutParams.height = mediaPlayer.getVideoHeight();
		        VideoViewRelativeLayout.setLayoutParams(VideoViewRelativeLayoutParams);
		      }
		    });
		    mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					VideoPos=VideoPos+1;
					Uri VideoURI = Uri.parse(constant.SERVER_HOST+"/"+VideoList.get(VideoPos).GetVideoUrl());
		      		System.out.println("Get Uri"+VideoURI.toString());
		      		mVideoView.setVideoURI(VideoURI);
				}
			});
		    
		  
	}

@Override
public boolean onInfo(MediaPlayer mp, int what, int extra) {
  switch (what) {
  case MediaPlayer.MEDIA_INFO_BUFFERING_START:
    if (mVideoView.isPlaying()) {
      mVideoView.pause();
      pb.setVisibility(View.VISIBLE);
      downloadRateView.setText("");
      loadRateView.setText("");
      downloadRateView.setVisibility(View.VISIBLE);
      loadRateView.setVisibility(View.VISIBLE);
      
    }
    break;
  case MediaPlayer.MEDIA_INFO_BUFFERING_END:
    mVideoView.start();
    pb.setVisibility(View.GONE);
    downloadRateView.setVisibility(View.GONE);
    loadRateView.setVisibility(View.GONE);
    break;
  case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
    downloadRateView.setText("" + extra + "kb/s" + "  ");
    break;
  }
  return true;
}

@Override
public void onBufferingUpdate(MediaPlayer mp, int percent) {
  loadRateView.setText(percent + "%");
}
	

class VideoDownLoad extends NetWorkStringDownLoad{

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			  if (result !=null )
		       {
		    	   try {
		              
		              JSONArray VideoJsonArray = new JSONArray(result);
		              VideoList.clear();
		              for (int i =0 ;i < VideoJsonArray.length() ; i++)
		              {
		            	  JSONObject VideoItemJson=(JSONObject) VideoJsonArray.get(i);
		            	  JSONObject  VideoItem = VideoItemJson.getJSONObject("fields");
		                  Video Video = new Video();
		                  Video.SetVideoName(VideoItem.getString("file_name"));
		                  Video.SetVideoUrl(VideoItem.getString("full_path")); //hehehehe
		                  Video.SetVideoParent(mVideoParent.GetName());
		                  Video.SetVideoId(VideoItem.getInt("Video_id"));
		                  Video.SetVideoScriptPicUrl(VideoItem.getString("video_scriptPic"));
		                  VideoList.add(Video);
		              }
		          
		              Collections.sort(VideoList);
		              VideoGridViewAdapter.notifyDataSetChanged();
		              VideoPos =0;
		      		Uri VideoURI = Uri.parse(constant.SERVER_HOST+"/"+VideoList.get(VideoPos).GetVideoUrl());
		      		System.out.println("Get Uri"+VideoURI.toString());
		      		mVideoView.setVideoURI(VideoURI);
		      		InitVideoView();
		             
		           
		           } catch (JSONException e){
		    	    	 e.printStackTrace();
		    	     }
		       }
		      
			  else
			  {
				  Toast.makeText(VideoPlayActivity.this,constant.NETWORK_ERROR_STRING, Toast.LENGTH_LONG).show();
				  
			  }
			
		}
		
		
	}


class VideoGridViewOnItemClickListener implements OnItemClickListener{

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if(mVideoView.isPlaying())
		{
			mVideoView.stopPlayback();
			
		}
		VideoPos = (int)arg3;
		Uri VideoURI = Uri.parse(constant.SERVER_HOST+"/"+VideoList.get(VideoPos).GetVideoUrl());
		System.out.println("Get Uri"+VideoURI.toString());
		mVideoView.setVideoURI(VideoURI);
		mVideoView.start();
	}
	
}
}